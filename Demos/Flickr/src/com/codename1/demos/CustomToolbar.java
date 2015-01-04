package com.codename1.demos;

import com.codename1.ui.Graphics;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ScrollListener;

/**
 * This Custom Toolbar changes the opacity of the Toolbar background upon scroll
 * 
 * @author Chen
 */
public class CustomToolbar extends Toolbar implements ScrollListener{

    private int alpha;
    
    public CustomToolbar() {
    }

    public CustomToolbar(boolean layered) {
        super(layered);
    }

    public void paintComponentBackground(Graphics g) {
        int a = g.getAlpha();
        g.setAlpha(alpha);
        super.paintComponentBackground(g);
        g.setAlpha(a);
    }

    public void scrollChanged(int scrollX, int scrollY, int oldscrollX, int oldscrollY) {
        alpha = scrollY;
        alpha = Math.max(alpha, 0);
        alpha = Math.min(alpha, 255);
    }

}
