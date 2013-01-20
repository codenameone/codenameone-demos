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

import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.DateTimeSpinner;
import com.codename1.ui.table.Table;
import com.codename1.ui.tree.Tree;

/**
 * Small gallery of Codename One components
 *
 * @author Shai Almog
 */
public class Components  extends Demo {

    public String getDisplayName() {
        return "Components";
    }

    public Image getDemoIcon() {
        return getResources().getImage("applications-engineering.png");
    }

    private Container createSpinnerDemo() {
        Container spinnerContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        DateTimeSpinner dt = new DateTimeSpinner();
        spinnerContainer.addComponent(dt);
        return spinnerContainer;
    }
    private Container createTreeDemo() {
        Container treeContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Tree dt = new Tree();
        treeContainer.addComponent(dt);
        return treeContainer;
    }
    private Container createTableDemo() {
        Container tableContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Table dt = new Table();
        tableContainer.addComponent(dt);
        return tableContainer;
    }
    
    public Container createDemo() {
        Tabs t = new Tabs();
        t.addTab("Tree", createTreeDemo());
        t.addTab("Spinner", createSpinnerDemo());
        t.addTab("Table", createTableDemo());
        return t;
    }
    
}
