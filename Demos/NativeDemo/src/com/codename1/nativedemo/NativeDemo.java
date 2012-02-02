package com.codename1.nativedemo;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class NativeDemo {
    private static Object contextValue;
    public void init(Object context) {
        contextValue = context;
    }

    public static Object getContext() {
        return contextValue;
    }
    
    public void start(){
        new StateMachine("/nativeDemo");        
    }

    public void stop(){
    }
    
    public void destroy(){
    }

}
