package com.codename1.demos.tipster;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class Tipster {
   
    public void init(Object context) {
    }

    public void start(){
        new StateMachine("/tipster");        
    }

    public void stop(){
    }
    
    public void destroy(){    
    }

}
