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

import com.codename1.components.MediaPlayer;
import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;

/**
 *
 * @author Shai Almog
 */
public class Video  extends Demo {

    public String getDisplayName() {
        return "Video";
    }

    public Image getDemoIcon() {
        return getResources().getImage("monitor-01.png");
    }

    public Container createDemo() {
        Container player = new Container(new BorderLayout());
        final MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(Display.getInstance().getResourceAsStream(getClass(), "/video.mp4"), "video/mp4", null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Button playButton = new Button("Play/Pause");
        ComponentGroup grp = new ComponentGroup();
        grp.addComponent(playButton);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(mp.getMedia().isPlaying()) {
                    mp.getMedia().pause();
                } else {
                    mp.getMedia().play();
                }
            }
        });
        player.addComponent(BorderLayout.CENTER, mp);
        player.addComponent(BorderLayout.SOUTH, grp);
        return player;
    }
    
}
