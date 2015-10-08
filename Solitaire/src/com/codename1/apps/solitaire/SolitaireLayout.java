package com.codename1.apps.solitaire;

import com.codename1.io.Preferences;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;

/**
 * A layout manager representing the Solitaire card layout. It expects a constraint indicating the card location
 * in logical terms then lays out the cards based on said location
 */
public class SolitaireLayout extends Layout {
    public static enum Position {
        DECK, FOUNDATION, TABLEAU
    }

    static class Constraint {

        private Position pos;
        private int offset;

        public Constraint(Position p, int offset) {
            pos = p;
            this.offset = offset;
        }
        
        public Position getPosition() {
            return pos;
        }
        
        public int getOffset() {
            return offset;
        }
    }
    public static final Constraint DECK_0 = new Constraint(Position.DECK, 0);
    public static final Constraint DECK_1 = new Constraint(Position.DECK, 1);
    public static final Constraint TABLEAU_0 = new Constraint(Position.TABLEAU, 0);
    public static final Constraint TABLEAU_1 = new Constraint(Position.TABLEAU, 1);
    public static final Constraint TABLEAU_2 = new Constraint(Position.TABLEAU, 2);
    public static final Constraint TABLEAU_3 = new Constraint(Position.TABLEAU, 3);
    public static final Constraint TABLEAU_4 = new Constraint(Position.TABLEAU, 4);
    public static final Constraint TABLEAU_5 = new Constraint(Position.TABLEAU, 5);
    public static final Constraint TABLEAU_6 = new Constraint(Position.TABLEAU, 6);
    public static final Constraint FOUNDATION_0 = new Constraint(Position.FOUNDATION, 0);
    public static final Constraint FOUNDATION_1 = new Constraint(Position.FOUNDATION, 1);
    public static final Constraint FOUNDATION_2 = new Constraint(Position.FOUNDATION, 2);
    public static final Constraint FOUNDATION_3 = new Constraint(Position.FOUNDATION, 3);

    @Override
    public void addLayoutComponent(Object value, Component comp, Container c) {
        if (value != null) {
            comp.putClientProperty("cons", value);
        }
    }

    @Override
    public Object getComponentConstraint(Component comp) {
        return comp.getClientProperty("cons");
    }

    @Override
    public boolean isConstraintTracking() {
        return true;
    }

    @Override
    public boolean isOverlapSupported() {
        return true;
    }
    
    private final int[] FOUNDATION_POSITIONS_X = new int[4];
    private final int[] TABLEAU_DEPTH = new int[7];

    @Override
    public void layoutContainer(Container parent) {
        int componentCount = parent.getComponentCount();
        if (componentCount < 1) {
            return;
        }
        int tableaDepthIncrement = Display.getInstance().convertToPixels(2, false);
        for (int iter = 0; iter < TABLEAU_DEPTH.length; iter++) {
            TABLEAU_DEPTH[iter] = 0;
        }
        // we assume all components are exactly the same size (cards)
        Component cmp = parent.getComponentAt(0);
        Style stl = cmp.getStyle();
        int cmpWidth = cmp.getPreferredW() + stl.getMargin(Component.LEFT) + stl.getMargin(Component.RIGHT);
        int cmpHeight = cmp.getPreferredH() + stl.getMargin(Component.TOP) + stl.getMargin(Component.BOTTOM);
        int width = parent.getLayoutWidth() - parent.getSideGap() - parent.getStyle().getPadding(false, Component.RIGHT) - parent.getStyle().getPadding(false, Component.LEFT);
        int height = parent.getLayoutHeight() - parent.getBottomGap() - parent.getStyle().getPadding(false, Component.BOTTOM) - parent.getStyle().getPadding(false, Component.TOP);
        int x = parent.getStyle().getPadding(parent.isRTL(), Component.LEFT);
        int y = parent.getStyle().getPadding(false, Component.TOP);
        int deck1X = x;
        
        int tableauWidth = width / 7;
        int deck2X = deck1X + tableauWidth;

        boolean draw3 = Preferences.get("3cards", false);
        FOUNDATION_POSITIONS_X[3] = x + width - tableauWidth;
        FOUNDATION_POSITIONS_X[2] = FOUNDATION_POSITIONS_X[3] - tableauWidth;
        FOUNDATION_POSITIONS_X[1] = FOUNDATION_POSITIONS_X[2] - tableauWidth;
        FOUNDATION_POSITIONS_X[0] = FOUNDATION_POSITIONS_X[1] - tableauWidth;
        
        // place the tableau a bit below the top cards but not at the complete bottom.
        int tableauY = y + cmpHeight;
        for (Component currentCmp : parent) {
            Constraint cons = (Constraint) getComponentConstraint(currentCmp);
            if (cons != null) {
                currentCmp.setWidth(cmpWidth);
                currentCmp.setHeight(cmpHeight);
                switch (cons.pos) {
                    case DECK:
                        currentCmp.setY(y);
                        if (cons.offset == 0) {
                            currentCmp.setX(deck1X);
                        } else {
                            currentCmp.setX(deck2X);
                        }
                        break;
                    case FOUNDATION:
                        currentCmp.setY(y);
                        currentCmp.setX(FOUNDATION_POSITIONS_X[cons.offset]);
                        break;
                    case TABLEAU:
                        currentCmp.setY(tableauY + TABLEAU_DEPTH[cons.offset]);
                        TABLEAU_DEPTH[cons.offset] += tableaDepthIncrement;
                        currentCmp.setX(cons.offset * tableauWidth);
                        
                        // special case for the drop targets for the foundation, we want them to stretch all the way down...
                        if(!(currentCmp instanceof CardComponent)) {
                            currentCmp.setHeight(height - currentCmp.getY());
                        }
                        break;
                }
            } 
        }
        
        // shift right the top 2 deck card in draw 3 cards mode
        if(draw3) {
            int found = 0;
            for(int iter = parent.getComponentCount() - 1 ; iter >= 0 ; iter--) {
                Component c = parent.getComponentAt(iter);
                Constraint con = (Constraint)getComponentConstraint(c);
                if(con.pos == Position.DECK && con.offset == 1) {
                    c.setX(c.getX() + Display.getInstance().convertToPixels((2 - found) * 2, true));
                    found++;
                    if(found == 2) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize(Container parent) {
        Display d = Display.getInstance();
        return new Dimension(d.getDisplayWidth(), d.getDisplayHeight());
    }
    
}
