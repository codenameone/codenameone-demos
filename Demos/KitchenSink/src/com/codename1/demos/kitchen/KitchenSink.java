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
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class KitchenSink {
    private Resources res;
    public void init(Object context){
        try{
            res = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private Button createDemo(Demo d) {
        Button btn = new Button(d.getDisplayName(), d.getDemoIcon());
        btn.setUIID("DemoButton");
        btn.setTextPosition(Button.TOP);
        return btn;
    }
    
    /**
     * This method wraps the layout in a way so the shelves are rendered underneath the
     * given component
     */
    private Container wrapInShelves(Container c) {
        Container layered = new Container(new LayeredLayout());
        Container sides = new Container(new BorderLayout());
        Label right = new Label(" ");
        right.setUIID("Right");
        sides.addComponent(BorderLayout.EAST, right);
        Label left = new Label(" ");
        left.setUIID("Left");
        sides.addComponent(BorderLayout.WEST, left);
        layered.addComponent(sides);
        
        layered.addComponent(c);
        
        return layered;
    }
    
    public void start(){
        final Form f = new Form("Kitchen Sink");
        Demo[] demos = new Demo[] {
            new Camera(), new Input(),
            new Layouts(), new Mail(),
            new Themes(), new Video(),
            new Web(), new Components()
        };
        for(int iter = 0 ; iter < demos.length ; iter++) {
            demos[iter].init(res);
        }
        f.setLayout(new BorderLayout());
        f.setScrollable(false);
        if(!Display.getInstance().isTablet()) {
            Container boxContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            boxContainer.setScrollableY(true);
            Container currentRow = null;
            f.addComponent(BorderLayout.CENTER, wrapInShelves(boxContainer));
            
            for(int iter = 0 ; iter < demos.length ; iter++) {
                final Demo currentDemo = demos[iter];
                Button b = createDemo(currentDemo);
                
                if(currentRow == null || currentRow.getComponentCount() > 2) {
                    currentRow = new Container(new FlowLayout(Component.CENTER));
                    
                    Container layers = new Container(new LayeredLayout());
                    Container shelfContainer = new Container(new BorderLayout());
                    layers.addComponent(shelfContainer);
                    layers.addComponent(currentRow);
                    boxContainer.addComponent(layers);                    
                    Label shelf = new Label(" ");
                    shelf.setUIID("Shelf");
                    
                    // used to space the shelf down
                    Label dl = new Label(" ");
                    dl.setPreferredSize(b.getPreferredSize());
                    shelfContainer.addComponent(BorderLayout.CENTER, dl);
                    shelfContainer.addComponent(BorderLayout.SOUTH, shelf);
                }
                currentRow.addComponent(b);
                
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        Container n = currentDemo.createDemo(f);
                        Form demoForm = new Form(currentDemo.getDisplayName());
                        demoForm.setScrollable(false);
                        demoForm.setLayout(new BorderLayout());
                        demoForm.addComponent(BorderLayout.CENTER, wrapInShelves(n));
                        String backTitle = "Back";
                        if(UIManager.getInstance().isThemeConstant("backUsesTitleBool", false)) {
                            backTitle = "Kitchen Sink";
                        }
                        Command backCommand = new Command(backTitle) {
                            public void actionPerformed(ActionEvent ev) {
                                f.showBack();
                            } 
                        }; 
                        demoForm.addCommand(backCommand);
                        demoForm.setBackCommand(backCommand);
                        demoForm.show();
                    }
                });
            }
        } else {
            final Container demoBox = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            demoBox.setScrollableY(true);
            f.addComponent(BorderLayout.WEST, demoBox);
            Container runBox = new Container(new BorderLayout());
            f.addComponent(BorderLayout.CENTER, runBox);
            for(int iter = 0 ; iter < demos.length ; iter++) {
                final Demo currentDemo = demos[iter];
                Button b = createDemo(currentDemo);
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        Container n = currentDemo.createDemo();
                        demoBox.replace(demoBox.getComponentAt(0), n, CommonTransitions.createFastSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 200));
                    }
                });
                demoBox.addComponent(b);
            }
            runBox.addComponent(BorderLayout.CENTER, demos[0].createDemo());
        }
        f.show();
    }

    public void stop(){
    }
    
    public void destroy(){
    }
}
