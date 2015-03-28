package com.codename1.demo.zxing;


import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class Zxing {
    private Form currentForm;
    private static Object contextValue;
    public void init(Object context) {
        contextValue = context;
    }

    public static Object getContext() {
        return contextValue;
    }

    public void start(){
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        new StateMachine("/themeAndGUI");        
    }

    public void stop(){
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy(){
    }
}
