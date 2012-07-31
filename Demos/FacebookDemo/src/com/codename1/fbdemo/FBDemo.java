package com.codename1.fbdemo;

import com.codename1.components.FileTree;
import com.codename1.components.ShareButton;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
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
        main.setLayout(new GridLayout(3, 2));
        Button me = new Button(new Command("My Profile") {

            public void actionPerformed(ActionEvent evt) {
                showMyProfile();
            }
        });
        main.addComponent(me);

        Button friends = new Button(new Command("My Friends") {

            public void actionPerformed(ActionEvent evt) {
                showMyFriends();
            }
        });
        main.addComponent(friends);

        Button wall = new Button(new Command("Wall") {

            public void actionPerformed(ActionEvent evt) {
                showMyWall();
            }
        });
        main.addComponent(wall);

        Button news = new Button(new Command("News") {

            public void actionPerformed(ActionEvent evt) {
                showNews();
            }
        });
        main.addComponent(news);
        
        Button upload = new Button(new Command("Upload photo") {

            public void actionPerformed(ActionEvent evt) {
                uploadPhoto();
            }
        });
        main.addComponent(upload);
        
        Button share = new Button(new Command("Share") {

            public void actionPerformed(ActionEvent evt) {
                showShare();
            }
        });
        main.addComponent(share);
        main.addCommand(new Command("Exit"){

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().exitApplication();
            }
            
        });
        main.show();
        Login.login(main);

    }

    public void stop() {
        System.out.println("stopped");
    }

    public void destroy() {
        System.out.println("destroyed");

    }
    
    private void uploadPhoto(){
        final String endpoint = "https://graph.facebook.com/me/photos?access_token="+Login.TOKEN;
        Form f = new Form("Choose a file");
        f.setScrollable(false);
        f.setLayout(new BorderLayout());
        FileTree ft = new FileTree();
        ft.addLeafListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String filename = (String)evt.getSource();
                if (Dialog.show("Send file?", filename, "OK", "Cancel")) {
                    MultipartRequest req = new MultipartRequest();
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
        f.addComponent(BorderLayout.CENTER, ft);
        f.addCommand(back);
        f.setBackCommand(back);
        f.show();
    }

    private void showMyProfile() {
        final Form f = new Form("My Profile");

        f.setLayout(new BorderLayout());
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
                    f.addComponent(BorderLayout.CENTER, gr);

                    Image i = getTheme().getImage("fbuser.jpg");
                    Container imageCnt = new Container(new BorderLayout());
                    Label myPic = new Label(i);
                    imageCnt.addComponent(BorderLayout.NORTH, myPic);
                    f.addComponent(BorderLayout.EAST, imageCnt);
                    
                    
                    f.revalidate();
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


        f.addCommand(back);
        f.setBackCommand(back);

        f.show();
    }

    private void showMyFriends() {
        Form f = new Form("My Friends");
        f.setScrollable(false);
        f.setLayout(new BorderLayout());
        List myFriends = new List();
        try {
            FaceBookAccess.getInstance().getUserFriends("me", (DefaultListModel) myFriends.getModel(), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        myFriends.setRenderer(new FriendsRenderer());
        f.addComponent(BorderLayout.CENTER, myFriends);
        f.addCommand(back);
        f.setBackCommand(back);

        f.show();
    }

    private void showMyWall() {
        Form f = new Form("My Wall");
        f.setScrollable(false);
        f.setLayout(new BorderLayout());
        List wall = new List();
        try {
            FaceBookAccess.getInstance().getWallFeed("me", (DefaultListModel) wall.getModel(), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        wall.setRenderer(new WallRenderer(true));
        f.addComponent(BorderLayout.CENTER, wall);
        
        
        f.addCommand(back);
        f.setBackCommand(back);

        f.show();
    }

    private void showNews() {
        Form f = new Form("News");
        f.setScrollable(false);
        f.setLayout(new BorderLayout());
        List news = new List();
        try {
            FaceBookAccess.getInstance().getNewsFeed("me", (DefaultListModel) news.getModel(), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        news.setRenderer(new WallRenderer(false));
        f.addComponent(BorderLayout.CENTER, news);
        
        
        f.addCommand(back);
        f.setBackCommand(back);

        f.show();
    }
    
    private void showShare() {
        Form f = new Form("Share");
        f.setLayout(new BorderLayout());
        final ShareButton share = new ShareButton();
        final TextArea t = new TextArea("Sharing on Facebook with CodenameOne is a breeze.\n"
                + "http://www.codenameone.com\n"
                + "(Sent from the facebook demo app)");
        t.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                share.setTextToShare(t.getText());
            }
        });
        f.addComponent(BorderLayout.CENTER, t);
        share.setTextToShare(t.getText());
        Container cnt = new Container(new BorderLayout());
        cnt.addComponent(BorderLayout.SOUTH, share);
        f.addComponent(BorderLayout.EAST, cnt);
        f.addCommand(back);
        f.setBackCommand(back);

        f.show();
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
