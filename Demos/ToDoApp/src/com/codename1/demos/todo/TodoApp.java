package com.codename1.demos.todo;


import com.codename1.ui.Display;
import userclasses.StateMachine;

public class TodoApp {
   
    public void init(Object context) {
    }

    public void start(){
        new StateMachine("/theme");        
    }

    public void stop(){
    }
    
    public void destroy(){
    }
}
