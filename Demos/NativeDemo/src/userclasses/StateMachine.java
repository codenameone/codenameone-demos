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
package userclasses;

import com.codename1.nativedemo.NativeCalls;
import com.codename1.system.NativeLookup;
import generated.StateMachineBase;
import com.codename1.ui.*; 
import com.codename1.ui.events.*;

/**
 *
 * @author Shai Almog
 */
public class StateMachine extends StateMachineBase {
    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }
    
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    protected void initVars() {
    }

    protected void onCreateGUI1() {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onCreateGUI1();
    
    }

    protected void onGUI1_AddNativeButtonAction(Component c, ActionEvent event) {
        // If the resource file changes the names of components this call will break notifying you that you should fix the code
        super.onGUI1_AddNativeButtonAction(c, event);
        try {
            NativeCalls n = (NativeCalls)NativeLookup.create(NativeCalls.class);
            if(n != null && n.isSupported()) {
                PeerComponent nativeButton = n.createNativeButton(findNameForNativeButton(c.getParent()).getText());
                System.out.println("onGUI1_AddNativeButtonAction got native button peer: " + nativeButton);
                if(nativeButton != null) {
                    c.getComponentForm().addComponent(nativeButton);
                } else {
                    c.getComponentForm().addComponent(new Label("Native interface failed to provide a button"));
                }
            } else {
                c.getComponentForm().addComponent(new Label("Native interface is not supported on this platform"));
            }
            c.getComponentForm().revalidate();
        } catch(Throwable t) {
            Dialog.show("Error", "Exception during native access: " + t, "OK", null);
        }
    }
}
