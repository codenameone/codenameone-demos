/**
 * Your application code goes here
 */

package userclasses;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.Storage;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.util.ImageIO;
import com.codename1.io.services.ImageDownloadService;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.system.NativeLookup;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.xml.Element;
import com.codename1.xml.XMLParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.Hashtable;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {
    private String photo;
    private Vector<Hashtable<String,String>> todos;
    private int imageWidth;
    private int imageHeight;
    private Database db;
    
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
        TestNative n = (TestNative)NativeLookup.create(TestNative.class);
        if(n != null && n.isSupported()) {
            n.hiWorld("YO!");
        }
    }


    @Override
    protected void onMain_CaptureButtonAction(final Component c, ActionEvent event) {
        Capture.capturePhoto(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if(evt == null){
                        return;
                    }

                    String path = (String) evt.getSource();
                    if(ImageIO.getImageIO() != null) {
                        String photoName = "" + System.currentTimeMillis();
                        java.io.OutputStream os = Storage.getInstance().createOutputStream(photoName);
                        ImageIO.getImageIO().save(FileSystemStorage.getInstance().openInputStream(path), os, 
                                ImageIO.FORMAT_JPEG, imageWidth, imageHeight, 0.9f);
                        os.close();
                        Image img = Image.createImage(Storage.getInstance().createInputStream(photoName));
                        photo = photoName;
                        ((Label)c).setIcon(img);
                        c.getParent().getParent().animateLayout(400);
                    }
                } catch (Exception ex) {
                    Log.e(ex);
                }                        
            }
        });
    }

    @Override
    protected void beforeMain(Form f) {
        Image icon = fetchResourceFile().getImage("shai_100x125.jpg");
        imageWidth = icon.getWidth();
        imageHeight = icon.getHeight();
        Container tasksContainer = findTasksContainer(f);
        tasksContainer.removeAll();
        todos = (Vector<Hashtable<String,String>>)Storage.getInstance().readObject("todos");
        if(todos == null) {
            todos = new Vector<Hashtable<String,String>>();
            return;
        }
        for(Hashtable<String,String> entry : todos) {
            MultiButton mb = createEntry(entry);
            tasksContainer.addComponent(mb);
        }
    }
    
    private MultiButton createEntry(final Hashtable<String, String> entry) {
        final MultiButton mb = new MultiButton();
        mb.setCheckBox(true);
        mb.setTextLine1((String)entry.get("title"));
        mb.setTextLine2((String)entry.get("description"));
        String photo = (String)entry.get("photo");
        if(photo != null) {
            try {
                mb.setIcon(Image.createImage(Storage.getInstance().createInputStream(photo)));
            } catch (IOException ex) {
                Log.e(ex);
            }
        } else {
            String photoURL = (String)entry.get("photoURL");
            if(photoURL != null) {
                ImageDownloadService.createImageToStorage(photoURL, mb.getIconComponent(), 
                        (String)entry.get("title"), new Dimension(imageWidth, imageHeight));
            }
        }
        mb.setEmblem(null);
        mb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Container parent = mb.getParent();
                parent.removeComponent(mb);
                parent.animateLayout(500);
                todos.remove(entry);
                Storage.getInstance().writeObject("todos", todos);
                logEntryRemoved(entry);
            }
        });
        return mb;
    }
    

    @Override
    protected void onMain_AddTaskButtonAction(Component c, ActionEvent event) {
        TextField title = findTitleField(c.getParent());
        TextField description = findDescriptionField(c.getParent());
        Hashtable<String, String> entry = new Hashtable<String, String>();
        entry.put("title", title.getText());
        entry.put("description", description.getText());
        if(photo != null) {
            entry.put("photo", photo);
        }
        title.setText("");
        description.setText("");
        findCaptureButton(c.getParent()).setIcon(null);
        MultiButton mb = createEntry(entry);
        photo = null;
        todos.add(entry);
        Storage.getInstance().writeObject("todos", todos);        
        findTabs1(c.getParent()).setSelectedIndex(0);
        Container tasksContainer = findTasksContainer(c.getParent());
        tasksContainer.addComponent(mb);
        tasksContainer.animateLayout(500);
    }

    @Override
    protected void onMain_ImportJSONAction(final Component c, ActionEvent event) {
        ConnectionRequest cr = new ConnectionRequest() {
            protected void readResponse(InputStream is) throws IOException {
                JSONParser p = new JSONParser();
                Hashtable h = p.parse(new InputStreamReader(is));
                Hashtable<Object, Hashtable<String, String>> todoHash = (Hashtable<Object, Hashtable<String, String>>)h.get("todo");
                Container tasksContainer = findTasksContainer(c.getParent());
                for(Hashtable<String, String> values : todoHash.values()) {
                    String photoURL = values.get("photoURL");
                    String title = values.get("title");

                    MultiButton mb = createEntry(values);
                    todos.add(values);
                    tasksContainer.addComponent(mb);


                    if(photoURL != null) {
                        ImageDownloadService.createImageToStorage(photoURL, mb.getIconComponent(), 
                                title, new Dimension(imageWidth, imageHeight));
                    }
                }
                Storage.getInstance().writeObject("todos", todos);        
                findTabs1(c.getParent()).setSelectedIndex(0);
                tasksContainer.animateLayout(500);
            }
        };
        InfiniteProgress i = new InfiniteProgress();
        Dialog blocking = i.showInifiniteBlocking();
        cr.setDisposeOnCompletion(blocking);
        cr.setPost(false);
        cr.setUrl("https://dl.dropbox.com/u/57067724/cn1/Course%20Material/webservice/NetworkingChapter.json");
        NetworkManager.getInstance().addToQueue(cr);
    }

    @Override
    protected void onMain_ImportXMLAction(final Component c, ActionEvent event) {
        ConnectionRequest cr = new ConnectionRequest() {
            protected void readResponse(InputStream is) throws IOException {
                Container tasksContainer = findTasksContainer(c.getParent());
                XMLParser p = new XMLParser();
                Element elem = p.parse(new InputStreamReader(is));
                int childCount = elem.getNumChildren();
                for(int iter = 0 ; iter < childCount ; iter++) {
                    Element current = elem.getChildAt(iter);
                    String title = current.getAttribute("title");
                    Hashtable values = new Hashtable();
                    values.put("title", title);
                    values.put("description", current.getAttribute("description"));
                    String photoURL = current.getAttribute("photourl");
                    if(photoURL != null) {
                        values.put("photoURL", photoURL);
                    }

                    MultiButton mb = createEntry(values);
                    todos.add(values);
                    tasksContainer.addComponent(mb);


                    if(photoURL != null) {
                        ImageDownloadService.createImageToStorage(photoURL, mb.getIconComponent(), 
                                title, new Dimension(imageWidth, imageHeight));
                    }
                }
                Storage.getInstance().writeObject("todos", todos);        
                findTabs1(c.getParent()).setSelectedIndex(0);
                tasksContainer.animateLayout(500);
            }
        };
        InfiniteProgress i = new InfiniteProgress();
        Dialog blocking = i.showInifiniteBlocking();
        cr.setDisposeOnCompletion(blocking);
        cr.setPost(false);
        cr.setUrl("https://dl.dropbox.com/u/57067724/cn1/Course%20Material/webservice/NetworkingChapter.xml");
        NetworkManager.getInstance().addToQueue(cr);
    }

    @Override
    protected boolean initListModelSqlData(List cmp) {
        Database d = getDb();
        if(d != null) {
            try {
                Cursor c = d.executeQuery("select title, description from log");
                Vector entries = new Vector();
                while (c.next()) {
                    Row r = c.getRow();
                    String title = r.getString(0);
                    String desc = r.getString(1);

                    Hashtable value = new Hashtable();
                    value.put("Line1", title);
                    value.put("Line2", desc);
                    entries.addElement(value);
                    cmp.setModel(new DefaultListModel(entries));
                }
            } catch (IOException ex) {
                Log.e(ex);
            }

        }
        return true;
    }

    private Database getDb() {
        try {
            if(db == null) {
                boolean created = Database.exists("MyDB1.db");
                db = Database.openOrCreate("MyDB1.db");
                if(db == null){
                    System.out.println("SQLite is not supported on this platform");
                    return null;
                }
                if (!created) {
                    db.execute("create table log (id INTEGER PRIMARY KEY,title text,description text,photo text,photoURL text);");
                }
            }
        } catch (IOException ex) {
            Log.e(ex);
        }
        return db;
    }

    void logEntryRemoved(Hashtable<String, String> entry) {
        try {
            Database d = getDb();
            if(d != null) {
                d.execute("insert into log (title,description,photo,photoURL) values (?,?,?,?);", new String[]{
                        entry.get("title"), entry.get("description"), entry.get("photo"), entry.get("photoURL")
                    });        
            }
        } catch (IOException ex) {
            Log.e(ex);
        }
    }

    private boolean leatherTheme = true;
    
    @Override
    protected void onMain_ToggleThemeAction(Component c, ActionEvent event) {
        Resources res = fetchResourceFile();
        if(leatherTheme) {
            UIManager.getInstance().setThemeProps(res.getTheme("Native"));
        } else {
            UIManager.getInstance().setThemeProps(res.getTheme("Theme"));
        }
        leatherTheme = !leatherTheme;
        Display.getInstance().getCurrent().refreshTheme();
    }
}
