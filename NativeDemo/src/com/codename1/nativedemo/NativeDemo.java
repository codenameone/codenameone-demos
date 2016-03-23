package com.codename1.nativedemo;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import userclasses.StateMachine;

/**
 * This simple demo, demonstrates how to access OS specific native features.
 * Android API for Android and Objective C for iOS.
 * In this demo a native button component is been created and added to the screen.
 * 
 * IMPORTANT - Native components can be added to a CN1 UI, they are added as a
 * PeerComponent to the Form, usually when adding PeerComponents to the screen it 
 * is recommended not to add them to a scrollable screen to avoid strange behaviors. 
 * 
 * PeerComponent are useful when the underlying Component is very unique and can't 
 * be replicated easily with the CN1 Components such examples are: WebBrowser, 
 * Native Maps and Video Player.
 * 
 * Buttons are not a very good example for a useful PeerComponent, but are simple 
 * enough to demo how to add such Components and how to access the native platform
 * 
 * @author Chen
 */
public class NativeDemo {
    
    private Form currentForm;
    
    public void init(Object context) {
        Toolbar.setGlobalToolbar(true);
        // Pro only feature, uncomment if you have a pro subscription
        //Log.bindCrashProtection(true);
    }

    public void start() {
        
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        new StateMachine("/nativeDemo");        
    }

    public void stop(){
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy(){
    }

}
