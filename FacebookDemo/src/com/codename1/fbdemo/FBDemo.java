package com.codename1.fbdemo;

import com.codename1.components.FileTree;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ShareButton;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.social.FacebookConnect;
import com.codename1.social.LoginCallback;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class FBDemo {

    private Form main;
    private static Resources theme;
    private Command back = new Command("Back") {

        public void actionPerformed(ActionEvent evt) {
            killNetworkAccess();
            main.showBack();
        }
    };

    public void init(Object context) {
        System.out.println("init");
        try {
            theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("started");
        main = new Form("Facebook Demo");
        main.setScrollable(false);
        main.setLayout(new BorderLayout());

        final Command profileCommand = new Command("My Profile") {

            public void actionPerformed(ActionEvent evt) {
                main.getContentPane().removeAll();
                main.addComponent(BorderLayout.CENTER, showMyProfile());
                main.revalidate();
            }
        };

        main.addCommand(profileCommand);

        Command c = new Command("FAVORITES");
        Label l = new Label("FAVORITES") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l.setUIID("Separator");
        c.putClientProperty("SideComponent", l);
        main.addCommand(c);

        main.addCommand(new Command("My Friends", theme.getImage("all friends.png")) {

            public void actionPerformed(ActionEvent evt) {
                main.getContentPane().removeAll();
                main.addComponent(BorderLayout.CENTER, showMyFriends());
                main.revalidate();
            }
        });

        main.addCommand(new Command("News", theme.getImage("friend feeds.png")) {

            public void actionPerformed(ActionEvent evt) {
                main.getContentPane().removeAll();
                main.addComponent(BorderLayout.CENTER, showNews());
                main.revalidate();
            }
        });

        main.addCommand(new Command("Upload photo", theme.getImage("photos icon.png")) {

            public void actionPerformed(ActionEvent evt) {
                main.getContentPane().removeAll();
                main.addComponent(BorderLayout.CENTER, uploadPhoto());
                main.revalidate();
            }
        });

        main.addCommand(new Command("Share", theme.getImage("wall post.png")) {

            public void actionPerformed(ActionEvent evt) {
                main.getContentPane().removeAll();
                main.addComponent(BorderLayout.CENTER, showShare());
                main.revalidate();
            }
        });

        Command c1 = new Command("ACTIONS");
        Label l1 = new Label("ACTIONS") {

            public void paint(Graphics g) {
                super.paint(g);
                g.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
            }
        };
        l1.setUIID("Separator");
        c1.putClientProperty("SideComponent", l1);
        main.addCommand(c1);

        main.addCommand(new Command("Exit") {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
        });
        main.addCommand(new Command("Logout") {

            public void actionPerformed(ActionEvent evt) {
                if(FacebookConnect.getInstance().isFacebookSDKSupported()) {
                    FacebookConnect.getInstance().logout();
                } else {
                    FaceBookAccess.getInstance().logOut();
                    Login.login(main);
                }
            }
        });
        main.show();
        
        if(FacebookConnect.getInstance().isFacebookSDKSupported()) {
            if(!FacebookConnect.getInstance().isLoggedIn()) {
                FacebookConnect.getInstance().setCallback(new LoginCallback() {
                    public void loginSuccessful() {
                        updateLoginPhoto();
                    }
                });
                FacebookConnect.getInstance().login();
            }
        } else {
            Login.login(main);
            updateLoginPhoto();
        }
    }

    public void updateLoginPhoto() {
        if (!FacebookConnect.getInstance().isFacebookSDKSupported() && Login.firstLogin()) {
            new Thread() {

                public void run() {
                    while (Login.firstLogin()) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    Display.getInstance().callSerially(new Runnable() {

                        public void run() {
                            updateLoginPhoto();
                        }
                    });
                }
            }.start();
            return;
        }
        final User me = new User();
        try {
            FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    try {
                        FaceBookAccess.getInstance().getPhotoThumbnail(me.getId(), new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                if (evt != null) {
                                    Image src = (Image) ((NetworkEvent) evt).getMetaData();
                                    if (src != null) {
                                        Command pc = new Command("", src) {

                                            public void actionPerformed(ActionEvent evt) {
                                                main.getContentPane().removeAll();
                                                main.addComponent(BorderLayout.CENTER, showMyProfile());
                                                main.revalidate();
                                            }
                                        };
                                        main.addCommand(pc, main.getCommandCount());
                                    }
                                }
                            }
                        }, true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("stopped");
    }

    public void destroy() {
        System.out.println("destroyed");

    }

    private Component uploadPhoto() {
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        final Container c = new Container(bl);
        c.addComponent(BorderLayout.CENTER, new Button(new Command("Pick Photo") {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().openImageGallery(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        if (evt == null) {
                            return;
                        }
                        String filename = (String) evt.getSource();
                        if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                            MultipartRequest req = new MultipartRequest();
                            String endpoint;
                            if(FacebookConnect.getInstance().isFacebookSDKSupported()) {
                                endpoint = "https://graph.facebook.com/me/photos?access_token=" + FacebookConnect.getInstance().getToken();
                            } else {
                                endpoint = "https://graph.facebook.com/me/photos?access_token=" + Login.TOKEN;
                            }
                            req.setUrl(endpoint);
                            req.addArgument("message", "test");
                            InputStream is = null;
                            try {
                                is = FileSystemStorage.getInstance().openInputStream(filename);
                                req.addData("source", is, FileSystemStorage.getInstance().getLength(filename), "image/jpeg");
                                NetworkManager.getInstance().addToQueue(req);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }

                    }
                });

            }
        }));
        return c;
    }

    private Component showMyProfile() {
        final Container c = new Container(new BorderLayout());
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        Container p = new Container(bl);
        p.addComponent(BorderLayout.CENTER, new InfiniteProgress());

        c.addComponent(BorderLayout.CENTER, p);

        final User me = new User();
        try {
            FaceBookAccess.getInstance().getUser("me", me, new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    int leftCol = Display.getInstance().getDisplayWidth() / 3;
                    ComponentGroup gr = new ComponentGroup();
                    gr.setLayout(new GridLayout(4, 1));
                    gr.addComponent(getPairContainer("Name", me.getName(), leftCol));
                    gr.addComponent(getPairContainer("Birthday", me.getBirthday(), leftCol));
                    gr.addComponent(getPairContainer("Status", me.getRelationship_status(), leftCol));
                    c.removeAll();
                    c.addComponent(BorderLayout.CENTER, gr);

                    Image i = getTheme().getImage("fbuser.jpg");
                    Container imageCnt = new Container(new BorderLayout());
                    Label myPic = new Label(i);
                    imageCnt.addComponent(BorderLayout.NORTH, myPic);
                    c.addComponent(BorderLayout.EAST, imageCnt);
                    c.revalidate();
                    try {
                        FaceBookAccess.getInstance().getPicture(me.getId(), myPic, null, true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    private Component showMyFriends() {
        final Container c = new Container(new BorderLayout());
        c.setScrollable(false);
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        Container p = new Container(bl);
        p.addComponent(BorderLayout.CENTER, new InfiniteProgress());

        c.addComponent(BorderLayout.CENTER, p);
        final List myFriends = new List();
        myFriends.setRenderer(new FriendsRenderer());
        try {
            FaceBookAccess.getInstance().getUserFriends("me", (DefaultListModel) myFriends.getModel(), new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    c.removeAll();
                    c.addComponent(BorderLayout.CENTER, myFriends);
                    c.revalidate();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    private Component showNews() {
        final Container c = new Container(new BorderLayout());
        c.setScrollable(false);
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        Container p = new Container(bl);
        p.addComponent(BorderLayout.CENTER, new InfiniteProgress());

        c.addComponent(BorderLayout.CENTER, p);
        final List news = new List();
        news.addPullToRefresh(new Runnable() {

            public void run() {

                Display.getInstance().invokeAndBlock(new Runnable() {

                    public void run() {
                        final DefaultListModel update = new DefaultListModel();
                        final Object lock = new Object();
                        try {
                            FaceBookAccess.getInstance().getNewsFeed("me", (DefaultListModel) update, new ActionListener() {

                                public void actionPerformed(ActionEvent evt) {
                                    news.setModel(update);

                                    synchronized (lock) {
                                        lock.notifyAll();
                                    }
                                }
                            });
                        } catch (IOException ex) {
                        }
                        synchronized (lock) {
                            try {
                                lock.wait();
                            } catch (Exception e) {
                            }
                        }
                    }
                });



            }
        });
        news.setRenderer(new WallRenderer(false));
        try {
            FaceBookAccess.getInstance().getNewsFeed("me", (DefaultListModel) news.getModel(), new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    c.removeAll();
                    c.addComponent(BorderLayout.CENTER, news);
                    c.revalidate();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    private Component showShare() {
        final Container c = new Container(new BorderLayout());
        final ShareButton share = new ShareButton();
        final TextArea t = new TextArea("Sharing on Facebook with CodenameOne is a breeze.\n"
                + "http://www.codenameone.com\n"
                + "(Sent from the facebook demo app)");
        t.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                share.setTextToShare(t.getText());
            }
        });
        c.addComponent(BorderLayout.CENTER, t);
        share.setTextToShare(t.getText());
        Container cnt = new Container(new BorderLayout());
        cnt.addComponent(BorderLayout.SOUTH, share);
        c.addComponent(BorderLayout.EAST, cnt);
        return c;
    }

    static Resources getTheme() {
        return theme;
    }

    private Container getPairContainer(String key, String val, int padding) {
        Label keyLabel = new Label(key);
        keyLabel.setUIID("Header");
        keyLabel.setPreferredW(padding);
        keyLabel.getStyle().setAlignment(Component.RIGHT);
        Label valLabel = new Label(val);
        valLabel.getStyle().setAlignment(Component.LEFT);
        Container cnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cnt.addComponent(keyLabel);
        cnt.addComponent(valLabel);
        return cnt;
    }

    private void killNetworkAccess() {
        Enumeration e = NetworkManager.getInstance().enumurateQueue();
        while (e.hasMoreElements()) {
            ConnectionRequest r = (ConnectionRequest) e.nextElement();
            r.kill();
        }

    }
}
