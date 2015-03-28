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

import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;

/**
 *
 * @author Shai Almog
 */
public class Mail  extends Demo {

    public String getDisplayName() {
        return "Mail";
    }

    public Image getDemoIcon() {
        return getResources().getImage("mail.png");
    }

    public Container createDemo() {
        Container message = new Container(new BoxLayout(BoxLayout.Y_AXIS));        
        ComponentGroup gp = new ComponentGroup();
        message.addComponent(gp);
        
        final TextField to = new TextField("");
        to.setHint("TO:");
        gp.addComponent(to);

        final TextField subject = new TextField("Codename One Is Cool");
        subject.setHint("Subject");
        gp.addComponent(subject);
        final TextField body = new TextField("Try it out at http://www.codenameone.com/");
        body.setSingleLineTextArea(false);
        body.setRows(4);
        body.setHint("Message Body");
        gp.addComponent(body);
        
        ComponentGroup buttonGroup = new ComponentGroup();
        Button btn = new Button("Send");
        buttonGroup.addComponent(btn);
        message.addComponent(buttonGroup);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Message msg = new Message(body.getText());
                Display.getInstance().sendMessage(new String[] {to.getText()}, subject.getText(), msg);
            }
        });
                
        return message;
    }
    
}
