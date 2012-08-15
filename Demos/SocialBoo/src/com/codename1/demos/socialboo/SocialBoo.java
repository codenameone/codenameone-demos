package com.codename1.demos.socialboo;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class SocialBoo {
   
    public void init(Object context) {
    }

    public void start() {
        new StateMachine("/theme");        
    }

    public void stop() {
    }
    
    public void destroy() {
    }
}
