package com.codename1.apps.photoshare;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.EventDispatcher;
import com.codename1.ui.util.Resources;
import com.codename1.util.Callback;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PhotoShare {
    //public static final String SERVER_URL = "http://localhost:8080/PhotosServer/";
    public static final String SERVER_URL = "http://env-9666990.j.layershift.co.uk/";
    private static String THUMB_URL_PREFIX = SERVER_URL + "image?";
    private static final String IMAGE_URL_PREFIX = SERVER_URL + "image?i=";
    private static final String UPLOAD_URL = SERVER_URL + "upload";
    private EncodedImage placeholder;
    ImageList imageList;
    
    private Form current;
    Label likeCount;

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            placeholder = (EncodedImage)theme.getImage("thumbnail.png");
            THUMB_URL_PREFIX = THUMB_URL_PREFIX + "w=" + placeholder.getWidth() + "&h=" + placeholder.getHeight() + "&i=";
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });*/
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        if(Preferences.get("loggedIn", false)) {
            showMainForm();
            return;
        }
        showLoginForm();
    }
    
    private void showLoginForm() {
        Form login = new Form("Login");
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        ComponentGroup loginDetails = new ComponentGroup();
        
        TextField displayName = new TextField();
        displayName.setHint("Display Name");
        loginDetails.addComponent(displayName);

        final TextField email = new TextField();
        email.setHint("E-Mail");
        loginDetails.addComponent(email);

        Button send = new Button("Send Verification");
        loginDetails.addComponent(send);
        
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onSendAction(email);
            }
        });
        
        login.setLayout(bl);
        login.addComponent(BorderLayout.CENTER, loginDetails);
        login.show();
    }
    
    void onSendAction(final TextField email) {
        try {
            final String key = WebServiceProxy.createAccount(email.getText());

            Form keyInput = new Form("Verify Account");
            BorderLayout bl = new BorderLayout();
            bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
            ComponentGroup loginDetails = new ComponentGroup();

            final TextField actualKey = new TextField();
            actualKey.setHint("Key");
            loginDetails.addComponent(actualKey);
            actualKey.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if(actualKey.getText().equals(key)) {
                        // success!
                        Preferences.set("loggedIn", true);
                        Preferences.set("key", key);
                        Preferences.set("email", email.getText());
                        showMainForm();
                    }
                }
            });

            keyInput.setBackCommand(new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    showLoginForm();
                }
            });

            keyInput.setLayout(bl);
            keyInput.addComponent(BorderLayout.CENTER, loginDetails);
            keyInput.show();
        } catch(IOException err) {
            if(Dialog.show("Error", "We experienced an error connecting to the server", "Retry", "Exit")) {
                onSendAction(email);
            } else {
                Display.getInstance().exitApplication();
            }
        }
    }
    
    private void showMainForm() {
        final Form photos = new Form("Photos");
        photos.setLayout(new BorderLayout());
        GridLayout gr = new GridLayout(1, 1);
        final Container grid = new Container(gr);
        gr.setAutoFit(true);
        grid.setScrollableY(true);
        grid.addPullToRefresh(new Runnable() {
            public void run() {
                refreshGrid(grid);
            }
        });

        grid.addComponent(new InfiniteProgress());
        photos.addComponent(BorderLayout.CENTER, grid);

        photos.removeAllCommands();
        photos.setBackCommand(null);
        photos.addCommand(createPictureCommand(grid));

        photos.show();
        refreshGrid(grid);
    }

    private Command createPictureCommand(final Container grid) {
        return new Command("Take Picture") {
            public void actionPerformed(ActionEvent ev) {
                String picture = Capture.capturePhoto(1024, -1);
                if(picture == null) {
                    return;
                }
                MultipartRequest mp = new MultipartRequest() {
                    private long key;
                    @Override
                    protected void readResponse(InputStream input) throws IOException {
                        DataInputStream di = new DataInputStream(input);
                        key = di.readLong();
                    }

                    @Override
                    protected void postResponse() {
                        final Button btn = createImageButton(key, grid, imageList.getSize());
                        imageList.addImageId(key);
                        grid.addComponent(0, btn);
                        if(!animating) {
                            animating = true;
                            grid.animateLayoutAndWait(400);
                            animating = false;
                        }
                    }
                };
                mp.setUrl(UPLOAD_URL);
                try {
                    mp.addData("i", picture, "image/jpeg");
                    mp.addArgument("p", "Data;More data");
                    NetworkManager.getInstance().addToQueue(mp);
                } catch(IOException err) {
                    err.printStackTrace();
                }
            }
        };
    }
    
    private static boolean animating;
    private void refreshGrid(final Container grid) {
        WebServiceProxy.listPhotosAsync(new Callback<long[]>() {
            public void onSucess(long[] value) {
                grid.removeAll();
                imageList = new ImageList(value);
                for(int iter = 0 ; iter < value.length ; iter++) {
                    long p = value[iter];
                    final Button btn = createImageButton(p, grid, iter);
                    grid.addComponent(btn);
                }
                if(!animating) {
                    animating = true;
                    grid.animateLayoutAndWait(400);
                    animating = false;
                }
            }

            public void onError(Object sender, Throwable err, int errorCode, String errorMessage) {
                if(Dialog.show("Error", "There was an error fetching the images", "Retry", "Exit")) {
                    showMainForm();
                } else {
                    Display.getInstance().exitApplication();
                }
            }
        });
    }
    
    
    Button createImageButton(final long imageId, final Container grid, final int offset) {
        final Button btn = new Button(URLImage.createToFileSystem(placeholder, FileSystemStorage.getInstance().getAppHomePath() + "/Thumb_" + imageId, THUMB_URL_PREFIX + imageId, URLImage.RESIZE_SCALE_TO_FILL));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                imageList.setSelectedIndex(offset);
                final Container viewerParent = new Container(new LayeredLayout());
                ImageViewer viewer = new ImageViewer(imageList.getItemAt(offset));
                viewerParent.addComponent(viewer);
                Container parent = new Container(new BorderLayout());
                viewerParent.addComponent(parent);
                parent.addComponent(BorderLayout.SOUTH, createToolbar(imageId));
                
                likeCount = new Label("");
                parent.addComponent(BorderLayout.NORTH, likeCount);
                
                
                viewer.setImageList(imageList);
                grid.getParent().replace(grid, viewerParent, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 300));
                Display.getInstance().getCurrent().setBackCommand(createBackCommand(viewerParent, grid));
            }
        });
        return btn;
    }

    Container createToolbar(long imageId) {
        Container toolbar = new Container(new GridLayout(1, 4));
        toolbar.setUIID("Toolbar");
        // notice that the characters in the name of the buttons map to icons in the icon font!
        Button likeButton = new Button("d");
        Button flagButton = new Button("c");
        Button detailsButton = new Button("a");
        final ShareButton shareButton = new ShareButton();
        shareButton.setIcon(null);
        shareButton.setText("b");

        bindShareButtonSelectionListener(shareButton);

        likeButton.setUIID("ToolbarLabel");
        flagButton.setUIID("ToolbarLabel");
        detailsButton.setUIID("ToolbarLabel");
        shareButton.setUIID("ToolbarLabel");
        toolbar.addComponent(likeButton);
        toolbar.addComponent(shareButton);
        toolbar.addComponent(detailsButton);
        toolbar.addComponent(flagButton);

        likeButton.addActionListener(createLikeAction(imageId));
        detailsButton.addActionListener(createDetailsButtonActionListener(imageId));
        return toolbar;
    }
    
    ActionListener createLikeAction(final long imageId) {
        return new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        WebServiceProxy.likePhoto(imageId, Preferences.get("key", ""));
                    } catch(IOException err) {
                        err.printStackTrace();
                    }
                }
            };
    }
    
    void bindShareButtonSelectionListener(final ShareButton shareButton) {
        imageList.addSelectionListener(new SelectionListener() {
            public void selectionChanged(int oldSelected, int newSelected) {
                shareButton.setImageToShare(FileSystemStorage.getInstance().getAppHomePath() + "/Thumb_" + imageList.getSelectedImageId(), "image/jpeg");
            }
        });        
    }
    
    ActionListener createDetailsButtonActionListener(final long imageId) {
        return new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    InfiniteProgress ip = new InfiniteProgress();
                    Dialog dlg = ip.showInifiniteBlocking();
                    try {
                        String[] data = WebServiceProxy.getPhotoDetails(imageId);
                        String s = "";
                        for(String d : data) {
                            s += d;
                            s += "\n";
                        }
                        dlg.dispose();
                        Dialog.show("Data", s, "OK", null);
                    } catch(IOException err) {
                        dlg.dispose();
                        Dialog.show("Error", "Error connecting to server", "OK", null);
                    }
                }
            };
    }
    
    Command createBackCommand(final Container viewerParent, final Container grid) {
        return new Command("Back") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    viewerParent.getParent().replace(viewerParent, grid, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 300));
                    Form frm = Display.getInstance().getCurrent();
                    frm.setBackCommand(null);
                }
            };
    }
    
    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    class ImageList implements ListModel<Image> {
        private int selection;
        private long[] imageIds;
        private EncodedImage[] images;
        private EventDispatcher listeners = new EventDispatcher();

        public void addImageId(long id) {
            long[] n = new long[imageIds.length + 1];
            EncodedImage[] nImages = new EncodedImage[n.length];
            System.arraycopy(imageIds, 0, n, 0, imageIds.length);
            System.arraycopy(images, 0, nImages, 0, images.length);
            n[imageIds.length] = id;
            imageIds = n;
            images = nImages;
            listeners.fireDataChangeEvent(-1, DataChangedListener.ADDED);
        }
        
        public long getSelectedImageId() {
            return imageIds[selection];
        }
        
        public ImageList(long[] images) {
            this.imageIds = images;
            this.images = new EncodedImage[images.length];
        }
        
        public Image getItemAt(final int index) {
            if(images[index] == null) {
                images[index] = placeholder;
                Util.downloadUrlToStorageInBackground(IMAGE_URL_PREFIX + imageIds[index], "FullImage_" + imageIds[index], new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            images[index] = EncodedImage.create(Storage.getInstance().createInputStream("FullImage_" + imageIds[index]));
                            listeners.fireDataChangeEvent(index, DataChangedListener.CHANGED);
                        } catch(IOException err) {
                            err.printStackTrace();
                        }
                    }
                });
            } 
            return images[index];
        }

        public int getSize() {
            return imageIds.length;
        }

        public int getSelectedIndex() {
            return selection;
        }
        
        public void setSelectedIndex(int index) {
            WebServiceProxy.getPhotoLikesAsync(imageIds[selection], new Callback<Integer>() {
                public void onSucess(Integer value) {
                    if(likeCount != null) {
                        likeCount.setText("" + value);
                        likeCount.getParent().revalidate();
                    }
                }

                public void onError(Object sender, Throwable err, int errorCode, String errorMessage) {
                }
            });
            selection = index;
        }

        public void addDataChangedListener(DataChangedListener l) {
            listeners.addListener(l);
        }

        public void removeDataChangedListener(DataChangedListener l) {
            listeners.removeListener(l);
        }

        public void addSelectionListener(SelectionListener l) {
        }

        public void removeSelectionListener(SelectionListener l) {
        }

        public void addItem(Image item) {
        }

        public void removeItem(int index) {
        }
    }
}
