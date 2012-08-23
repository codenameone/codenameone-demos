package com.codename1.demos.mapper;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class Mapper {
   
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
