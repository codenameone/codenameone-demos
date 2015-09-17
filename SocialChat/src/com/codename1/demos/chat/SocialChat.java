package com.codename1.demos.chat;


import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.AccessToken;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.push.Push;
import com.codename1.push.PushCallback;
import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.MorphTransition;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.SwipeBackSupport;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.util.WeakHashMap;
import com.codename1.util.CaseInsensitiveOrder;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialChat implements PushCallback {
    private static final String PUSH_TOKEN = "----------------------------------";
    private static final String GCM_SENDER_ID = "------------------";
    private static final boolean ITUNES_PRODUCTION_PUSH = false;
    private static final String GCM_SERVER_API_KEY = "------------------------------";
    private static final String ITUNES_PRODUCTION_PUSH_CERT = "------------------------";
    private static final String ITUNES_PRODUCTION_PUSH_CERT_PASSWORD = "--------";
    private static final String ITUNES_DEVELOPMENT_PUSH_CERT = "-----------------------";
    private static final String ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD = "----------";
    
    private Form current;
    private Resources theme;

    private String fullName;
    private String uniqueId;
    private String imageURL;
    private static EncodedImage userPlaceholder; 
    private EncodedImage roundPlaceholder;
    private Image mask;
    private static String tokenPrefix;
    private java.util.List<String> recentContacts;
    private ContactData[] contacts;
    private Pubnub pb;
    private Image roundedMeImage;
    private final WeakHashMap<String, EncodedImage> roundedImagesOfFriends = new WeakHashMap<>();
    
    /**
     * Includes messages that received ACK notices from the receiver
     */
    private ArrayList<String> pendingAck = new ArrayList<>();
    
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        
        Style iconFontStyle = UIManager.getInstance().getComponentStyle("LargeIconFont");
        FontImage fnt = FontImage.create(" \ue80f ", iconFontStyle);
        userPlaceholder = fnt.toEncodedImage();
        mask = theme.getImage("rounded-mask.png");
        roundPlaceholder = EncodedImage.createFromImage(userPlaceholder.scaled(mask.getWidth(), mask.getHeight()).applyMask(mask.createMask()), false);
        fullName = Preferences.get("fullName", null);
        uniqueId = Preferences.get("uniqueId", null);
        imageURL = Preferences.get("imageURL", null);
        
        if(Storage.getInstance().exists("recentContacts")) {
            recentContacts = (java.util.List<String>)Storage.getInstance().readObject("recentContacts");
        }
        
        Util.register("Message", Message.class);

        Display.getInstance().addEdtErrorHandler((evt) -> {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            });
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        showLoginForm();

        // let the login form show before we register the push so the permission screen doesn't appear on a white
        // background
        Display.getInstance().callSerially(() -> {
            // registering for push after the UI appears
            Hashtable args = new Hashtable();
            args.put(com.codename1.push.Push.GOOGLE_PUSH_KEY, GCM_SENDER_ID);        
            Display.getInstance().registerPush(args, true);        
        });
    }

    private void showLoginForm() {
        Form loginForm = new Form();
        loginForm.getTitleArea().setUIID("Container");
        loginForm.setLayout(new BorderLayout());
        loginForm.setUIID("MainForm");
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS)); 
        cnt.setUIID("Padding");
        Button loginWithGoogle = new Button("Signin with Google");
        loginWithGoogle.setUIID("LoginButtonGoogle");
        Button loginWithFacebook = new Button("Signin with Facebook");
        loginWithFacebook.setUIID("LoginButtonFacebook");
        Style iconFontStyle = UIManager.getInstance().getComponentStyle("IconFont");
        loginWithFacebook.setIcon(FontImage.create(" \ue96c ", iconFontStyle));
        loginWithGoogle.setIcon(FontImage.create(" \ue976 ", iconFontStyle));
        cnt.addComponent(loginWithGoogle);
        cnt.addComponent(loginWithFacebook);
        loginWithGoogle.addActionListener((e) -> {
            Dialog.show("Test", "Test", "OK", null);
            tokenPrefix = "google";
            Login gc = GoogleConnect.getInstance();
            gc.setScope("profile email https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me");
            gc.setClientId("1013232201263-lf4aib14r7g6mln58v1e36ibhktd79db.apps.googleusercontent.com");
            gc.setRedirectURI("https://www.codenameone.com/oauth2callback");
            gc.setClientSecret("uvu03IXOhx9sO8iPcmDfuX3R");
            doLogin(gc, new GoogleData(), false);
        });
        loginWithFacebook.addActionListener((e) -> {
            tokenPrefix = "facebook";
            Login fb = FacebookConnect.getInstance();
            fb.setClientId("739727009469185");
            fb.setRedirectURI("http://www.codenameone.com/");
            fb.setClientSecret("4c4a7df81a8e9ab29ac4e38e6b9e4eb1");
            doLogin(fb, new FacebookData(), false);
        });
        loginForm.addComponent(BorderLayout.SOUTH, cnt);
        loginForm.show();
    }
    
    /**
     * Creates the title area that allows searching and "peeking" of the user avatar from the title area
     * @return the title component
     */
    private Component createTitleComponent(Form parent) {
        // we want the toolbar to be completely transparent, since we created it on the layered pane (using the true
        // argument in the constructor) it will flow in the UI
        parent.getToolbar().setUIID("Container");
        
        // we create 3 layers within the title, the region contains all the layers, the encspsulate includes the "me image"
        // which we want to protrude under the title area layer
        Container titleRegion = new Container(new LayeredLayout());
        Container titleEncapsulate = new Container(new BorderLayout());
        Container titleArea = new Container(new BorderLayout());
        
        // since the Toolbar is now transparent we assign the title area UIID to one of the layers within and the look 
        // is preserved, we make it translucent though so we can see what's underneath 
        titleArea.setUIID("TitleArea");
        titleArea.getUnselectedStyle().setBgTransparency(128);
        
        // We customize the title completely using a component, the "title" is just a label with the Title UIID
        Label title = new Label(parent.getTitle());
        title.setUIID("Title");
        titleArea.addComponent(BorderLayout.CENTER, title);
        
        // the search button allows us to search a large list of contacts rather easily
        Button search = createSearchButton(parent, title, titleArea, titleRegion);
        
        // we package everything in a container so we can replace the title area with a search button as needed
        Container cnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        titleArea.addComponent(BorderLayout.EAST, cnt);
        cnt.addComponent(search);        
        
        // this is the Me picture that protrudes downwards. We use a placeholder which is then replace by the URLImage
        // with the actual image. Notice that createMaskAdapter allows us to not just scale the image but also apply
        // a mask to it...
        roundedMeImage = URLImage.createToStorage(roundPlaceholder, "userImage", imageURL, URLImage.createMaskAdapter(mask));
        Label me = new Label(roundedMeImage);
        me.setUIID("UserImage");
        
        // the search icon and the "me" image are on two separate layers so we use a "dummy" component that we 
        // place in the search container to space it to the side and leave room for the "me" image
        Label spacer = new Label(" ");
        Container.setSameWidth(spacer, me);
        cnt.addComponent(spacer);
        
        Container iconLayer = new Container(new BorderLayout());
        titleEncapsulate.addComponent(BorderLayout.NORTH, titleArea);
        
        titleRegion.addComponent(titleEncapsulate);
        titleRegion.addComponent(iconLayer);
        iconLayer.addComponent(BorderLayout.EAST, me);
        
        return titleRegion;
    }
    
    private Button createSearchButton(Form parent, Label title, Container titleArea, Container titleRegion) {
        // we want the search feature to be based on the title style so it will "fit" but we need it to use the font defined 
        // by the icon font UIID so we merge both
        Style s = new Style(title.getUnselectedStyle());
        Style iconFontStyle = UIManager.getInstance().getComponentStyle("IconFont");
        s.setFont(iconFontStyle.getFont().derive(s.getFont().getHeight(), Font.STYLE_PLAIN));
        FontImage searchIcon = FontImage.create(" \ue806 ", s);
        FontImage cancelIcon = FontImage.create(" \ue81e ", s);
        
        // this is the actual search button, but we don't want it to have a border...
        Button search = new Button(searchIcon);
        search.setUIID("Label");
        
        // the search box will be placed in the title area so we can type right into it. We make it look like a title but
        // explicitly align it to the left for cases such as iOS where the title is centered by default
        TextField searchBox = new TextField();
        searchBox.setUIID("Title");
        searchBox.getUnselectedStyle().setAlignment(Component.LEFT);
        searchBox.getSelectedStyle().setAlignment(Component.LEFT);
        
        // the data change listener allows us to animate the data on every key press into the field
        searchBox.addDataChangeListener((type, index) -> {
            String text = searchBox.getText().toLowerCase();
            if(text.length() > 0) {
                Dimension hidden = new Dimension(0, 0);
                // iterates over the components, if a component matches its set to visible and its size is kept as default
                // otherwise the component is hidden and set to occupy no space.
                for(Component cmp : parent.getContentPane()) {
                    if(cmp instanceof MultiButton) {
                        String l1 = ((MultiButton)cmp).getTextLine1();
                        if(l1.toLowerCase().indexOf(text) > -1) {
                            cmp.setPreferredSize(null);
                            cmp.setVisible(true);
                        } else {
                            cmp.setPreferredSize(hidden);
                            cmp.setVisible(false);
                        }
                    } 
                }                    
            } else {
                // no search string, show all the components by resetting the preferred size to default (thru null) and making them visible
                for(Component cmp : parent.getContentPane()) {
                    cmp.setPreferredSize(null);
                    cmp.setVisible(true);
                }                    
            }
            
            // update the UI with an animation effect
            parent.getContentPane().animateLayout(200);
        });
        
        // the action event is invoked when the button is pressed, this can have 2 separate states: during search/before search
        search.addActionListener((e) -> {
            if(search.getIcon() == searchIcon) {
                // Starts the search operation by replacing the title with a text field and launching the native editing
                search.setIcon(cancelIcon);
                titleArea.replaceAndWait(title, searchBox, CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, true, 400));
                titleRegion.revalidate();
                Display.getInstance().editString(searchBox, searchBox.getMaxSize(), searchBox.getConstraint(), "");
            } else {
                // if we are currently searching then cancel the search, return all items to visible and restore everything
                search.setIcon(searchIcon);
                for(Component cmp : parent.getContentPane()) {
                    cmp.setPreferredSize(null);
                    cmp.setVisible(true);
                }                    
                parent.getContentPane().animateLayoutAndWait(200);                
                search.setEnabled(true);
                search.setVisible(true);
                titleArea.replaceAndWait(searchBox, title, CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, true, 400));
            }
        });        
        return search;
    }
    
    private ContactData getContactById(String id) {
        for(ContactData d : contacts) {
            if(d.uniqueId.equals(id) || (tokenPrefix + d.uniqueId).equals(id)) {
                return d;
            }
        }
        return null;
    }
    
    private MultiButton createContactComponent(ContactData d) {
        MultiButton mb = new MultiButton();
        mb.putClientProperty("uid", d.uniqueId);
        mb.setTextLine1(d.name);
        if(d.imageUrl != null) {
            mb.setIcon(URLImage.createToStorage(userPlaceholder, "userPic" + d.uniqueId, d.imageUrl, URLImage.RESIZE_SCALE_TO_FILL));
        } else {
            mb.setIcon(userPlaceholder);
        }
        mb.addActionListener((e) -> {
            showChatForm(d, mb);
        });
        return mb;
    }
    
    private void listenToMessages() {
        try {
            pb = new Pubnub("pub-c-f058310b-04b4-4797-a8eb-a5448fe77b2e", "sub-c-e9863cf0-3a14-11e5-a5d7-02ee2ddab7fe");
            pb.subscribe(tokenPrefix + uniqueId, new Callback() {
                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    if(message instanceof String) {
                        pendingAck.remove(channel);
                        return;
                    }
                    Message m = new Message((JSONObject)message);
                    pb.publish(tokenPrefix + m.getSenderId(),  "ACK", new Callback() {});
                    Display.getInstance().callSerially(() -> {
                        addMessage(m);
                        respond(m);
                    });
                }
            });
        } catch(PubnubException err) {
            Log.e(err);
            Dialog.show("Error", "There was a communication error: " + err, "OK", null);
        }
    }
    
    /**
     * Shows the form containing the list of contacts we can chat with
     */
    void showContactsForm(UserData data) {
        listenToMessages();
        Form contactsForm = new Form("Contacts");
        contactsForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        // the toolbar is created into a layer on the content pane. This allows us to render behind it and leave it semi transparent
        Toolbar tb = new Toolbar(true);
        
        // we want the title area to be transparent so it won't get in the way
        contactsForm.getTitleArea().setUIID("Container");

        // folds the toolbar automatically as we scroll down, shows it if we scroll back up
        tb.setScrollOffUponContentPane(true);
        contactsForm.setToolBar(tb);
        
        // we want the image behind the toolbar to stretch and fit the entire screen and leave no margin
        Label titleLabel = new Label(" ");
        Style titleLabelStyle = titleLabel.getUnselectedStyle();
        titleLabelStyle.setBgImage(theme.getImage("social-chat-tutorial-image-top.jpg"));
        titleLabelStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        titleLabelStyle.setPadding(tb.getPreferredH(), tb.getPreferredH(), tb.getPreferredH(), tb.getPreferredH());
        titleLabelStyle.setPaddingUnit(Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS);
        titleLabelStyle.setMargin(0, 0, 0, 0);
        
        contactsForm.addComponent(titleLabel);
        
        // the behavior of the title is rather complex so we extract it to a separate method
        tb.setTitleComponent(createTitleComponent(contactsForm));
                
        InfiniteProgress ip = new InfiniteProgress();
        contactsForm.addComponent(ip);
        
        loadContacts(data, ip, contactsForm.getContentPane());
        
        // creates the morph and other animations from the main form to the second form of the app
        createMorphEffect(titleLabel);
        
        contactsForm.show();
        showPendingMessages(contactsForm);
    }
    
    private void showPendingMessages(Form f) {
        if(Storage.getInstance().exists("pendingMessages")) {
            java.util.List<Message> pendingMessages = (java.util.List<Message>)Storage.getInstance().readObject("pendingMessages");
            Message m = pendingMessages.get(0);
            pendingMessages.remove(0);
            respond(m);
            if(pendingMessages.size() == 0) {
                Storage.getInstance().deleteStorageFile("pendingMessages");
            } else {
                Storage.getInstance().writeObject("pendingMessages", pendingMessages);
                UITimer uit = new UITimer(() -> {
                    showPendingMessages(f);
                });
                uit.schedule(3500, false, f);
            }
        }
    }
    
    private void loadContacts(UserData data, InfiniteProgress ip, Container contactsContainer) {
        // we sort the contacts by name which is pretty concise code thanks to Java 8 lambdas
        Display.getInstance().scheduleBackgroundTask(() -> {
            contacts = data.getContacts();
            CaseInsensitiveOrder co = new CaseInsensitiveOrder();
            Arrays.sort(contacts, (ContactData o1, ContactData o2) -> {
                return co.compare(o1.name, o2.name);
            });

            Display.getInstance().callSerially(() -> {
                if(recentContacts != null && recentContacts.size() > 0) {
                    Label recentHeader = new Label("Recent");
                    recentHeader.setUIID("ContactsHeader");
                    contactsContainer.addComponent(recentHeader);

                    for(String cont : recentContacts) {
                        ContactData d = getContactById(cont);
                        contactsContainer.addComponent(createContactComponent(d));
                    }

                    Label allHeader = new Label("All Contacts");
                    allHeader.setUIID("ContactsHeader");
                    contactsContainer.addComponent(allHeader);
                }

                contactsContainer.removeComponent(ip);

                for(ContactData d : contacts) {
                    contactsContainer.addComponent(createContactComponent(d));
                }        
                contactsContainer.revalidate();
            });
        });
    }
    
    private void createMorphEffect(Label titleLabel) {
        // animate the components out of the previous form if we are coming in from the login form
        Form parent = Display.getInstance().getCurrent();
        if(parent.getUIID().equals("MainForm")) {
            for(Component cmp : parent.getContentPane()) {
                cmp.setX(parent.getWidth());
            }
            
            // moves all the components outside of the content pane to the right while fading them out over 400ms
            parent.getContentPane().animateUnlayoutAndWait(400, 0);
            parent.getContentPane().removeAll();
            
            // we can't mutate a form into a component in another form so we need to convert the background to an image and then morph that
            // this is especially easy since we already removed all the components
            Label dummy = new Label();
            dummy.setShowEvenIfBlank(true);
            dummy.setUIID("Container");
            dummy.setUnselectedStyle(new Style(parent.getUnselectedStyle()));
            parent.setUIID("Form");
            
            // special case to remove status bar on iOS 7
            parent.getTitleArea().removeAll();
            parent.setLayout(new BorderLayout());
            parent.addComponent(BorderLayout.CENTER, dummy);
            parent.revalidate();
            
            // animate the main panel to the new location at the top title area of the screen
            dummy.setName("fullScreen");
            titleLabel.setName("titleImage");
            parent.setTransitionOutAnimator(MorphTransition.create(1100).morph("fullScreen", "titleImage"));
        }
    }
    
    private EncodedImage getRoundedFriendImage(String uid, String imageUrl) {
        EncodedImage roundedHimOrHerImage = roundedImagesOfFriends.get(uid);
        if(roundedHimOrHerImage == null) {
            roundedHimOrHerImage = URLImage.createToStorage(roundPlaceholder, "rounded" + uid, imageUrl, URLImage.createMaskAdapter(mask));
            roundedImagesOfFriends.put(uid, roundedHimOrHerImage);
        }
        return roundedHimOrHerImage;
    }
    
    void showChatForm(ContactData d, Component source) {
        Form chatForm = new Form(d.name);

        // this identifies the person we are chatting with, so an incoming message will know if this is the right person...
        chatForm.putClientProperty("cid", tokenPrefix + d.uniqueId);
        chatForm.setLayout(new BorderLayout());
        Toolbar tb = new Toolbar();
        final Container chatArea = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        chatArea.setScrollableY(true);
        chatArea.setName("ChatArea");
        chatForm.setToolBar(tb);
        chatForm.setBackCommand(new Command("Contacts") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                source.getComponentForm().showBack();
            }
        });
        
        // Provides the ability to swipe the screen to go back to the previous form
        SwipeBackSupport.bindBack(chatForm, (args) -> {
            return source.getComponentForm();
        });
        
        // Gets a rounded version of our friends picture and caches it
        Image roundedHimOrHerImage = getRoundedFriendImage(d.uniqueId, d.imageUrl);

        // load the stored messages and add them to the form
        java.util.List<Message> messages = (java.util.List<Message>)Storage.getInstance().readObject(tokenPrefix + d.uniqueId);
        if(messages != null) {
            for(Message m : messages) {
                if(m.getRecepientId().equals(tokenPrefix + uniqueId)) {
                    respondNoLayout(chatArea, m.getMessage(), roundedHimOrHerImage);
                } else {
                    sayNoLayout(chatArea, m.getMessage());
                }
            }
        }
                
        // to place the image on the right side of the toolbar we just use a command that does nothing...
        Command himOrHerCommand = new Command("", roundedHimOrHerImage);
        tb.addCommandToRightBar(himOrHerCommand);
        
        // we type the message to the chat partner in the text field on the south side
        TextField write = new TextField(30);
        write.setHint("Write to " + d.name);
        chatForm.addComponent(BorderLayout.CENTER, chatArea);
        chatForm.addComponent(BorderLayout.SOUTH, write);
        
        // the action listener for the text field creates a message object, converts it to JSON and publishes it to the listener queue
        write.addActionListener((e) -> {
            String text = write.getText();
            final Component t = say(chatArea, text);
            
            // we make outgoing messages translucent to indicate that they weren't received yet
            t.getUnselectedStyle().setOpacity(120);
            write.setText("");
            
            final Message messageObject = new Message(tokenPrefix + uniqueId, tokenPrefix + d.uniqueId, imageURL, fullName, text);
            JSONObject obj = messageObject.toJSON();

            String pid = Preferences.get("pid-" + tokenPrefix + d.uniqueId, null);
            if(pid != null) {
                // if we have a push address for the contact we can send them a push if they aren't reachable...
                UITimer timeout = new UITimer(() -> {
                    if(true) { //pendingAck.contains(tokenPrefix + d.uniqueId)) {
                        pendingAck.remove(tokenPrefix + d.uniqueId);
                        // send two messages, one hidden with the data as JSON for parsing on the client
                        // the other one visible with the text that should appear to the user who isn't running
                        // the app, this will allow him to launch the app and then receive the hidden message immediately
                        // within the app
                        String cert = ITUNES_DEVELOPMENT_PUSH_CERT;
                        String pass = ITUNES_DEVELOPMENT_PUSH_CERT_PASSWORD;
                        if(ITUNES_PRODUCTION_PUSH) {
                            cert = ITUNES_PRODUCTION_PUSH_CERT;
                            pass = ITUNES_PRODUCTION_PUSH_CERT_PASSWORD;
                        }
                        if(Push.sendPushMessage(PUSH_TOKEN, text + ";" + obj.toString(), 
                                ITUNES_PRODUCTION_PUSH, GCM_SERVER_API_KEY, cert, pass, 3, pid)) {
                            t.getUnselectedStyle().setOpacity(255);
                            t.repaint();
                            addMessage(messageObject);
                        } else {
                            chatArea.removeComponent(t);
                            chatArea.revalidate();
                            Dialog.show("Error", "We couldn't reach " + d.name + " thru push", "OK", null);
                        }
                    } 
                });

                timeout.schedule(10000, false, write.getComponentForm());
                if(!pendingAck.contains(tokenPrefix + d.uniqueId)) {
                    pendingAck.add(tokenPrefix + d.uniqueId);
                }
            }

            pb.publish(tokenPrefix + d.uniqueId, obj, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    // a message was received, we make it opauqe and add it to the storage
                    t.getUnselectedStyle().setOpacity(255);
                    t.repaint();
                    addMessage(messageObject);
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    chatArea.removeComponent(t);
                    chatArea.revalidate();
                    Dialog.show("Error", "Connection error message wasn't sent", "OK", null);
                }                    
            });
        });

        chatForm.show();
    }
    
    private Component say(Container chatArea, String text) {
        Component t = sayNoLayout(chatArea, text);
        t.setY(chatArea.getHeight());
        t.setWidth(chatArea.getWidth());
        t.setHeight(40);
        chatArea.animateLayoutAndWait(300);        
        chatArea.scrollComponentToVisible(t);
        return t;
    }

    private Component sayNoLayout(Container chatArea, String text) {
        SpanLabel t = new SpanLabel(text);
        t.setIcon(roundedMeImage);
        t.setTextBlockAlign(Component.LEFT);
        t.setTextUIID("BubbleMe");
        chatArea.addComponent(t);
        return t;
    }

    private Container getChatArea(Container cnt) {
        String n = cnt.getName();
        if(n != null && n.equals("ChatArea")) {
            return cnt;
        }
        
        for(Component cmp : cnt) {
            if(cmp instanceof Container) {
                Container cur = getChatArea((Container)cmp);
                if(cur != null) {
                    return cur;
                }
            }
        }
        return null;
    }

    /**
     * Stores the given message into the permanent storage
     */
    private void addMessage(Message m) {
        String personId;
        
        // if this is a message to me then store based on sender otherwise store based on recepient
        if(m.getRecepientId().equals(tokenPrefix + uniqueId)) {
            personId = m.getSenderId();
        } else {
            personId = m.getRecepientId();
        }
        java.util.List messages = (java.util.List)Storage.getInstance().readObject(personId);
        if(messages == null) {
            messages = new ArrayList();
        }
        messages.add(m);
        Storage.getInstance().writeObject(personId, messages);
    }
    
    private void respond(Message m) {
        String clientId = (String)Display.getInstance().getCurrent().getClientProperty("cid");
        EncodedImage rounded = getRoundedFriendImage(m.getSenderId(), m.getPicture());
        if(clientId == null || !clientId.equals(m.getSenderId())) {
            // show toast, we aren't in the chat form...
            InteractionDialog toast = new InteractionDialog();
            toast.setUIID("Container");
            toast.setLayout(new BorderLayout());

            SpanButton messageButton = new SpanButton(m.getMessage());
            messageButton.setIcon(rounded);

            toast.addComponent(BorderLayout.CENTER, messageButton);
            int h = toast.getPreferredH();
            toast.show(Display.getInstance().getDisplayHeight() - h - 10, 10, 10, 10);
            UITimer uit = new UITimer(() -> {
                toast.dispose();
            });
            uit.schedule(3000, false, Display.getInstance().getCurrent());

            messageButton.addActionListener((e) -> {
                uit.cancel();
                toast.dispose();
                showChatForm(getContactById(m.getSenderId()), Display.getInstance().getCurrent());
            });
        } else {
            Container chatArea = getChatArea(Display.getInstance().getCurrent().getContentPane());
            respond(chatArea, m.getMessage(), rounded);
        }
    }
    
    private void respond(Container chatArea, String text, Image roundedHimOrHerImage) {
        Component answer = respondNoLayout(chatArea, text, roundedHimOrHerImage);
        answer.setX(chatArea.getWidth());
        answer.setWidth(chatArea.getWidth());
        answer.setHeight(40);
        chatArea.animateLayoutAndWait(300);
        chatArea.scrollComponentToVisible(answer);
    }

    private Component respondNoLayout(Container chatArea, String text, Image roundedHimOrHerImage) {
        SpanLabel answer = new SpanLabel(text);
        answer.setIcon(roundedHimOrHerImage);
        answer.setIconPosition(BorderLayout.EAST);
        answer.setTextUIID("BubbleThem");
        answer.setTextBlockAlign(Component.RIGHT);
        chatArea.addComponent(answer);        
        return answer;
    }
    
    void doLogin(Login lg, UserData data, boolean forceLogin) {
        if(!forceLogin) {
            if(lg.isUserLoggedIn()) {
                showContactsForm(data);
                return;
            }

            // if the user already logged in previously and we have a token
            String t = Preferences.get(tokenPrefix + "token", (String)null);
            if(t != null) {
                // we check the expiration of the token which we previously stored as System time
                long tokenExpires = Preferences.get(tokenPrefix + "tokenExpires", (long)-1);
                if(tokenExpires < 0 || tokenExpires > System.currentTimeMillis()) {
                    // we are still logged in
                    showContactsForm(data);
                    return;
                } 
            }
        }
        lg.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
                Dialog.show("Error Logging In", "There was an error logging in: " + errorMessage, "OK", null);
            }

            @Override
            public void loginSuccessful() {
                // when login is successful we fetch the full data
                data.fetchData(lg.getAccessToken().getToken(), ()-> {
                    // we store the values of result into local variables
                    uniqueId = data.getId();
                    fullName = data.getName();
                    imageURL = data.getImage();
                    
                    // we then store the data into local cached storage so they will be around when we run the app next time
                    Preferences.set("fullName", fullName);
                    Preferences.set("uniqueId", uniqueId);
                    Preferences.set("imageURL", imageURL);
                    Preferences.set(tokenPrefix + "token", lg.getAccessToken().getToken());
                    
                    // token expiration is in seconds from the current time, we convert it to a System.currentTimeMillis value so we can
                    // reference it in the future to check expiration
                    Preferences.set(tokenPrefix + "tokenExpires", tokenExpirationInMillis(lg.getAccessToken()));
                    showContactsForm(data);
                });
            }
        });
        lg.doLogin();
    }
    
    /**
     * token expiration is in seconds from the current time, we convert it to a System.currentTimeMillis value so we can
     * reference it in the future to check expiration
     */
    long tokenExpirationInMillis(AccessToken token) {
        String expires = token.getExpires();
        if(expires != null && expires.length() > 0) {
            try {
                // when it will expire in seconds
                long l = (long)(Float.parseFloat(expires) * 1000);
                return System.currentTimeMillis() + l;
            } catch(NumberFormatException err) {
                // ignore invalid input
            }
        }
        return -1;
    } 
    
    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
        if(tokenPrefix != null && uniqueId != null) {
            pb.unsubscribe(tokenPrefix + uniqueId);
        }
    }

    @Override
    public void push(String value) {
        // its a JSON message, otherwise its a notice to the user
        if(value.startsWith("{") || value.startsWith("[")) {
            try {
                JSONObject obj = new JSONObject(value);
                
                // this is still early since we probably didn't login yet so add the messages to the list of pending messages
                java.util.List<Message> pendingMessages = (java.util.List<Message>)Storage.getInstance().readObject("pendingMessages");
                if(pendingMessages == null) {
                    pendingMessages = new ArrayList<>();
                }
                Message m = new Message(obj);
                pendingMessages.add(m);
                Storage.getInstance().writeObject("pendingMessages", pendingMessages);
                addMessage(m);
            } catch(JSONException err) {
                err.printStackTrace();
            }
        }
    }

    @Override
    public void registeredForPush(String deviceId) {
    }

    @Override
    public void pushRegistrationError(String error, int errorCode) {
    }

    static final class ContactData {
        public String uniqueId;
        public String name;
        public String imageUrl;
    }
    
    static interface UserData {
        public String getName();
        public String getId();
        public String getImage();
        public void fetchData(String token, Runnable callback);
        public ContactData[] getContacts();
    }
    
    class FacebookData implements UserData {
        String name;
        String id;
        String token;
        
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getImage() {
            return "http://graph.facebook.com/v2.4/" + id + "/picture";
        }

        @Override
        public void fetchData(String token, Runnable callback) {
            this.token = token;
            ConnectionRequest req = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> parsed = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
                    name = (String) parsed.get("name");
                    id = (String) parsed.get("id");
                }

                @Override
                protected void postResponse() {
                    callback.run();
                }

                @Override
                protected void handleErrorResponseCode(int code, String message) {
                    //access token not valid anymore
                    if(code >= 400 && code <= 410){
                        doLogin(FacebookConnect.getInstance(), FacebookData.this, true);
                        return;
                    }
                    super.handleErrorResponseCode(code, message);
                }
            };
            req.setPost(false);
            req.setUrl("https://graph.facebook.com/v2.4/me");
            req.addArgumentNoEncoding("access_token", token);
            NetworkManager.getInstance().addToQueue(req);
        }

        @Override
        public ContactData[] getContacts() {
            ArrayList<ContactData> dat = new ArrayList<>();
            ConnectionRequest req = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> parsed = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
                    //name = (String) parsed.get("name");
                    java.util.List<Object> data = (java.util.List<Object>)parsed.get("data");
                    for(Object current : data) {
                        Map<String, Object> cMap = (Map<String, Object>)current;
                        String name = (String)cMap.get("name");
                        if(name == null) {
                            continue;
                        }
                        String id = cMap.get("id").toString();
                        ContactData cd = new ContactData();
                        cd.name = name;
                        cd.uniqueId = id;
                        cd.imageUrl = "http://graph.facebook.com/v2.4/" + id + "/picture";
                        dat.add(cd);
                    }
                }

                @Override
                protected void handleErrorResponseCode(int code, String message) {
                    //access token not valid anymore
                    if(code >= 400 && code <= 410){
                        doLogin(FacebookConnect.getInstance(), FacebookData.this, true);
                        return;
                    }
                    super.handleErrorResponseCode(code, message);
                }
            };
            req.setPost(false);
            req.setUrl("https://graph.facebook.com/v2.4/me/friends");
            if(token == null) {
                token = Preferences.get("facebooktoken", (String)null);
            }
            req.addArgumentNoEncoding("access_token", token);
            NetworkManager.getInstance().addToQueueAndWait(req);

            ContactData[] cd = new ContactData[dat.size()];
            dat.toArray(cd);
            return cd;
        }            
    }
    
    class GoogleData extends ConnectionRequest implements UserData {
        private Runnable callback;
        private Map<String, Object> parsedData;
        
        @Override
        public String getName() {
            return (String) parsedData.get("displayName");
        }

        @Override
        public String getId() {
            return parsedData.get("id").toString();
        }

        @Override
        public String getImage() {
            Map<String, Object> imageMeta = ((Map<String, Object>) parsedData.get("image"));
            return (String)imageMeta.get("url");
        }

        @Override
        public void fetchData(String token, Runnable callback) {
            this.callback = callback;
            addRequestHeader("Authorization", "Bearer " + token);
            setUrl("https://www.googleapis.com/plus/v1/people/me");
            setPost(false);
            NetworkManager.getInstance().addToQueue(this);
        }
        
        @Override
        protected void handleErrorResponseCode(int code, String message) {
            //access token not valid anymore
            if(code >= 400 && code <= 410){
                doLogin(GoogleConnect.getInstance(), this, true);
                return;
            }
            super.handleErrorResponseCode(code, message);
        }
        

        @Override
        protected void readResponse(InputStream input) throws IOException {
            JSONParser parser = new JSONParser();
            parsedData = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
        }

        @Override
        protected void postResponse() {
            callback.run();
        }

        private String token;
        
        @Override
        public ContactData[] getContacts() {
            ArrayList<ContactData> dat = new ArrayList<>();
            ConnectionRequest req = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    JSONParser parser = new JSONParser();
                    Map<String, Object> parsed = parser.parseJSON(new InputStreamReader(input, "UTF-8"));
                    java.util.List<Object> data = (java.util.List<Object>)parsed.get("items");
                    for(Object current : data) {
                        Map<String, Object> cMap = (Map<String, Object>)current;
                        String name = (String)cMap.get("displayName");
                        if(name == null) {
                            continue;
                        }
                        String type =(String)cMap.get("objectType");
                        if(!type.equals("person")) {
                            continue;
                        }
                        String id = cMap.get("id").toString();
                        ContactData cd = new ContactData();
                        cd.name = name;
                        cd.uniqueId = id;
                        
                        if(cMap.containsKey("image")) {
                            cd.imageUrl = (String)((Map<String, Object>)cMap.get("image")).get("url");;
                        }
                        
                        dat.add(cd);
                    }
                }

                @Override
                protected void handleErrorResponseCode(int code, String message) {
                    //access token not valid anymore
                    if(code >= 400 && code <= 410){
                        doLogin(GoogleConnect.getInstance(), GoogleData.this, true);
                        return;
                    }
                    super.handleErrorResponseCode(code, message);
                }
            };
            req.setPost(false);
            req.setUrl("https://www.googleapis.com/plus/v1/people/me/people/visible");
            if(token == null) {
                token = Preferences.get("googletoken", (String)null);
            }
            req.addRequestHeader("Authorization", "Bearer " + token);
            NetworkManager.getInstance().addToQueueAndWait(req);

            ContactData[] cd = new ContactData[dat.size()];
            dat.toArray(cd);
            return cd;
        }
    }
}
