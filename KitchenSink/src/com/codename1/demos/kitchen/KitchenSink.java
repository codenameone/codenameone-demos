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


import com.codename1.io.Log;
import com.codename1.payment.PurchaseCallback;
import com.codename1.push.PushCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import java.util.Vector;

public class KitchenSink implements PushCallback, PurchaseCallback {
    private Resources res;
    private static boolean launched;
    private String pushText;
    private Form currentForm;
    
    public void init(Object context){
        //Log.setReportingLevel(Log.REPORTING_PRODUCTION);
        //DefaultCrashReporter.init(true, 2);
        Log.p("Init");
        try{
            res = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
        } catch(IOException e){
            Log.e(e);
        }
    }
    
    private Button createDemo(Demo d) {
        Button btn = new Button(d.getDisplayName(), d.getDemoIcon());
        btn.setUIID("DemoButton");
        initDemoButtonMargin(btn.getUnselectedStyle());
        initDemoButtonMargin(btn.getSelectedStyle());
        initDemoButtonMargin(btn.getPressedStyle());
        initDemoButtonMargin(btn.getDisabledStyle());
        btn.setTextPosition(Button.TOP);
        return btn;
    }
    
    private void initDemoButtonMargin(Style s) {
        s.setMargin(4, 0, 2, 2);
        s.setMarginUnit(new byte[] {Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS});
    }
    
    /**
     * This method wraps the layout in a way so the shelves are rendered underneath the
     * given component
     */
    private Container wrapInShelves(Container c) {
        Container layered = new Container(new LayeredLayout());
        Container sides = new Container(new BorderLayout());
        Label right = new Label(" ");
        right.setUIID("Right");
        sides.addComponent(BorderLayout.EAST, right);
        Label left = new Label(" ");
        left.setUIID("Left");
        sides.addComponent(BorderLayout.WEST, left);
        layered.addComponent(sides);
        
        layered.addComponent(c);
        
        return layered;
    }
    
    private void showSplashAnimation() {
        Form splash = new Form();
        splash.setUIID("Splash");
        splash.getContentPane().setUIID("Container");
        splash.getTitleArea().setUIID("Container");
        BorderLayout border = new BorderLayout();
        border.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        splash.setLayout(border);
        splash.setScrollable(false);
        Label title = new Label("Kitchen Sink Demo");
        title.setUIID("SplashTitle");
        Label subtitle = new Label("By Codename One");
        subtitle.setUIID("SplashSubTitle");
        splash.addComponent(BorderLayout.NORTH, title);
        splash.addComponent(BorderLayout.SOUTH, subtitle);
        Label beaker = new Label(res.getImage("beaker.png"));
        final Label beakerLogo = new Label(res.getImage("beaker_logo.png"));
        beakerLogo.setVisible(false);
        Container layeredLayout = new Container(new LayeredLayout());
        splash.addComponent(BorderLayout.CENTER, layeredLayout);
        layeredLayout.addComponent(beaker);
        final Container logoParent = new Container(new BorderLayout());
        layeredLayout.addComponent(logoParent);
        logoParent.addComponent(BorderLayout.CENTER, beakerLogo);
        splash.revalidate();
        
        Display.getInstance().callSerially(new Runnable() {
            @Override
            public void run() {
                beakerLogo.setVisible(true);
                beakerLogo.setX(0);
                beakerLogo.setY(0);
                beakerLogo.setWidth(3);
                beakerLogo.setHeight(3);
                logoParent.setShouldCalcPreferredSize(true);
                logoParent.animateLayoutFade(2000, 0);
            }
        });
        
        splash.show();
        splash.setTransitionOutAnimator(CommonTransitions.createFastSlide(CommonTransitions.SLIDE_VERTICAL, true, 300));
        new UITimer(new Runnable() {
            public void run() {
                showMainUI();
            }
        }).schedule(2500, false, splash);
    }
    
    public void start(){
        Log.p("Start");
        if(currentForm != null && !(currentForm instanceof Dialog)) {
            currentForm.show();
            return;
        }
        showSplashAnimation();
    }

