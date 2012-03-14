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
import com.codename1.ui.Command;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author Shai Almog
 */
public class Themes  extends Demo {

    public String getDisplayName() {
        return "Themes";
    }

    public Image getDemoIcon() {
        return getResources().getImage("paint-01.png");
    }

    public Container createDemo() {
        return null;
    }
    public Container createDemo(final Form parentForm) {
        Container themes = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        ComponentGroup gp = new ComponentGroup();
        themes.addComponent(gp);
        
        Button books = new Button("Books");
        Button nativeTheme = new Button("Native");
        Button tzone = new Button("TZone");
        gp.addComponent(books);
        if(Display.getInstance().hasNativeTheme()) {
            gp.addComponent(nativeTheme);
        }
        gp.addComponent(tzone);
        
        books.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/theme");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        nativeTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().installNativeTheme();
                refreshTheme(parentForm);
            }
        });
        tzone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/tzone_theme");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        
        return themes;
    }
 
    private void refreshTheme(Form parentForm) {
        Form c = Display.getInstance().getCurrent();
        c.refreshTheme();
        parentForm.refreshTheme();
    }
}

