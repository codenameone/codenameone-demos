package com.codename1.demos.signature;


import com.codename1.components.SignatureComponent;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

public class SignatureComponentDemo {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Signature Component");
        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        hi.add("Enter Your Name:");
        hi.add(new TextField());
        hi.add("Signature:");
        SignatureComponent sig = new SignatureComponent();
        sig.addActionListener((evt)-> {
            System.out.println("The signature was changed");
            Image img = sig.getSignatureImage();
            // Now we can do whatever we want with the image of this signature.
        });
        hi.addComponent(sig);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

}
