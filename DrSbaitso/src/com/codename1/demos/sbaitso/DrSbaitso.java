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

package com.codename1.demos.sbaitso;

import com.codename1.capture.Capture;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.system.NativeLookup;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 * Demo of Codename One inspired by the classic soundblaster demo and Apples imessage design
 * 
 * @author Shai Almog
 */
public class DrSbaitso {
    private String userName;
    private Image userPicture;
    private Form current;
    private TTS tts;

    public void init(Object context) {
        Resources theme = UIManager.initFirstTheme("/theme");
        userPicture = theme.getImage("duke_iphone.png");
        tts = (TTS)NativeLookup.create(TTS.class);        
        // Pro only feature, uncomment if you have a pro subscription
        //Log.bindCrashProtection(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form("Welcome");
        BorderLayout bl = new BorderLayout();
        bl.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER);
        hi.setLayout(bl);
        hi.setFormBottomPaddingEditingMode(true);
        ComponentGroup cg = new ComponentGroup();
        final TextField name = new TextField();
        name.setHint("What Is Your Name?");
        final Button btn = new Button("Take Photo");
        cg.addComponent(name);
        cg.addComponent(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                userPicture = captureRoundImage();
                btn.setIcon(userPicture);
            }
        });
        hi.addComponent(BorderLayout.CENTER, cg);
        Toolbar t = new Toolbar();
        hi.setToolBar(t);
        t.addCommandToRightBar(new Command("Next") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userName = name.getText();
                if(userName.length() == 0) {
                    userName = "[Unnamed]";
                }
                showSbaitso();
            }
        });
        hi.show();
    }

    private DataChangedListener createSearchListener(final TextField searchField, final Container discussion, final Button ask) {
        return new DataChangedListener() {
            private boolean animateLock;
            public void dataChanged(int type, int index) {
                String t = searchField.getText();
                int count = discussion.getComponentCount();
                if(t.length() == 0) {
                    ask.setEnabled(true);
                    for(int iter = 0 ; iter < count ; iter++) {
                        Component c = discussion.getComponentAt(iter);
                        c.setPreferredSize(null);
                        c.setVisible(true);
                    }
                    animateChanage();
                    return;
                }              
                t = t.toLowerCase();
                ask.setEnabled(false);
                for(int iter = 0 ; iter < count ; iter++) {
                    SpanLabel tt = (SpanLabel)discussion.getComponentAt(iter);
                    if(tt.getText().toLowerCase().indexOf(t) < 0) {
                        tt.setPreferredSize(new Dimension(1, 1));
                        tt.setVisible(false);
                    } else {
                        tt.setPreferredSize(null);
                        tt.setVisible(true);
                    }
                }
                animateChanage();
            }
            private void animateChanage() {
                if(!animateLock) {
                    animateLock = true;
                    discussion.animateLayoutAndWait(300);
                    animateLock = false;
                }
            }
        };        
    }
    
    void showSbaitso() {
        Form sb = new Form();
        sb.setFormBottomPaddingEditingMode(true);
        Toolbar t = new Toolbar();
        sb.setToolBar(t);
        final TextField searchField = new TextField();
        searchField.setHint("Search For Answers...");
        t.setTitleComponent(searchField);
        sb.setLayout(new BorderLayout());
        final TextField ask = new TextField();
        ask.setHint("Ask The Dr.");
        Container askContainer = new Container(new BorderLayout());
        askContainer.addComponent(BorderLayout.CENTER, ask);
        Button askButton = new Button("Ask");
        askContainer.addComponent(BorderLayout.EAST, askButton);        
        sb.addComponent(BorderLayout.SOUTH, askContainer);
        final Container discussion = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        sb.addComponent(BorderLayout.CENTER, discussion);
        discussion.setScrollableY(true);
        sb.show();
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                String w = "HELLO " + userName +", MY NAME IS DOCTOR SBAITSO.\n\nI AM HERE TO HELP YOU.\n" +
                                            "SAY WHATEVER IS IN YOUR MIND FREELY," +
                                            "OUR CONVERSATION WILL BE KEPT IN STRICT CONFIDENCE.\n" +
                                            "MEMORY CONTENTS WILL BE WIPED OFF AFTER YOU LEAVE.";
                say(discussion, w, false);
                if(tts != null && tts.isSupported()) {
                    tts.say(w);
                }
            }
        });
        searchField.addDataChangeListener(createSearchListener(searchField, discussion, askButton));
        ActionListener askEvent = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String t = ask.getText();
                if(t.length() > 0) {
                    ask.setText("");
                    say(discussion, t, true);
                    answer(t, discussion);
                }
            }
        };
        ask.setDoneListener(askEvent);
        askButton.addActionListener(askEvent);
    }
    
    void answer(String question, Container dest) {
        String resp = AI.getResponse(question);
        say(dest, resp, false);
        if(tts != null && tts.isSupported()) {
            tts.say(resp);
        }
    }
    
    void say(Container destination, String text, boolean question) {
        SpanLabel t = new SpanLabel(text);
        t.setWidth(destination.getWidth());
        t.setX(0);
        t.setHeight(t.getPreferredH());
        
        if(question) {
            t.setY(Display.getInstance().getDisplayHeight());
            t.setTextUIID("BubbleUser");
            t.setIconPosition(BorderLayout.EAST);
            t.setIcon(userPicture);
            t.setTextBlockAlign(Component.RIGHT);
        } else {
            t.setY(0);
            t.setTextUIID("BubbleSbaitso");
            t.setTextBlockAlign(Component.LEFT);
        }
        destination.addComponent(t);
        destination.animateLayoutAndWait(400);
        destination.scrollComponentToVisible(t);
    }
    
    private Image roundImage(Image img) {
        int width = img.getWidth();
        Image roundMask = Image.createImage(width, width, 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, width, width, 0, 360);
        Object mask = roundMask.createMask();
        img = img.applyMask(mask);
        return img;
    }
    
    private Image captureRoundImage() {
        try {
            int width = userPicture.getWidth();
            String result = Capture.capturePhoto(width, -1);
            if(result == null) {
                return userPicture;
            }
            Image capturedImage = Image.createImage(result);
            if(capturedImage.getHeight() != width) {
                if(capturedImage.getWidth() < capturedImage.getHeight()) {
                    capturedImage = capturedImage.subImage(0, capturedImage.getHeight() / 2 - width / 2, width, width, false);
                } else {
                    Image n = Image.createImage(width, width);
                    n.getGraphics().drawImage(capturedImage, 0, width / 2- capturedImage.getHeight() / 2);
                    capturedImage = n;
                }
            }
            return roundImage(capturedImage);
        } catch (IOException err) {
            err.printStackTrace();
            return userPicture;
        }
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
}
