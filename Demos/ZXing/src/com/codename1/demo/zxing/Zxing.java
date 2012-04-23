package com.codename1.demo.zxing;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class Zxing {
    private static Object contextValue;
    public void init(Object context) {
        contextValue = context;
    }

    public static Object getContext() {
        return contextValue;
    }

    public void start(){
        System.out.println("started");    
        new StateMachine("/themeAndGUI");        
    }

    public void stop(){
    }
    
    public void destroy(){
    }
}
