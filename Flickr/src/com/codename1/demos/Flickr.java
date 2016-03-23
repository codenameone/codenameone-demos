package com.codename1.demos;

import com.codename1.components.InfiniteProgress;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.BubbleTransition;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Flickr {

    private Form current;

    private static Resources res;

    public void init(Object context) {
        res = UIManager.initFirstTheme("/theme");
        // Pro only feature, uncomment if you have a pro subscription
        //Log.bindCrashProtection(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Form main = createMainForm();
        main.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

    public static void addCommandsToToolbar(Toolbar tool) {

        tool.addCommandToSideMenu(new Command("Main") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Form main = createMainForm();
                main.show();
            }

        });

        tool.addCommandToSideMenu(new Command("Cats") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                final Form cats = new Form("Cats");
                cats.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                cats.setScrollableY(true);
                final CustomToolbar bar = new CustomToolbar(true);
                cats.getContentPane().addScrollListener(bar);
                cats.setToolBar(bar);
                addCommandsToToolbar(bar);
                
                Image icon = FontImage.createMaterial(FontImage.MATERIAL_REFRESH, UIManager.getInstance().getComponentStyle("TitleCommand"));                
                bar.addCommandToRightBar(new Command("", icon) {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Display.getInstance().callSerially(new Runnable() {

                            public void run() {
                                updateScreenFromNetwork(cats, "cat");
                                cats.revalidate();
                            }
                        });
                    }
                });
                bar.addCommandToOverflowMenu(new Command("Clear ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Container cnt = (Container) cats.getContentPane();
                        cnt.removeAll();
                        //add back the big angry cat image
                        try {
                            Image im = Image.createImage("/cat.jpg");
                            im = im.scaledWidth(Display.getInstance().getDisplayWidth());
                            Label bigCat = new Label(im);
                            cats.addComponent(bigCat);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        cnt.revalidate();
                    }
                });
                bar.addCommandToOverflowMenu(new Command("About Cats ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (Dialog.show("Cats", "Cats are meowing", "Ok", "Cancel")) {
                            try {
                                Media m = MediaManager.createMedia(Display.getInstance().getResourceAsStream(getClass(), "/Cats.mp3"), "audio/mp3");
                                m.play();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                });

                //add the big angry cat image
                try {
                    Image im = Image.createImage("/cat.jpg");
                    im = im.scaledWidth(Display.getInstance().getDisplayWidth());
                    Label bigCat = new Label(im);
                    cats.addComponent(bigCat);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                cats.show();

                updateScreenFromNetwork(cats, "cat");

            }

        });
        tool.addCommandToSideMenu(new Command("Dogs") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                final Form dogs = new Form("Dogs");
                dogs.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                dogs.setScrollableY(true);
                Toolbar bar = new Toolbar();
                bar.setScrollOffUponContentPane(true);
                dogs.setToolBar(bar);
                addCommandsToToolbar(bar);
                Image icon = FontImage.createMaterial(FontImage.MATERIAL_REFRESH, UIManager.getInstance().getComponentStyle("TitleCommand"));                
                bar.addCommandToRightBar(new Command("", icon) {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Display.getInstance().callSerially(new Runnable() {

                            public void run() {
                                updateScreenFromNetwork(dogs, "dog");
                                dogs.revalidate();
                            }
                        });
                    }

                });
                bar.addCommandToOverflowMenu(new Command("Clear ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Container cnt = (Container) dogs.getContentPane();
                        cnt.removeAll();
                        cnt.revalidate();
                    }
                });
                bar.addCommandToOverflowMenu(new Command("About Dogs ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (Dialog.show("Dogs", "Dogs are barking", "Ok", "Cancel")) {
                            try {
                                Media m = MediaManager.createMedia(Display.getInstance().getResourceAsStream(getClass(), "/Dogs.mp3"), "audio/mp3");
                                m.play();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                });
                dogs.show();

                updateScreenFromNetwork(dogs, "dog");
            }

        });
        
        tool.addCommandToSideMenu(new Command("Birds") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                final Form birds = new Form();
                birds.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                birds.setScrollableY(true);
                final CustomToolbar2 bar = new CustomToolbar2(true);
                birds.getContentPane().addScrollListener(bar);
                birds.setToolbar(bar);
                bar.setTitle("Birds");
                addCommandsToToolbar(bar);
                
                Image icon = FontImage.createMaterial(FontImage.MATERIAL_REFRESH, UIManager.getInstance().getComponentStyle("TitleCommand"));                
                bar.addCommandToRightBar(new Command("", icon) {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Display.getInstance().callSerially(new Runnable() {

                            public void run() {
                                updateScreenFromNetwork(birds, "bird");
                                birds.revalidate();
                            }
                        });
                    }
                });
                bar.addCommandToOverflowMenu(new Command("Clear ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Container cnt = (Container) birds.getContentPane();
                        cnt.removeAll();
                        try {
                            Image im = Image.createImage("/bird.png");
                            im = im.scaledWidth(Display.getInstance().getDisplayWidth());
                            Label bird = new Label(im);
                            birds.addComponent(bird);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        cnt.revalidate();
                    }
                });
                bar.addCommandToOverflowMenu(new Command("About Birds ") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (Dialog.show("Birds", "Birds are singing", "Ok", "Cancel")) {
                            try {
                                Media m = MediaManager.createMedia(Display.getInstance().getResourceAsStream(getClass(), "/Birds.mp3"), "audio/mp3");
                                m.play();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                });

                try {
                    Image im = Image.createImage("/bird.png");
                    im = im.scaledWidth(Display.getInstance().getDisplayWidth());
                    Label bird = new Label(im);
                    birds.addComponent(bird);
                    bar.setPadding(bird.getPreferredH());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                birds.show();

                updateScreenFromNetwork(birds, "bird");

            }

        });
        
        
        tool.addCommandToSideMenu(new Command("Search") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                final Form search = new Form();
                search.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                search.setScrollableY(true);
                Toolbar bar = new Toolbar();
                search.setToolBar(bar);
                addCommandsToToolbar(bar);
                final TextField txt = new TextField();
                txt.setHint("Enter tag");
                bar.setTitleComponent(txt);
                bar.addCommandToRightBar(new Command("GO") {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Display.getInstance().callSerially(new Runnable() {

                            public void run() {
                                if (txt.getText().length() > 2) {
                                    updateScreenFromNetwork(search, txt.getText());
                                    search.revalidate();
                                }
                            }
                        });
                    }

                });
                search.show();
            }

        });

    }

    public static Form createMainForm() {
        Form main = new Form("Flickr tags");
        main.setLayout(new BorderLayout());
        Toolbar bar = new Toolbar();
        main.setToolBar(bar);
        addCommandsToToolbar(bar);

        TextArea desc = new TextArea();
        desc.setText("This is a Flickr tags demo, the demo uses the Toolbar to arrange the Form Commands.\n\n"
                + "Select \"Cats\" to view the latest photos that were tagged as \"Cats\".\n\n"
                + "Select \"Dogs\" to view the latest photos that were tagged as \"Dogs\".\n\n"
                + "Select \"Search\" to enter your own tags for search.");
        desc.setEditable(false);

        main.addComponent(BorderLayout.CENTER, desc);
        return main;
    }

    /**
     * This method builds a UI Entry dynamically from a data Map object.
     */
    private static Component createEntry(Map data, final int index) {
        final Container cnt = new Container(new BorderLayout());
        cnt.setUIID("MultiButton");
        Button icon = new Button();
        icon.setUIID("Label");
        //take the time and use it as the identifier of the image
        String time = (String) data.get("date_taken");
        String link = (String) ((Map) data.get("media")).get("m");

        EncodedImage im = (EncodedImage) res.getImage("flickr.png");
        final URLImage image = URLImage.createToStorage(im, time, link, null);
        icon.setIcon(image);
        icon.setName("ImageButton" + index);
        icon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                
                Dialog d = new Dialog();
                //d.setDialogUIID("Container");                
                d.setLayout(new BorderLayout());
                Label l = new Label(image);
                l.setUIID("ImagePop");
                d.add(BorderLayout.CENTER, l);
                d.setDisposeWhenPointerOutOfBounds(true);
                d.setTransitionInAnimator(new BubbleTransition(300, "ImageButton" + index));
                d.setTransitionOutAnimator(new BubbleTransition(300, "ImageButton" + index));
                d.show();
            }
        });
        
        cnt.addComponent(BorderLayout.WEST, icon);

        Container center = new Container(new BorderLayout());

        Label des = new Label((String) data.get("title"));
        des.setUIID("MultiLine1");
        center.addComponent(BorderLayout.NORTH, des);
        Label author = new Label((String) data.get("author"));
        author.setUIID("MultiLine2");
        center.addComponent(BorderLayout.SOUTH, author);

        cnt.addComponent(BorderLayout.CENTER, center);
        return cnt;
    }

    private static void addWaitingProgress(Form f) {
        addWaitingProgress(f, true, f.getContentPane());
    }

    private static void addWaitingProgress(Form f, boolean center, Container pane) {
        pane.setVisible(false);
        Container cnt = f.getLayeredPane();
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        cnt.setLayout(bl);
        if (center) {
            cnt.addComponent(BorderLayout.CENTER, new InfiniteProgress());
        } else {
            Container top = new Container();
            BorderLayout bl1 = new BorderLayout();
            bl1.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
            top.setLayout(bl1);
            top.addComponent(BorderLayout.CENTER, new InfiniteProgress());

            cnt.addComponent(BorderLayout.NORTH, top);
        }
    }

    private static void removeWaitingProgress(Form f) {
        removeWaitingProgress(f, f.getContentPane());
    }

    private static void removeWaitingProgress(Form f, Container pane) {
        Container cnt = f.getLayeredPane();
        cnt.removeAll();
        pane.setVisible(true);
    }

    private static void updateScreenFromNetwork(final Form f, final String tag) {
        //show a waiting progress on the Form
        addWaitingProgress(f);

        //run the networking on a background thread
        Display.getInstance().scheduleBackgroundTask(new Runnable() {

            public void run() {
                final List entries = ServerAccess.getEntriesFromFlickrService(tag);

                //build the UI entries on the EDT using the callSerially
                Display.getInstance().callSerially(new Runnable() {

                    public void run() {
                        Container cnt = f.getContentPane();
                        for (int i = 0; i < entries.size(); i++) {
                            Map data = (Map) entries.get(i);
                            cnt.addComponent(createEntry(data, i));
                        }
                        f.revalidate();
                        //remove the waiting progress from the Form
                        removeWaitingProgress(f);
                    }
                });

            }
        });

    }

}
