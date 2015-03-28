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
import com.codename1.ui.Command;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Shai Almog
 */
public class Themes  extends Demo {

    public String getDisplayName() {
        return "Themes";
    }

    public Image getDemoIcon() {
        return getResources().getImage("preferences-desktop-theme.png");
    }

    public Container createDemo() {
        return null;
    }
    
    public Container createDemo(final Form parentForm) {
        Container themes = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        themes.setScrollableY(true);
        ComponentGroup gp = new ComponentGroup();
        themes.addComponent(gp);
        
        Button books = new Button("Books");
        Button nativeTheme = new Button("Native");
        Button leather = new Button("Leather");
        Button chrome = new Button("Chrome");
        Button tzone = new Button("TZone");
        gp.addComponent(books);
        if(Display.getInstance().hasNativeTheme()) {
            gp.addComponent(nativeTheme);
        }
        
        // low end devices won't have all the themes installed
        if(Display.getInstance().getResourceAsStream(getClass(), "/leather.res") != null) {
            gp.addComponent(leather);
            gp.addComponent(chrome);
            gp.addComponent(tzone);
        }
        
        books.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/theme");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        nativeTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().installNativeTheme();
                refreshTheme(parentForm);
            }
        });
        leather.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/leather");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        chrome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/chrome");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        tzone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    Resources res = Resources.openLayered("/tzone_theme");
                    UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
                } catch(IOException e){
                    e.printStackTrace();
                }
                refreshTheme(parentForm);
            }
        });
        
        ComponentGroup fonts = new ComponentGroup();
        final Button monospaceFont = new Button("Monospace Font");
        final Button defaultFont = new Button("Default Font");
        defaultFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                applyFont(Font.getDefaultFont(), Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_BOLD), parentForm);
            }
        });
        monospaceFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                applyFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM), 
                        Font.createSystemFont(Font.FACE_MONOSPACE, Font.SIZE_LARGE, Font.STYLE_BOLD), parentForm);
            }
        });
        
        if(Font.isTrueTypeFileSupported()) {
            final Button handlee = new Button("Handlee Font");
            final Font handleeFont = Font.createTrueTypeFont("Handlee", "Handlee-Regular.ttf").
                            derive(Font.getDefaultFont().getHeight(), Font.STYLE_PLAIN);
            final Font handleeFontLarge = handleeFont.derive(handleeFont.getHeight() * 1.5f, Font.STYLE_PLAIN);
            handlee.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    applyFont(handleeFont, handleeFontLarge, parentForm);
                    handlee.repaint();
                    defaultFont.repaint();
                }
            });
            fonts.addComponent(handlee);
            fonts.addComponent(monospaceFont);
            fonts.addComponent(defaultFont);

            // the font is set here since otherwise the UIID change will break the settings
            handlee.getUnselectedStyle().setFont(handleeFont);
            handlee.getSelectedStyle().setFont(handleeFont);
            handlee.getPressedStyle().setFont(handleeFont);
        } else {
            fonts.addComponent(monospaceFont);
            fonts.addComponent(defaultFont);
        }
        monospaceFont.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        monospaceFont.getSelectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        monospaceFont.getPressedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
        themes.addComponent(fonts);

        return themes;
    }
 
    private void applyFont(Font f, Font largeFont, Form parent) {
        Hashtable themeAddition = new Hashtable();
        themeAddition.put("font", f);
        themeAddition.put("sel#font", f);
        themeAddition.put("press#font", f);
        themeAddition.put("dis#font", f);
        themeAddition.put("Label.font", largeFont);
        themeAddition.put("Title.font", largeFont);
        themeAddition.put("Button.font", f);
        themeAddition.put("Button.sel#font", f);
        themeAddition.put("Button.press#font", f);
        themeAddition.put("DemoButton.font", f);
        themeAddition.put("DemoButton.sel#font", f);
        themeAddition.put("DemoButton.press#font", f);
        themeAddition.put("Button.font", f);
        themeAddition.put("Button.sel#font", f);
        themeAddition.put("Button.press#font", f);
        themeAddition.put("ButtonGroupOnly.font", f);
        themeAddition.put("ButtonGroupOnly.sel#font", f);
        themeAddition.put("ButtonGroupOnly.press#font", f);
        themeAddition.put("ButtonGroupFirst.font", f);
        themeAddition.put("ButtonGroupFirst.sel#font", f);
        themeAddition.put("ButtonGroupFirst.press#font", f);
        themeAddition.put("ButtonGroupLast.font", f);
        themeAddition.put("ButtonGroupLast.sel#font", f);
        themeAddition.put("ButtonGroupLast.press#font", f);
        themeAddition.put("ButtonGroup.font", f);
        themeAddition.put("ButtonGroup.sel#font", f);
        themeAddition.put("ButtonGroup.press#font", f);
        themeAddition.put("GroupElementOnly.font", f);
        themeAddition.put("GroupElementOnly.sel#font", f);
        themeAddition.put("GroupElementOnly.press#font", f);
        themeAddition.put("GroupElementFirst.font", f);
        themeAddition.put("GroupElementFirst.sel#font", f);
        themeAddition.put("GroupElementFirst.press#font", f);
        themeAddition.put("GroupElementLast.font", f);
        themeAddition.put("GroupElementLast.sel#font", f);
        themeAddition.put("GroupElementLast.press#font", f);
        themeAddition.put("GroupElement.font", f);
        themeAddition.put("GroupElement.sel#font", f);
        themeAddition.put("GroupElement.press#font", f);
        
        UIManager.getInstance().addThemeProps(themeAddition);
        refreshTheme(parent);
    }
    
    private void refreshTheme(Form parentForm) {
        Form c = Display.getInstance().getCurrent();
        c.refreshTheme();
        parentForm.refreshTheme();
        parentForm.revalidate();
    }
}

