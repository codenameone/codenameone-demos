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

import com.codename1.components.WebBrowser;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.BrowserNavigationCallback;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author Shai Almog
 */
public class Web extends Demo {

    public String getDisplayName() {
        return "Web";
    }

    public Image getDemoIcon() {
        return getResources().getImage("applications-internet.png");
    }

    public Container createDemo() {
        Container cnt = new Container(new BorderLayout());
        final WebBrowser wb = new WebBrowser();
        if(wb.getInternal() instanceof BrowserComponent) {
            Button btn = new Button("Add");
            final TextArea content = new TextArea();
            Container north = new Container(new BorderLayout());
            north.addComponent(BorderLayout.CENTER, content);
            north.addComponent(BorderLayout.EAST, btn);
            cnt.addComponent(BorderLayout.NORTH, north);
            content.setHint("Add to web document");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    ((BrowserComponent)wb.getInternal()).execute("fnc('" + content.getText() + "');");
                }
            });
            ((BrowserComponent)wb.getInternal()).setBrowserNavigationCallback(new BrowserNavigationCallback() {
                public boolean shouldNavigate(String url) {
                    if(url.startsWith("http://sayhello")) {
                        // warning!!! This is not on the EDT and this method MUST return immediately!
                        Display.getInstance().callSerially(new Runnable() {
                            public void run() {
                                ((BrowserComponent)wb.getInternal()).execute("fnc('this was written by Java code!')");
                            }
                        });
                        return false;
                    }
                    return true;
                }
            });
        }
        
        cnt.addComponent(BorderLayout.CENTER, wb);
        wb.setURL("jar:///Page.html");
        return cnt;
    }
    
}
