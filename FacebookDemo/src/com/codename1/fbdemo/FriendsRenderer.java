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
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.List;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.list.DefaultListCellRenderer;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Chen
 */
public class FriendsRenderer extends DefaultListCellRenderer{

    
    private Dimension imageSize = new Dimension(Display.getInstance().getDisplayHeight()/10, Display.getInstance().getDisplayHeight()/10);
    
    public Component getCellRendererComponent(Component list, Object model, Object value, int index, boolean isSelected) {
        Hashtable v = (Hashtable) value;
        setText((String)v.get("name"));
        Image im = (Image)v.get("pic");
        String id = (String)v.get("id");
        
        if(im == null && id != null && v.get("fetching") == null){
            v.put("fetching", Boolean.TRUE);
            try {
                FaceBookAccess.getInstance().getPicture(id, (List)list, index, "pic", imageSize, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            setIcon(im);        
        }
        return this;
    }

    
}
