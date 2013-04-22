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

import com.codename1.components.InfiniteProgress;
import com.codename1.cloud.CloudException;
import com.codename1.cloud.CloudObject;
import com.codename1.cloud.CloudStorage;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Demonstrates the ability to connect to the cloud and place an object in the cloud
 *
 * @author Shai Almog
 */
public class CloudDB extends Demo {
    public String getDisplayName() {
        return "CloudDB";
    }

    public Image getDemoIcon() {
        return getResources().getImage("clouddb.png");
    }

    public Container createDemo() {
        CloudStorage.getInstance().rollback();
        Container cloudDb = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cloudDb.setScrollableY(true);
        
        InfiniteProgress progress = new InfiniteProgress();
        Dialog dlg = progress.showInifiniteBlocking();
        try {
            CloudObject[] objects = CloudStorage.getInstance().querySorted("MyObject", 1, true, 0, 10, CloudObject.ACCESS_APPLICATION);
            if(objects != null && objects.length > 0) {
                cloudDb.addComponent(new Label("My Cloud Objects"));
                ComponentGroup entries = new ComponentGroup();
                cloudDb.addComponent(entries);
                for(int iter = 0 ; iter < objects.length ; iter++) {
                    final TextField entry = new TextField();
                    final CloudObject currentObject = objects[iter];
                    entry.setText(currentObject.getString("txt"));
                    entry.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            currentObject.setString("txt", entry.getText());
                            currentObject.setIndexString(1, entry.getText());
                            CloudStorage.getInstance().save(currentObject);
                        }
                    });
                    entries.addComponent(entry);
                }
                ComponentGroup comm = new ComponentGroup();
                Button commitChanges = new Button("Commit");
                commitChanges.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        int result = CloudStorage.getInstance().commit();
                        if(result != CloudStorage.RETURN_CODE_SUCCESS) {
                            if(!Dialog.show("Cloud Error", "Error " + result, "OK", "Retry")) {
                                actionPerformed(evt);
                            }
                        }
                    }
                });
                comm.addComponent(commitChanges);
                cloudDb.addComponent(comm);
            }
        } catch (CloudException ex) {
            cloudDb.addComponent(new Label("Error connecting to cloud" + ex.getErrorCode()));
        }
                
        ComponentGroup addNewGroup = new ComponentGroup();
        final TextField title = new TextField("New Entry");
        title.setHint("Label for new Entry");
        Button addNew = new Button("Add");
        addNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CloudObject obj = new CloudObject("MyObject", CloudObject.ACCESS_APPLICATION);
                obj.setString("txt", title.getText());
                obj.setIndexString(1, title.getText());
                CloudStorage.getInstance().save(obj);
                int result = CloudStorage.getInstance().commit();
                if(result != CloudStorage.RETURN_CODE_SUCCESS) {
                    Dialog.show("Cloud Error", "Error " + result, "OK", null);
                }
            }
        });
        addNewGroup.addComponent(title);
        addNewGroup.addComponent(addNew);
        cloudDb.addComponent(addNewGroup);

        dlg.dispose();
        
        return cloudDb;
    }    
}
