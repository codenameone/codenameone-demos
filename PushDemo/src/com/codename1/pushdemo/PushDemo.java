package com.codename1.pushdemo;


import com.codename1.push.Push;
import com.codename1.push.PushCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class PushDemo implements PushCallback {
    private StateMachine s;
    private Form current;

    public void init(Object context) {
    }

    public void start() {
        if(current != null){
            current.show();
            return;
        }
        s = new StateMachine("/theme");        
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    public void push(String value) {
        Dialog.show("Push Received", value, "OK", null);
    }

    public void registeredForPush(String deviceId) {
        Dialog.show("Push Registered", "Device ID: " + deviceId + "\nDevice Key: " + Push.getDeviceKey() , "OK", null);
        String k = Push.getDeviceKey();
        if(k != null) {
            s.findDeviceKey().setText("Device Key: " + k);
        }
    }

    public void pushRegistrationError(String error, int errorCode) {
        Dialog.show("Registration Error", "Error " + errorCode + "\n" + error, "OK", null);
    }
}
