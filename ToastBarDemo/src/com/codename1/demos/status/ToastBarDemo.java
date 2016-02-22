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
package com.codename1.demos.status;


import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import java.util.Random;

public class ToastBarDemo {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        Form hi = new Form("Status Demo");
        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Button task1Btn = new Button("Do Task 1");
        Random r = new Random();
        task1Btn.addActionListener((evt)-> {
            Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Doing task 1...");
            status.setShowProgressIndicator(true);
            status.setIcon(createIcon(FontImage.MATERIAL_WORK));
            status.show();
            // Now do something that takes a while
            Display.getInstance().invokeAndBlock(()->{
                Util.sleep(2000);
            });
             
            // Let's pretend that en error occurs half the time


            if (r.nextDouble() < 0.5) {
                // There was an error
                status.setShowProgressIndicator(false);
                status.setMessage("An error occurred. Please try again.  This is a really "
                        + "long message that you may want to read.  This is some detail, "
                        + "because we want to make the box grow a bit");
                status.setIcon(createIcon(FontImage.MATERIAL_ERROR));
                status.show();
                // show this error message for 3 seconds, then hide automatically
                status.setExpires(10000);
            } else {
                // If the task succeeded, the user doesn't need to see anything
                // so we just hide the message
                status.clear();
            }
        });
        Label heading = new Label("Task 1");
        heading.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        hi.addComponent(heading);
        hi.addComponent(new SpanLabel("Run a task, that has a 50/50 chance of completing "
                + "successfully.  Upon starting the task, it will show a status message "
                + "with a progress indicator, and an icon.  If it completes successfully, "
                + "the status will simply hide.  If it fails, it will show an error message "
                + "in the status bar, and hide it 3 seconds later."));
        hi.addComponent(task1Btn);
        
        Label task2Result = new Label();
        
        Button task2Btn = new Button("Do Task 2");
        task2Btn.addActionListener((evt)-> {
            task2Result.setText("");
            hi.getContentPane().revalidate();
            Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Doing task 2 now");
            status.setShowProgressIndicator(true);
            status.showDelayed(300); 
            
            if (r.nextDouble() < 0.5) {
                // Simulate that half of the time it takes a couple of seconds to perform
                // this calculation
                Display.getInstance().invokeAndBlock(()->{
                    Util.sleep(2000);
                });
                
                if (r.nextDouble() < 0.5) {
                    // Simulate that half of the time the operation fails
                    status.setMessage("Failed to calculate the result.  Try again");
                    status.setExpires(3000);
                    status.setShowProgressIndicator(false);
                    status.setIcon(createIcon(FontImage.MATERIAL_ERROR));
                    status.show();
                    return;

                }
            }
            
            
            
            // Let's pretend that en error occurs half the time
            task2Result.setText("The result is 10");
            hi.getContentPane().revalidate();
            
            // Everything went well here, so we'll just clear the status.
            status.clear();

        });
        
        Label task2Heading = new Label("Task 2");
        task2Heading.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        hi.addComponent(task2Heading);
        hi.addComponent(new SpanLabel("In this task, we use status.showDelayed() to only show "
                + "the status if the operation is slow (half the time). If the operation succeeds, "
                + "it will display the result in the label below.  If it fails, it will show an "
                + "error message in the status bar."));
        hi.addComponent(task2Btn);
        hi.addComponent(task2Result);
        
        Button task3Btn = new Button("Do Task 3");
        task3Btn.addActionListener((evt)->{
            Status status = ToastBar.getInstance().createStatus();
            status.setMessage("Working on Task with Progress");
            status.setProgress(0);
            status.setIcon(createIcon(FontImage.MATERIAL_BACKUP));
            status.show();
            
            for (int progress=0; progress <= 100; progress += 10) {
                Display.getInstance().invokeAndBlock(()->{Util.sleep(500);});
                status.setProgress(progress);
                status.show();
                if (progress == 100) {
                    status.setIcon(createIcon(FontImage.MATERIAL_DONE));
                    status.setProgress(-1);
                    status.setExpires(3000);
                    status.setMessage("The task is now complete");
                    status.show();
                }
            }
        });
        
        Label task3Heading = new Label("Task 3");
        task3Heading.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        hi.addComponent(task3Heading);
        hi.addComponent(new SpanLabel("A task that uses a progress bar to show the user how far along it is"));
        hi.addComponent(task3Btn);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }

    private Image createIcon(char charcode) {
        int iconWidth = Display.getInstance().convertToPixels(8, true);
        Style iconStyle = new Style();
        Font iconFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        if (Font.isNativeFontSchemeSupported()) {
            iconFont = Font.createTrueTypeFont("native:MainBold", null).derive((int)(iconWidth * 0.5), Font.STYLE_BOLD);
        }
        iconStyle.setFont(iconFont);
        iconStyle.setFgColor(0xffffff);
        iconStyle.setBgTransparency(0);

        FontImage completeIcon = FontImage.createMaterial(charcode, iconStyle);
        return completeIcon;
    }
}
