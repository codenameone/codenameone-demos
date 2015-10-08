package com.codename1.apps.solitaire;


import com.codename1.components.InteractionDialog;
import com.codename1.io.Log;
import com.codename1.io.Preferences;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.WeakHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solitaire {
    private static final char SUITE_SPADE = 's';
    private static final char SUITE_HEART = 'h';
    private static final char SUITE_DIAMOND = 'd';
    private static final char SUITE_CLUB = 'c';
    
    private Resources cards;

    private Form current;
    private Resources theme;

    private WeakHashMap<String, Image> cardImageCache = new WeakHashMap<>();
    
    private final static Card[] deck;
    static {
        // we initialize constant card values that will be useful later on in the game
        deck = new Card[52];
        for(int iter = 0 ; iter < 13 ; iter++) {
            deck[iter] = new Card(SUITE_SPADE, iter + 2);
            deck[iter + 13] = new Card(SUITE_HEART, iter + 2);
            deck[iter + 26] = new Card(SUITE_DIAMOND, iter + 2);
            deck[iter + 39] = new Card(SUITE_CLUB, iter + 2);
        }
    }

    private static final int[] DPIS = {Display.DENSITY_MEDIUM, Display.DENSITY_HIGH, Display.DENSITY_VERY_HIGH, Display.DENSITY_HD};
    private static final int[] WIDTHS = {76, 101, 135, 217};
    private static final int[] SIZES = {36, 44, 55, 76, 85,
                                                                93, 101, 109, 118, 124,
                                                                135, 155, 173, 190, 208,
                                                                217, 230, 240, 250, 270};
    private static int defaultZoom;
    private static int currentZoom;
    
    
    /**
     * We use this method to calculate a "fake" DPI based on screen resolution rather than its actual DPI
     * this is useful so we can have large images on a tablet
     */
    private int calculateDPI() {
        int min = Math.min(Display.getInstance().getDisplayHeight(),  Display.getInstance().getDisplayWidth());
        // we need 7 cards to fit in the screen,
        if(min > 1100) {
            defaultZoom = 15;
            return Display.DENSITY_HD;
        }
        if(min > 700) {
            defaultZoom = 10;
            return Display.DENSITY_VERY_HIGH;
        }
        if(min > 640) {
            defaultZoom = 6;
            return Display.DENSITY_HIGH;
        }
        defaultZoom = 3;
        return Display.DENSITY_MEDIUM;
    }

    public void init(Object context) {
        try {
            theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            int dpi =  Preferences.get("dpi", calculateDPI());
            cards = Resources.open("/gamedata.res",dpi);
            currentZoom = Preferences.get("zoom", defaultZoom);
        } catch(IOException e){
            e.printStackTrace();
        }
        // Pro users - uncomment this code to get crash reports sent to you automatically
        Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });
    }
    
    public Image getCardImage(String name) {
        Image i = cardImageCache.get(name);
        if(i != null) {
            return i;
        }
        i = cards.getImage(name);
        if(i.getWidth() != SIZES[currentZoom]) {
            i = i.scaledWidth(SIZES[currentZoom]);
        }
        cardImageCache.put(name, i);
        return i;
    }

    void refreshZoom(Container solitairContainer) {
        int dpi =  DPIS[currentZoom / 5];
        Preferences.set("dpi", dpi);
        Preferences.set("zoom", currentZoom);
        try {
            cards = Resources.open("/gamedata.res",dpi);
            cardImageCache.clear();
            Image cardBack = getCardImage("card_back.png");
            for(Component c : solitairContainer) {
                CardComponent crd = (CardComponent)c;
                crd.setImages(getCardImage(crd.getCard().getFileName()), cardBack);
            }
            solitairContainer.revalidate();
            solitairContainer.getParent().replace(solitairContainer.getParent().getComponentAt(0), createPlacementContainer(solitairContainer), null);
        } catch(IOException e) {
            Log.e(e);
        }        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        final Container solitairContainer = new Container(new SolitaireLayout());
        Form sol = new Form("Solitaire") /*{
            private float zoom = currentZoom;
            private double pinchDistance;

            private double distance(int[] x, int[] y) {
                int disx = x[0] - x[1];
                int disy = y[0] - y[1];
                return Math.sqrt(disx * disx + disy * disy);
            }

            @Override
            public void pointerDragged(int[] x, int[] y) {
                if (x.length > 1) {
                    double currentDis = distance(x, y);

                    // prevent division by 0
                    if (pinchDistance <= 0) {
                        pinchDistance = currentDis;
                    }
                    double scale = currentDis / pinchDistance;
                    if (pinch((float)scale)) {
                        refreshZoom(solitairContainer);
                        return;
                    }
                }
                super.pointerDragged(x, y);
            }
            
            
            @Override
            protected boolean pinch(float scale) {
                zoom = Math.max(1, zoom) * scale;
                zoom = Math.min(zoom, SIZES.length - 1);
                
                // don't allow zooming beyond display size
                int displaySize = Math.max(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
                while(SIZES[(int)zoom] > displaySize / 7) {
                    zoom--;
                }
                
                if(((int)zoom) != currentZoom) {
                    currentZoom = (int)zoom;
                }
                return true; 
            }
        }*/;
        Toolbar tb = new Toolbar();
        sol.setToolBar(tb);
        tb.addCommandToRightBar(new Command("\ue8b5") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                GameMoveQueue.undo();
            }
        });
        tb.addCommandToRightBar(new Command("\ue8b4") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                GameMoveQueue.redo();
            }
        });
        tb.addCommandToRightBar(new Command("\ue8bc") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                returnToDeck(solitairContainer);
                dealFromDeck(solitairContainer);
                GameMoveQueue.reset();
            }
        });
        tb.addCommandToOverflowMenu(new ToggleCommand("Autoplay", true, "autoplay"));
        tb.addCommandToOverflowMenu(new ToggleCommand("Draw 3 Cards", false, "3cards"));
        tb.addCommandToOverflowMenu(new Command("Zoom In") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int z = currentZoom + 1;
                if(currentZoom < SIZES.length) {
                    // don't allow zooming beyond display size
                    int displaySize = Math.max(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
                    while(SIZES[(int)z] > displaySize / 7) {
                        z--;
                    }
                    if(z > currentZoom) {
                        currentZoom = z;
                        refreshZoom(solitairContainer);
                    }
                }
            }
        });
        tb.addCommandToOverflowMenu(new Command("Zoom Out") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(currentZoom > 0) {
                    currentZoom--;
                    refreshZoom(solitairContainer);
                }
            }
        });
        tb.addCommandToOverflowMenu(new Command("Default Zoom") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(currentZoom != defaultZoom) {
                    currentZoom = defaultZoom;
                    refreshZoom(solitairContainer);
                }
            }
        });
        tb.addCommandToOverflowMenu(new Command("About") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("About", "A fun little game to demonstrate the versatility of CodenameOne\n\nVersion: " + Display.getInstance().getProperty("AppVersion", "1.0"), "OK", null);
            }
        });
        final List<Card> shuffledDeck = new ArrayList<>(Arrays.asList(deck));
        Collections.shuffle(shuffledDeck);
        
        
        sol.setLayout(new LayeredLayout());
        sol.addComponent(createPlacementContainer(solitairContainer));
        sol.addComponent(solitairContainer);
        sol.setScrollable(false);
        Image cardBack = getCardImage("card_back.png");
        
        for(Card c : shuffledDeck) {
            CardComponent cmp = new CardComponent(c, getCardImage(c.getFileName()), cardBack, false);
            solitairContainer.addComponent(SolitaireLayout.DECK_0, cmp);
        }
        
        sol.show();
        Display.getInstance().callSerially(() -> {
            dealFromDeck(solitairContainer);
        });
    }

    private Container createPlacementContainer(Container solitairContainer) {
        Container placementContainer = new Container(new SolitaireLayout());
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.DECK_0);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.DECK_1);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.FOUNDATION_0);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.FOUNDATION_1);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.FOUNDATION_2);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.FOUNDATION_3);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_0);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_1);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_2);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_3);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_4);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_5);
        createPlacementComponent(placementContainer, solitairContainer, SolitaireLayout.TABLEAU_6);
        return placementContainer;
    }
    
    private void createPlacementComponent(final Container cnt, final Container cardsContainer, final SolitaireLayout.Constraint cons) {
        Button l = new Button(" ") {
            private boolean validDrop;
            @Override
            protected void dragEnter(Component dragged) {
                validDrop = isValidMove((CardComponent)dragged, cons, getTopOf(cardsContainer, cons.getPosition(), cons.getOffset()));
                if(validDrop) {
                    getUnselectedStyle().setBgColor(0xff);
                } else {
                    getUnselectedStyle().setBgColor(0xff0000);
                }
                if(getUnselectedStyle().getBgTransparency() == 0) {
                    getUnselectedStyle().setBgTransparency(255);
                    super.dragEnter(dragged);
                    repaint();
                } else {
                    super.dragEnter(dragged);
                }
            }
            
            @Override
            public void drop(Component dragged, int x, int y) {
                getUnselectedStyle().setBgTransparency(0);
                if(validDrop) {
                    final java.util.List<CardComponent> group = (java.util.List<CardComponent>)dragged.getClientProperty("dragGroup");
                    final SolitaireLayout.Constraint originalConstraint = (SolitaireLayout.Constraint)cardsContainer.getLayout().getComponentConstraint(dragged);

                    GameMoveQueue.perform(new GameMove() {
                        @Override
                        public void doMove() {
                            CardComponent d = componentFromCard(cardsContainer, ((CardComponent)dragged).getCard());
                            cardsContainer.removeComponent(d);
                            cardsContainer.addComponent(cons, d);
                            if(group != null) {
                                for(CardComponent cur : group) {
                                    cur = componentFromCard(cardsContainer, cur.getCard());
                                    cardsContainer.removeComponent(cur);
                                    cur.setVisible(true);
                                    cardsContainer.addComponent(cons, cur);
                                }
                            }
                            d.putClientProperty("dragGroup", null);

                            cardsContainer.revalidate();
                        }

                        @Override
                        public void undoMove() {
                            CardComponent d = componentFromCard(cardsContainer, ((CardComponent)dragged).getCard());
                            cardsContainer.removeComponent(d);
                            cardsContainer.addComponent(originalConstraint, d);
                            if(group != null) {
                                for(CardComponent cur : group) {
                                    cur = componentFromCard(cardsContainer, cur.getCard());
                                    cardsContainer.removeComponent(cur);
                                    cardsContainer.addComponent(originalConstraint, cur);
                                }
                            }
                            cardsContainer.animateLayoutAndWait(150);
                        }
                    });
                    Display.getInstance().callSerially(() -> {
                        checkAutoMoves(cardsContainer);
                    });
                } else {
                    cardsContainer.revalidate();
                }
            }
            
            @Override
            protected void dragExit(Component dragged) {
                getUnselectedStyle().setBgTransparency(0);
                super.dragExit(dragged); 
                getParent().repaint();
            }
        };
        l.setUIID("Label");
        Image back = getCardImage("card_back.png");
        Style s = l.getUnselectedStyle();
        s.setBgImage(back);
        s.setBackgroundType(Style.BACKGROUND_IMAGE_ALIGNED_TOP);
        l.setPreferredSize(new Dimension(back.getWidth() + s.getPadding(Component.LEFT) + s.getPadding(Component.RIGHT), 
                    back.getHeight() + s.getPadding(Component.LEFT) + s.getPadding(Component.RIGHT)));
        s.setBgColor(0xff);
        s.setOpacity(100);
        l.setDropTarget(true);
        if(cons.getPosition() == SolitaireLayout.Position.DECK && cons.getOffset() == 0) {
            // flip the deck
            l.addActionListener((e) -> {
                GameMoveQueue.perform(new GameMove() {
                    @Override
                    public void doMove() {
                        for(int iter = 0 ; iter < 52 ; iter++) {
                            final Component cmp = cardsContainer.getComponentAt(iter);
                            final SolitaireLayout.Constraint con = (SolitaireLayout.Constraint)cardsContainer.getLayout().getComponentConstraint(cmp);
                            if(con.getPosition() == SolitaireLayout.Position.DECK) {
                                cardsContainer.removeComponent(cmp);
                                ((CardComponent)cmp).setFacingUp(false);
                                cardsContainer.addComponent(0, SolitaireLayout.DECK_0, cmp);
                            }
                        }
                        cardsContainer.revalidate();
                    }

                    @Override
                    public void undoMove() {
                        for(int iter = 0 ; iter < 52 ; iter++) {
                            final Component cmp = cardsContainer.getComponentAt(iter);
                            final SolitaireLayout.Constraint con = (SolitaireLayout.Constraint)cardsContainer.getLayout().getComponentConstraint(cmp);
                            if(con.getPosition() == SolitaireLayout.Position.DECK) {
                                cardsContainer.removeComponent(cmp);
                                ((CardComponent)cmp).setFacingUp(true);
                                cardsContainer.addComponent(SolitaireLayout.DECK_1, cmp);
                            }
                        }
                        cardsContainer.revalidate();
                    }
                });
            });
        }
        cnt.addComponent(cons, l);
    }

    public static java.util.List<CardComponent> getTableauColumn(Container cardsContainer, int offset) {
        ArrayList<CardComponent> dat = new ArrayList<>();
        for(int iter = 0 ; iter < 52 ; iter++) {
            Component cmp = cardsContainer.getComponentAt(iter);
            SolitaireLayout.Constraint con = (SolitaireLayout.Constraint)cardsContainer.getLayout().getComponentConstraint(cmp);
            if(con.getPosition() == SolitaireLayout.Position.TABLEAU && con.getOffset() == offset) {
                dat.add((CardComponent)cmp);
            }
        }
        return dat;
    }    
    
    public static CardComponent getTopOf(Container cardsContainer, SolitaireLayout.Position position, int offset) {
        for(int iter = 51 ; iter >= 0 ; iter--) {
            Component cmp = cardsContainer.getComponentAt(iter);
            SolitaireLayout.Constraint con = (SolitaireLayout.Constraint)cardsContainer.getLayout().getComponentConstraint(cmp);
            if(con.getPosition() == position && con.getOffset() == offset) {
                return (CardComponent)cmp;
            }
        }
        return null;
    }
    
    public boolean isValidMove(CardComponent crd, SolitaireLayout.Constraint dest, CardComponent top) {
        switch(dest.getPosition()) {
            case DECK:
                return false;
                
            case TABLEAU:
                Card tc = crd.getCard();
                if(top == null) {
                    return tc.isKing();
                }
                Card below = top.getCard();
                return tc.isBlack() != below.isBlack() && tc.numericRank() == below.numericRank() - 1;
                
            case FOUNDATION:
                Card c = crd.getCard();
                if(top == null) {
                    return c.isAce();
                }

                return top.getCard().isNextCard(c);
                
            default:
                throw new RuntimeException();
        }
    }
    
    /**
     * Traverses the deck and looks for cards to move/flip
     * @param cards the cards
     */
    public static void checkAutoMoves(final Container cards) {
        final CardComponent[] foundation = new CardComponent[] {
            getTopOf(cards, SolitaireLayout.Position.FOUNDATION, 0),
            getTopOf(cards, SolitaireLayout.Position.FOUNDATION, 1),
            getTopOf(cards, SolitaireLayout.Position.FOUNDATION, 2),
            getTopOf(cards, SolitaireLayout.Position.FOUNDATION, 3)            
        };
        
        int count = 0;
        for(int iter = 0 ; iter < foundation.length ; iter++) {
            if(foundation[iter] != null && foundation[iter].getCard().isKing()) {
                count++;
            } else {
                break;
            }
        }
        
        if(count == foundation.length) {
            // compleation animation
            InteractionDialog wellDone = new InteractionDialog();
            wellDone.setAnimateShow(false);
            wellDone.setDialogUIID("Container");
            wellDone.setUIID("Container");
            Label w = new Label("Well Done!!!");
            w.setUIID("WellDone");
            wellDone.setLayout(new BorderLayout());
            wellDone.addComponent(BorderLayout.CENTER, w);
            wellDone.show(0, 0, 0, 0);
            Display.getInstance().callSerially(() -> {
                Random rnd = new Random();
                for(Component c : cards) {
                    if(rnd.nextInt(2) == 1) {
                        c.setX(-c.getWidth());
                    } else {
                        c.setX(cards.getWidth());
                    }
                    if(rnd.nextInt(2) == 1) {
                        c.setY(-c.getHeight());
                    } else {
                        c.setY(cards.getHeight());
                    }
                }
                cards.animateUnlayoutAndWait(4000, 255);
                wellDone.dispose();
                returnToDeck(cards);
                dealFromDeck(cards);
                GameMoveQueue.reset();
            });
            return;
        }
        
        final CardComponent[] t = new CardComponent[] {
            getTopOf(cards, SolitaireLayout.Position.DECK, 1),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 0),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 1),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 2),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 3),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 4),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 5),
            getTopOf(cards, SolitaireLayout.Position.TABLEAU, 6)
        };
        
        for(int iter = 1 ; iter < t.length ; iter++) {
            if(t[iter] != null && !t[iter].isFacingUp()) {
                final int offset = iter;
                GameMoveQueue.perform(new GameMove(true) {
                    @Override
                    public void doMove() {
                        componentFromCard(cards, t[offset].getCard()).flip();
                    }

                    @Override
                    public void undoMove() {
                        componentFromCard(cards, t[offset].getCard()).flip();
                    }
                });
            }
        }

        if(!Preferences.get("autoplay", true)) {
            return;
        }
        
        // check for foundation card
        for(int iter = 0 ; iter < t.length ; iter++) {
            for(int found = 0 ; found < foundation.length ; found++) {
                if(t[iter] == null) {
                    continue;
                }
                // don't autoplay too aggresively
                if(isLargestFoundation(found, foundation)) {
                    continue;
                }
                if(checkFoundation(t[iter], foundation[found])) {
                    final int offset = iter;
                    final int finalFound = found;
                    final SolitaireLayout.Constraint originalConstraint = (SolitaireLayout.Constraint)cards.getLayout().getComponentConstraint(t[offset]);
                    GameMoveQueue.perform(new GameMove() {
                        @Override
                        public void doMove() {
                            CardComponent crd = componentFromCard(cards, t[offset].getCard());
                            cards.removeComponent(crd);
                            cards.addComponent(new SolitaireLayout.Constraint(SolitaireLayout.Position.FOUNDATION, finalFound), crd);
                            cards.animateLayoutAndWait(100);
                        }

                        @Override
                        public void undoMove() {
                            CardComponent crd = componentFromCard(cards, t[offset].getCard());
                            cards.removeComponent(crd);
                            cards.addComponent(originalConstraint, crd);
                            cards.animateLayoutAndWait(100);
                        }
                    });
                    checkAutoMoves(cards);
                    return;
                }
            }
        }
    }
    
    private static boolean isLargestFoundation(int offset, CardComponent[] foundation) {
        if(foundation[offset] == null) {
            return false;
        }
        for(int iter = 0 ; iter < foundation.length ; iter++) {
            if(iter == offset || foundation[iter] == null) {
                continue;
            }
            if(foundation[iter].getCard().numericRank() >= foundation[offset].getCard().numericRank()) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkFoundation(CardComponent tabOrDeck, CardComponent foundation) {
        Card c = tabOrDeck.getCard();
        if(foundation == null) {
            return c.isAce();
        }

        return foundation.getCard().isNextCardFoundation(c);
    }
    
    private static void dealFromDeck(Container c) {
        int count = c.getComponentCount();
        for(int parentCounter = 0 ; parentCounter < 7 ; parentCounter++) {
            for(int iter = parentCounter ; iter < 7 ; iter++) {
                CardComponent crd = (CardComponent)c.getComponentAt(count - 1);
                c.removeComponent(crd);
                c.addComponent(new SolitaireLayout.Constraint(SolitaireLayout.Position.TABLEAU, iter), crd);
                c.animateLayoutAndWait(100);
                if(iter == parentCounter) {
                    crd.flip();
                }
                count--;
            }
        }
        checkAutoMoves(c);
    }
    
    private static void returnToDeck(Container c) {
        int cmpCount = c.getComponentCount();
        final List<CardComponent> shuffledDeck = new ArrayList<>();
        for(int iter = 0 ; iter < cmpCount ; iter++) {
            CardComponent crd = (CardComponent)c.getComponentAt(0);
            c.removeComponent(crd);
            shuffledDeck.add(crd);
            crd.setFacingUp(false);
        }

        Collections.shuffle(shuffledDeck);
        
        for(CardComponent current : shuffledDeck) {
            c.addComponent(SolitaireLayout.DECK_0, current);
        }
        
        c.animateLayoutAndWait(300);
    }
    
    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }    
}
