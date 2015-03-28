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
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;

/**
 *
 * @author Shai Almog
 */
public class Layouts  extends Demo {
    
    public String getDisplayName() {
        return "Layouts";
    }

    public Image getDemoIcon() {
        return getResources().getImage("view-left-right.png");
    }

    public Container createDemo() {
        final Container layouts = new Container();        
        final Button borderLayout = new Button("Border");
        final Button boxYLayout = new Button("Box Y");
        final Button boxXLayout = new Button("Box X");
        final Button flowLayout = new Button("Flow");
        final Button flowCenterLayout = new Button("Flow Center");
        final Button gridLayout = new Button("Grid");
        final Button tableLayout = new Button("Table");
        
        layouts.addComponent(borderLayout);
        layouts.addComponent(boxYLayout);
        layouts.addComponent(boxXLayout);
        layouts.addComponent(flowLayout);
        layouts.addComponent(flowCenterLayout);
        layouts.addComponent(gridLayout);
        layouts.addComponent(tableLayout);
        
        borderLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                layouts.setLayout(new BorderLayout());
                
                // need to re-add the components since the layout requires a contraint
                layouts.removeComponent(boxYLayout);
                layouts.removeComponent(boxXLayout);
                layouts.removeComponent(flowLayout);
                layouts.removeComponent(flowCenterLayout);
                layouts.removeComponent(gridLayout);
                borderLayout.setVisible(false);
                tableLayout.setVisible(false);
                layouts.addComponent(BorderLayout.CENTER, boxYLayout);
                layouts.addComponent(BorderLayout.EAST, boxXLayout);
                layouts.addComponent(BorderLayout.WEST, flowLayout);
                layouts.addComponent(BorderLayout.NORTH, flowCenterLayout);
                layouts.addComponent(BorderLayout.SOUTH, gridLayout);
                
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        boxYLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                layouts.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        boxXLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                layouts.setLayout(new BoxLayout(BoxLayout.X_AXIS));
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        flowLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                layouts.setLayout(new FlowLayout());
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        flowCenterLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                layouts.setLayout(new FlowLayout(Component.CENTER));
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        gridLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                GridLayout g = new GridLayout(1, 1);
                g.setAutoFit(true);
                layouts.setLayout(g);
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        tableLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                borderLayout.setVisible(true);
                tableLayout.setVisible(true);
                TableLayout tl = new TableLayout(2, 4);
                layouts.setLayout(tl);

                // need to re-add the components since the layout requires a contraint
                layouts.removeAll();
                layouts.addComponent(borderLayout);
                layouts.addComponent(boxYLayout);
                layouts.addComponent(boxXLayout);
                layouts.addComponent(flowLayout);
                
                TableLayout.Constraint c = tl.createConstraint();
                c.setHorizontalSpan(2);
                layouts.addComponent(c, flowCenterLayout);
                layouts.addComponent(gridLayout);
                layouts.addComponent(tableLayout);
                
                layouts.setShouldCalcPreferredSize(true);
                layouts.animateLayout(800);
            }
        });
        
        return layouts;
    }
    
}