    private void showMainUI() {
        Log.p("Show Main UI");
        final Form f = new Form("Kitchen Sink");
        Demo[] demos = new Demo[] {
            new Effects(), new Layouts(), 
            new DialogDemo(), new Contacts(), 
            new Mail(), new Themes(), 
            new Web(), new Components(),
            new Video(), new Camera(), 
            new WebServices(),new Input(),
            new Share(), new CloudDB(), new Pay(),
            new Push()//, new RSS()
        };
        for(int iter = 0 ; iter < demos.length ; iter++) {
            demos[iter].init(res);
        }
        int componentsPerRow = 3;
        f.setLayout(new BorderLayout());
        Container boxContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        boxContainer.setScrollableY(true);
        Container currentRow = null;
        f.setScrollable(false);
        final Container runBox = new Container(new BorderLayout());
        if(Display.getInstance().isTablet()) {
            f.addComponent(BorderLayout.NORTH, wrapInShelves(boxContainer));
            f.addComponent(BorderLayout.CENTER, wrapInShelves(runBox));
            runBox.addComponent(BorderLayout.CENTER, demos[0].createDemo(f));
            componentsPerRow = demos.length / 2 + demos.length % 2;
        } else {
            f.addComponent(BorderLayout.CENTER, wrapInShelves(boxContainer));
        }

        Vector shelves = new Vector();
        Vector demoComponents = new Vector();
        for(int iter = 0 ; iter < demos.length ; iter++) {
            final Demo currentDemo = demos[iter];
            Button b = createDemo(currentDemo);

            if(currentRow == null || currentRow.getComponentCount() == componentsPerRow) {
                currentRow = new Container(new FlowLayout(Component.CENTER));

                Container layers = new Container(new LayeredLayout());
                Container shelfContainer = new Container(new BorderLayout());
                layers.addComponent(shelfContainer);
                layers.addComponent(currentRow);
                boxContainer.addComponent(layers);                    
                Label shelf = new Label(" ");
                shelf.setUIID("Shelf");
                shelves.addElement(shelf);

                // used to space the shelf down
                Label dl = new Label(" ");
                dl.setPreferredSize(b.getPreferredSize());
                shelfContainer.addComponent(BorderLayout.CENTER, dl);
                shelfContainer.addComponent(BorderLayout.SOUTH, shelf);
            }
            currentRow.addComponent(b);
            demoComponents.addElement(b);

            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Container n = currentDemo.createDemo(f);
                    if(Display.getInstance().isTablet()) {
                        runBox.replace(runBox.getComponentAt(0), n, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 300));
                    } else {
                        Form demoForm = new Form(currentDemo.getDisplayName());
                        demoForm.setScrollable(false);
                        demoForm.setLayout(new BorderLayout());
                        demoForm.addComponent(BorderLayout.CENTER, wrapInShelves(n));
                        String backTitle = "Back";
                        if(UIManager.getInstance().isThemeConstant("backUsesTitleBool", false)) {
                            backTitle = "Kitchen Sink";
                        }
                        Command backCommand = new Command(backTitle) {
                            public void actionPerformed(ActionEvent ev) {
                                f.showBack();
                            } 
                        }; 
                        demoForm.addCommand(backCommand);
                        demoForm.setBackCommand(backCommand);
                        demoForm.show();
                    }
                }
            });
        }
        f.revalidate();

        // Loop Over All the components and move them so we can animate everything into place
        int dw = Display.getInstance().getDisplayWidth();
        for(int iter = 0 ; iter < shelves.size() ; iter++) {
            Component cmp = (Component)shelves.elementAt(iter);
            cmp.setY(-40 - cmp.getAbsoluteY());
        }
        for(int iter = 0 ; iter < demoComponents.size() ; iter++) {
            Component cmp = (Component)demoComponents.elementAt(iter);
            if(iter < componentsPerRow) {
                cmp.setX(-cmp.getWidth());
            } else {
                if(iter < componentsPerRow * 2) {
                    cmp.setX(dw);
                } else {
                    cmp.setX(-cmp.getWidth());
                }
            }
        }
        boxContainer.setShouldCalcPreferredSize(true);
        boxContainer.animateHierarchyFade(3000, 30);
        f.show();        
        launched = true;
        if(pushText != null) {
            UITimer ut = new UITimer(new Runnable() {
                public void run() {
                    Dialog.show("Push!", pushText, "OK", null);
                    pushText = null;
                }
            });
            ut.schedule(800, false, f);
        }
    }
    
    public void stop(){
        Log.p("Stop");
        currentForm = Display.getInstance().getCurrent();
    }
    
    public void destroy(){
        Log.p("Destroy");
    }
    
    public void push(String value) {
        if(launched) {
            Dialog.show("Push!", value, "OK", null);
        } else {
            pushText = value;
        }
    }

    public void registeredForPush(String deviceId) {
        Dialog.show("Registered", "Id: " + deviceId, "OK", null);
    }

    public void pushRegistrationError(String error, int errorCode) {
        Dialog.show("Error: " + errorCode, error, "OK", null);
    }

    public void itemPurchased(String sku) {
        Dialog.show("Event Received", "itemPurchased event for SKU: " + sku, "OK", null);
    }

    public void itemPurchaseError(String sku, String errorMessage) {
        Dialog.show("Event Received", "itemPurchaseError: " + errorMessage, "OK", null);
    }

    public void itemRefunded(String sku) {
        Dialog.show("Event Received", "itemRefunded for SKU: " + sku, "OK", null);
    }

    public void subscriptionStarted(String sku) {
        Dialog.show("Event Received", "subscriptionStarted for SKU: " + sku, "OK", null);
    }

    public void subscriptionCanceled(String sku) {
        Dialog.show("Event Received", "subscriptionCanceled for SKU: " + sku, "OK", null);
    }

    public void paymentFailed(String paymentCode, String failureReason) {
        Dialog.show("Event Received", "paymentFailed: " + failureReason, "OK", null);
    }

    public void paymentSucceeded(String paymentCode, double amount, String currency) {
        Dialog.show("Event Received", "paymentSucceeded amount: " + amount, "OK", null);
    }
}
