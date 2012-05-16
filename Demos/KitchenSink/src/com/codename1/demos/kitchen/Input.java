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

import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Shai Almog
 */
public class Input  extends Demo {

    public String getDisplayName() {
        return "Input";
    }

    public Image getDemoIcon() {
        return getResources().getImage("abiword.png"); 
    }
    
    public Container createDemo() {
        Container input = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        input.addComponent(new Label("Text Field"));
        input.addComponent(new TextField("Hi World"));
        input.addComponent(new Label("Text Field With Hint"));
        TextField hint = new TextField();
        hint.setHint("Hint");
        input.addComponent(hint);
        input.addComponent(new Label("Multi-Line Text Field"));
        TextField multi = new TextField();
        multi.setSingleLineTextArea(false);
        multi.setRows(4);
        multi.setColumns(20);
        input.addComponent(multi);
        return input;
    }
    
}
