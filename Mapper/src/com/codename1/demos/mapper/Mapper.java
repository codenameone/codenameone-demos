package com.codename1.demos.mapper;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class Mapper {
    private Form currentForm;
    
    public void init(Object context) {
    }

    public void start() {
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        new StateMachine("/theme");        
    }

    public void stop() {
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
