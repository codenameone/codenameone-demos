package com.codename1.db;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class DBDemo {
    private Form currentForm;
    private Database db;
    private String[] users = new String[]{"Chen Fishbein", "Shai Almog",
        "Bill gates", "Mark zuckerberg", "Larry Page"};
    private int[] ages = new int[]{36, 36, 56, 28, 39};

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        try {
            boolean created = Database.exists("MyDB.db");
            db = Database.openOrCreate("MyDB.db");
            if(db == null){
                System.out.println("SQLite is not supported on this platform");
                return;
            }
            if (!created) {
            db.execute("create table temp (id INTEGER PRIMARY KEY,name text,num double);");
                for (int i = 0; i < users.length; i++) {
                    db.execute("insert into temp (name,num) values (?,?);", new String[]{users[i], "" + ages[i]});
                }
            }
            final Form f = new Form("SQLite Demo");
            f.setLayout(new BorderLayout());
            f.setScrollable(false);
            f.addComponent(BorderLayout.CENTER, getItems());

            Container cnt = new Container(new GridLayout(1, 4));
            Button add = new Button(new Command("+") {

                public void actionPerformed(ActionEvent evt) {
                    final Container c = new ComponentGroup();
                    c.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                    for (int i = 0; i < users.length; i++) {
                        CheckBox cb = new CheckBox(users[i] + " " + ages[i]);
                        c.addComponent(cb);
                    }
                    Command cmd = Dialog.show("Add Users", c, new Command[]{new Command("Ok"), new Command("Cancel")});
                    if(cmd.getCommandName().equals("Ok")){
                        int count = c.getComponentCount();
                        for (int i = 0; i < count; i++) {
                            CheckBox cb = (CheckBox) c.getComponentAt(i);
                            if (cb.isSelected()) {
                                try {
                                    db.execute("insert into temp (name,num) values (?,?);", new String[]{users[i], "" + ages[i]});
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    Container rows = (Container) f.getContentPane().getComponentAt(0);
                    try {
                        f.replace(rows, getItems(), CommonTransitions.createEmpty());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    f.revalidate();
                }
            });
            cnt.addComponent(add);

            Button remove = new Button(new Command("-") {

                public void actionPerformed(ActionEvent evt) {
                    try {
                        Container rows = (Container) f.getContentPane().getComponentAt(0);
                        int count = rows.getComponentCount();
                        for (int i = 0; i < count; i++) {
                            CheckBox cb = (CheckBox) rows.getComponentAt(i);
                            if (cb.isSelected()) {
                                String id = (String) cb.getClientProperty("id");
                                db.execute("delete from temp where id=" + id);

                            }
                        }
                        f.replace(rows, getItems(), CommonTransitions.createEmpty());
                        f.revalidate();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            });
            cnt.addComponent(remove);

            f.addComponent(BorderLayout.SOUTH, cnt);
            f.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }

    }

    private Container getItems() throws IOException {
        Cursor c = db.executeQuery("select * from temp");


        final Container rows = new ComponentGroup();
        rows.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        rows.setScrollable(true);
        while (c.next()) {
            Row r = c.getRow();
            int id = r.getInteger(0);
            String name = r.getString(1);
            double num = r.getDouble(2);
            CheckBox cb = new CheckBox(id + " " + name + " " + num);
            cb.putClientProperty("id", "" + id);
            rows.addComponent(cb);
        }
        return rows;

    }

    public void stop() {
        currentForm = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
}
