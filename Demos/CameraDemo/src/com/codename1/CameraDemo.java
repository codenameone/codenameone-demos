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
package com.codename1;


import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a simple Demo that demonstrates how to the Camera API
 */
public class CameraDemo {
    private Form currentForm;
    
    public void init(Object context){
        try{
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void start(){
        if(currentForm != null) {
            currentForm.show();
            return;
        }
    
        final Form f = new Form("Camera");
        f.setLayout(new BorderLayout());
        f.setScrollable(false);
        Button b = new Button("Take A Picture");
        final Label l = new Label();
        f.addComponent(BorderLayout.CENTER, l);

        b.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Image i = l.getStyle().getBgImage();
                if(i != null){
                    i.dispose();
                }
                l.getStyle().setBgImage(null);

                Capture.capturePhoto(new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        InputStream is = null;
                        try {
                            String path = (String) evt.getSource();
                            System.out.println("path " + path);
                            is = com.codename1.io.FileSystemStorage.getInstance().openInputStream(path);
                            Image i = Image.createImage(is);
                            l.setIcon(i.scaledWidth(300));
                            f.revalidate();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                is.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

            }
        });
        ComponentGroup g = new ComponentGroup();
        g.addComponent(b);
        f.addComponent(BorderLayout.SOUTH, g);
        f.show();
    }

    public void stop(){
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy(){
    }

}
