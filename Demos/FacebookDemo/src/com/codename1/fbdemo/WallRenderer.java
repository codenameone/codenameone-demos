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
package com.codename1.fbdemo;

import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.services.ImageDownloadService;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.list.CellRenderer;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.list.ListCellRenderer;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Chen
 */
public class WallRenderer extends Container implements ListCellRenderer{

    private Dimension userIconSize = new Dimension(Display.getInstance().getDisplayHeight()/10, Display.getInstance().getDisplayHeight()/10);
    
    private Dimension postIconSize = new Dimension(Display.getInstance().getDisplayHeight()/15, Display.getInstance().getDisplayHeight()/15);
    
    private Label userIcon = new Label();
    
    private Label userName = new Label();
    
    private Label post = new Label();
        
    private boolean myWall;
    
    public WallRenderer(boolean myWall) {
        setLayout(new BorderLayout());
        addComponent(BorderLayout.WEST, userIcon);
        addComponent(BorderLayout.NORTH, userName);
        addComponent(BorderLayout.CENTER, post);
        this.myWall = myWall;
        if(myWall){
            try {
                FaceBookAccess.getInstance().getPicture("me", userIcon, userIconSize, false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        Hashtable v = (Hashtable) value;
        String txt = (String) v.get("message");
        if (txt == null) {
            txt = (String) v.get("story");
            if (txt == null) {
                txt = (String) v.get("name");
            }
        }
        String picture = (String) v.get("picture");
        post.setText(txt);
        Image im = (Image) v.get("post_pic");
        if (im == null && picture != null && v.get("fetching_post_pic") == null) {
            v.put("fetching_post_pic", Boolean.TRUE);
            ImageDownloadService.createImageToStorage(picture, (List) list, index, "post_pic", (String)v.get("id"), postIconSize);
        } else {
            post.setIcon(im);
        }
        
        if(!myWall){
            Hashtable from = (Hashtable)v.get("from");
            if(from != null && from.get("id") != null){
                userName.setText((String)from.get("name"));

                im = (Image) v.get("user_pic");
                if(im == null && v.get("fetching_user_pic") == null){
                    v.put("fetching_user_pic", Boolean.TRUE);
                    try {
                        FaceBookAccess.getInstance().getPicture((String)from.get("id"), (List)list, index, "user_pic", userIconSize, true);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    userIcon.setIcon(im);        
                }
            }else{
                userName.setText("");
                userIcon.setIcon(null);        
            }
        }
        return this;
    }

    public Component getListFocusComponent(List list) {
        return new Label();
    }
}
