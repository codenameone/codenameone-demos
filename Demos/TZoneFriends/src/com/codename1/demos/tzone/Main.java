/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.demos.tzone;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

/**
 * This is the main lifecycle class for the demo, the methods within this class
 * are invoked by the device callbacks.
 *
 * @author Shai Almog
 */
public class Main {
    private Form currentForm;
    
    /**
     * Invoked to initialize the lifecycle
     * 
     * @param o platform specific handle object
     */
    public void init(Object o) {
    }
    
    /**
     * Invoked to start or resume the application
     */
    public void start() {
        if(currentForm != null) {
            currentForm.show();
            return;
        }
        new StateMachine("/timeline");
    }
        
    /**
     * Invoked when the application is exited or paused
     */
    public void stop() {
        currentForm = Display.getInstance().getCurrent();
    }
    
    /**
     * Invoked to completely cleanup the application
     */
    public void destroy() {
    }    
}
