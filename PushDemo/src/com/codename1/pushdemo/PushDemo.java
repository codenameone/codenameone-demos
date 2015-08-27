package com.codename1.pushdemo;


import com.codename1.io.Log;
import com.codename1.messaging.Message;
import com.codename1.push.Push;
import com.codename1.push.PushCallback;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
        
        Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });
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
        Dialog.show("Push Registered", "Device ID: " + deviceId + "\nDevice Key: " + Push.getPushKey(), "OK", null);
        String k = Push.getPushKey();
        if(k != null) {
            s.findDeviceKey().setText("Device Key: " + k);
        }
        
        Message m = new Message(Push.getPushKey());
        Display.getInstance().sendMessage(new String[] {"shai@codenameone.com"}, "Push key", m);
    }

    public void pushRegistrationError(String error, int errorCode) {
        Dialog.show("Registration Error", "Error " + errorCode + "\n" + error, "OK", null);
    }
}
