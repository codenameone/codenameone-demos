package com.codename1.demo.chrome;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class Chrome {
    private Form current;
    public void init(Object context) {
    }

    public void start() {
        if(current != null) {
            current.show();
            return;
        }
        new StateMachine("/theme");        
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
