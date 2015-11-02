package com.mycompany.myapp;

import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.processing.Result;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class ReactDemo {
    private static final String REQUEST_URL = "https://raw.githubusercontent.com/facebook/react-native/master/docs/MoviesExample.json";
    private Form current;
    private EncodedImage placeholder;

    public void init(Object context) {
        UIManager.initFirstTheme("/theme");
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        placeholder = EncodedImage.createFromImage(Image.createImage(53, 81, 0), false);
        Form react = new Form("React Demo", new BorderLayout());
        react.add(BorderLayout.CENTER, new InfiniteContainer() {
            public Component[] fetchComponents(int index, int amount) {
                try {
                    Collection data = (Collection)ConnectionRequest.fetchJSON(REQUEST_URL).get("movies");
                    Component[] response = new Component[data.size()];
                    int offset = 0;
                    for(Object movie : data) {
                        response[offset] = createMovieEntry(Result.fromContent((Map)movie));
                        offset++;
                    }
                    return response;
                } catch(IOException err) {
                    Dialog.show("Error", "Error during connection: " + err, "OK", null);
                }
                return null;
            }
        });
        react.show();
    }
    
    Component createMovieEntry(Result data) {
        Container entry = BorderLayout.center(
                BoxLayout.encloseY(
                        new SpanLabel(data.getAsString("title"), "Line1"), 
                        new Label(data.getAsString("year"), "Line2"))).
                add(BorderLayout.WEST, 
                        URLImage.createToStorage(placeholder, data.getAsString("id"), 
                                    data.getAsString("posters/thumbnail")));
        return entry;
    } 

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
