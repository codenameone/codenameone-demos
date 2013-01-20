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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.payment.Purchase;
import com.codename1.ui.Button;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;

/**
 * Demo of in-app purchase functionality
 *
 * @author Shai Almog
 */
public class Pay extends Demo {
    private static final String[] ITEM_IDS = {
        "ITEM001",
        "ITEM002",
        "ITEM003"
    };
    private static final String[] ITEM_NAMES = {
        "Give Us A Little Money",
        "Give Us Some More Money",
        "Thanks!!!"
    };
    public String getDisplayName() {
        return "Purchase";
    }

    public Image getDemoIcon() {
        return getResources().getImage("evolution-contacts.png");
    }

    public Container createDemo() {
        final Container purchaseDemo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        final Purchase p = Purchase.getInAppPurchase();
        
        if(p != null) {
            if(p.isManualPaymentSupported()) {
                purchaseDemo.addComponent(new Label("Manual Payment Mode"));
                final TextField tf = new TextField("100");
                tf.setHint("Send us money, thanks");
                Button sendMoney = new Button("Send Us Money");
                sendMoney.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        p.pay(Double.parseDouble(tf.getText()), "USD");
                    }
                });
                purchaseDemo.addComponent(tf);
                purchaseDemo.addComponent(sendMoney);
            } 
            if(p.isManagedPaymentSupported()) {
                purchaseDemo.addComponent(new Label("Managed Payment Mode"));
                for(int iter = 0 ; iter < ITEM_NAMES.length ; iter++) {
                    Button buy = new Button(ITEM_NAMES[iter]);
                    final String id = ITEM_IDS[iter];
                    buy.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            p.purchase(id);
                        }
                    });
                    purchaseDemo.addComponent(buy);
                }
            } 
        } else {
            purchaseDemo.addComponent(new Label("Payment unsupported on this device"));
        }
        
        return purchaseDemo;
    }
}
