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

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Shai Almog
 */
public class Effects extends Demo {

    public String getDisplayName() {
        return "Effects";
    }

    public Image getDemoIcon() {
        return getResources().getImage("image-missing.png");
    }

    private void arrangeForInterlace(Container c) {
        int w = Display.getInstance().getDisplayWidth();
        for(int iter = 0 ; iter < c.getComponentCount() ; iter++) {
            Component cmp = c.getComponentAt(iter);
            if(iter % 2 == 0) {
                cmp.setX(-w);
            } else {
                cmp.setX(w);
            }
        }
    }
    
    private void arrangeForFall(Container c) {
        int w = Display.getInstance().getDisplayWidth();
        for(int iter = 0 ; iter < c.getComponentCount() ; iter++) {
            Component cmp = c.getComponentAt(iter);
            cmp.setY(-c.getHeight());
        }
    }

    private void arrangeForConcentrate(Container c) {
        int w = Display.getInstance().getDisplayWidth();
        int h = Display.getInstance().getDisplayHeight();
        int[] positionX = {-100, w / 2,  w + 100, w + 100, w + 100, w / 2, -100, -100};
        int[] positionY = {-100, -100, -100, h / 2, h + 100, h + 100, h + 100, h / 2};
        for(int iter = 0 ; iter < c.getComponentCount() ; iter++) {
            Component cmp = c.getComponentAt(iter);
            cmp.setY(positionY[iter % positionY.length]);
            cmp.setX(positionX[iter % positionX.length]);
        }
    }
    
    public Container createDemo() {
        final Container effects = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        Button interlace = new Button("Interlace");
        Button fall = new Button("Fall");
        Button concentrate = new Button("Concentrate");
        final Button grow = new Button("Grow");
        final Button morph = new Button("Morph");
        Button dragAndDrop = new Button("Drag & Drop");
        //Button shake = new Button("Shake");
        
        interlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                arrangeForInterlace(effects);
                effects.animateUnlayoutAndWait(800, 20);
                effects.animateHierarchyFade(800, 20);
            }
        });

        fall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                arrangeForFall(effects);
                effects.animateUnlayoutAndWait(800, 20);
                effects.animateHierarchyFade(800, 20);
            }
        });

        concentrate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                arrangeForConcentrate(effects);
                effects.animateUnlayoutAndWait(800, 20);
                effects.animateHierarchyFade(800, 20);
            }
        });
        
        grow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                grow.setPreferredH(grow.getPreferredH() * 3);
                effects.animateLayoutAndWait(800);
                grow.setPreferredSize(null);
                effects.animateLayoutAndWait(800);
            }
        });
        
        morph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TextArea ta = new TextArea("Morphed Text Area");
                ta.setVisible(false);
                effects.addComponent(0, ta);
                effects.animateLayoutAndWait(400);
                ta.setVisible(true);
                effects.morphAndWait(morph, ta, 3000);
                effects.removeComponent(morph);
                effects.revalidate();
                effects.addComponent(morph);
                effects.morphAndWait(ta, morph, 3000);
                effects.removeComponent(ta);
                effects.animateLayout(400);
            }
        });
        
        dragAndDrop.setDraggable(true);
        effects.setDropTarget(true);
        
        effects.addComponent(interlace);
        effects.addComponent(fall);
        effects.addComponent(concentrate);
        effects.addComponent(grow);
        effects.addComponent(morph);
        effects.addComponent(dragAndDrop);
        //effects.addComponent(shake);
        
        return effects;
    }
    
}
