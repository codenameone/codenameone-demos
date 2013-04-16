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
package com.codename1.demos.kitchen;

import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.util.Hashtable;

/**
 * A sample for push notifications support in Codename One, <b>warning The values for the entries 
 * here are only valid for this application and only when built with our certificates so
 * they will not work for you!</b>
 *
 * @author Shai Almog
 */
public class Push  extends Demo {
    /**
     * We get this key from the Google play API console by following the instructions here:
     * http://developer.android.com/google/gcm/gs.html
     */
    private static final String GOOGLE_AUTH_KEY = "718167036973"; 

    /**
     * We get this key from the Google play API console by following the instructions here:
     * http://developer.android.com/google/gcm/gs.html
     */
    private static final String GOOGLE_SERVER_KEY = "AIzaSyATSw_rGeKnzKWULMGEk7MDfEjRxJ1ybqo"; 

    /**
     * When pushing to an iOS device we need a special certificate (NOT THE SIGNING CERTIFICATE) which
     * you get when you create the application in the provisioning portal. This is a special push certificate.
     * This is the password for that certificate.
     */
    private static final String IOS_CERTIFICATE_PASSWORD = "password"; 
    
    /**
     * When pushing to an iOS device we need a special certificate (NOT THE SIGNING CERTIFICATE) which
     * you get when you create the application in the provisioning portal. This is a special push certificate.
     * This is the URL where you are hosting that certificate so our server can fetch it for push
     */
    private static final String IOS_CERTIFICATE_URL = "https://dl.dropbox.com/u/57067724/cn1/kitchen_sink_push_certificate.p12"; 

    public String getDisplayName() {
        return "Push";
    }

    public Image getDemoIcon() {
        return getResources().getImage("go-top.png");
    }

    public Container createDemo() {
        return null;
    }
    
    public Container createDemo(final Form parentForm) {
        ComponentGroup grp = new ComponentGroup();
        Button register = new Button("Register For Push");
        Button deregister = new Button("Deregister");
        Button sendPush = new Button("Send Push");
        final TextField destDevice = new TextField("");
        destDevice.setHint("Dest ID, blank for all devices");
        grp.addComponent(register);
        grp.addComponent(deregister);
        grp.addComponent(sendPush);
        grp.addComponent(destDevice);
        grp.addComponent(new Label("Id: " + com.codename1.push.Push.getDeviceKey()));
        
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Hashtable meta = new Hashtable();
                meta.put(com.codename1.push.Push.GOOGLE_PUSH_KEY, GOOGLE_AUTH_KEY);
                Display.getInstance().registerPush(meta, false);
            }
        });
        deregister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().deregisterPush();
            }
        });
        sendPush.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String devKey = null;
                if(destDevice.getText().length() > 0) {
                    devKey = destDevice.getText();
                }
                com.codename1.push.Push.sendPushMessage("Hi Kitchen Sink users!", devKey, false, GOOGLE_SERVER_KEY, IOS_CERTIFICATE_URL, IOS_CERTIFICATE_PASSWORD);
            }
        });
        
        return grp;
    }
}

