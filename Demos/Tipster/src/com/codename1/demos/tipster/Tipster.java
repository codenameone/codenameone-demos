package com.codename1.demos.tipster;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class Tipster {
    private Form currentForm;
    public void init(Object context) {
    }

    public void start() {
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        new StateMachine("/tipster");        
    }

    public void stop() {
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy() {  
    }

}
