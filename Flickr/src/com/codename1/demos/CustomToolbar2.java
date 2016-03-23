/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos;

import com.codename1.ui.Component;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;

/**
 * This Custom Toolbar changes the opacity of the Toolbar background, shifts the 
 * title and changes its size upon scroll
 * 
 * @author Chen
 */
public class CustomToolbar2 extends Toolbar implements ScrollListener {

    private int alpha;

    private Container bottomPadding = new Container();
    
    private Container topPadding = new Container();

    private int minimumHeight;

    private int topHeight;

    private Label title = new Label(" ");

    public CustomToolbar2() {
    }

    public CustomToolbar2(boolean layered) {
        super(layered);
    }

    /**
     * Sets the initial height of this toolbar
     */ 
    public void setPadding(int topHeight) {
        bottomPadding.setPreferredH(0);
        minimumHeight = getPreferredH();
        bottomPadding.setPreferredH(topHeight - minimumHeight);        
        topPadding.setPreferredH(bottomPadding.getPreferredH());
        this.topHeight = topHeight;
        revalidate();
    }

    @Override
    public void setTitle(String titleText) {
        super.setTitle(" ");
        title.setText(titleText);
        title.setUIID("Title");
        title.getStyle().setAlignment(Component.LEFT);
        title.getStyle().setPaddingUnit(new byte[]{Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_PIXELS});
    }

    @Override
    protected void initTitleBarStatus() {
        super.initTitleBarStatus();
        if (bottomPadding.getParent() == null) {
            add(BorderLayout.SOUTH, bottomPadding);
        }

        if (title.getParent() == null) {
            Container titleCnt = new Container(new BorderLayout());
            title.getStyle().removeListeners();
            titleCnt.add(BorderLayout.CENTER, title);
            Container top = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            if (((BorderLayout) getLayout()).getNorth() != null) {
                Container bar = new Container();
                bar.setUIID("StatusBar");
                top.addComponent(bar);
            }
            top.addComponent(topPadding);            
            titleCnt.add(BorderLayout.NORTH, top);

            Container parent = getParent();
            parent.removeComponent(this);
            Container lay = new Container(new LayeredLayout());
            lay.add(this);
            lay.add(titleCnt);

            parent.addComponent(BorderLayout.NORTH, lay);
            revalidate();
        }

    }

    @Override
    protected void paintBorderBackground(Graphics g) {
        int a = g.getAlpha();
        g.setAlpha(alpha);
        super.paintBorderBackground(g);
        g.setAlpha(a);
    }

    @Override
    public void paintComponentBackground(Graphics g) {
        int a = g.getAlpha();
        g.setAlpha(alpha);
        super.paintComponentBackground(g);
        g.setAlpha(a);
    }

    @Override
    public void scrollChanged(int scrollX, int scrollY, int oldscrollX, int oldscrollY) {
        //modify the alpha value
        alpha = scrollY;
        alpha = Math.max(alpha, 0);
        alpha = Math.min(alpha, 255);

        //push the title to the right when the scroll moves up
        int padding = (int) ((float) alpha * (Display.getInstance().getDisplayWidth() * 40 / 100) / (float) 255);

        padding = Math.min((Display.getInstance().getDisplayWidth()) / 2, padding);
        title.getStyle().setPadding(LEFT, padding);
        
        //change the size of the tollbar
        bottomPadding.setPreferredH(Math.max(0, topHeight - minimumHeight - scrollY));
        topPadding.setPreferredH(bottomPadding.getPreferredH());
        revalidate();
    }

}
