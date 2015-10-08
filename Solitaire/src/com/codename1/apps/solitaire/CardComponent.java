package com.codename1.apps.solitaire;

import com.codename1.io.Preferences;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.animations.FlipTransition;
import com.codename1.ui.layouts.BorderLayout;

/**
 * A component representing a specific playing card, in addition to the card logic it can also indicate whether it
 * is flipped or not
 */
public class CardComponent extends Button {
    private Card c;
    private Image front;
    private Image back;
    private boolean faceingUp;
    public CardComponent(Card c, Image front, Image back, boolean faceingUp) {
        setUIID("Label");
        this.c = c;
        this.back = back;
        this.front = front;
        this.faceingUp = faceingUp;
        setDraggable(faceingUp);
        if(faceingUp) {
            setIcon(front);
        } else {
            setIcon(back);
        }
        getUnselectedStyle().setBgTransparency(0);
        getSelectedStyle().setBgTransparency(0);
        addActionListener((e) -> {
            SolitaireLayout.Constraint cons = (SolitaireLayout.Constraint)getParent().getLayout().getComponentConstraint(CardComponent.this);
            if(cons.getPosition() == SolitaireLayout.Position.DECK && cons.getOffset() == 0) {
                final Container cnt = getParent();
                
                // this might change during gameplay so we need to keep it
                final boolean draw3Mode = Preferences.get("3cards", false);
                
                GameMoveQueue.perform(new GameMove() {
                    @Override
                    public void doMove() {
                        CardComponent card = componentFromCard(cnt, c);
                        cnt.removeComponent(card);
                        cnt.addComponent(SolitaireLayout.DECK_1, card);
                        cnt.animateLayoutAndWait(100);
                        card.flip();
                        if(draw3Mode) {
                            for(int iter = 0 ; iter < 2 ; iter++) {
                                card = Solitaire.getTopOf(cnt, SolitaireLayout.Position.DECK, 0);
                                if(card == null) {
                                    return;
                                }
                                cnt.removeComponent(card);
                                cnt.addComponent(SolitaireLayout.DECK_1, card);
                                cnt.animateLayoutAndWait(100);
                                card.flip();
                            }
                        }
                    }

                    @Override
                    public void undoMove() {
                        if(draw3Mode) {
                            for(int iter = 0 ; iter < 3 ; iter++) {
                                CardComponent card = Solitaire.getTopOf(cnt, SolitaireLayout.Position.DECK, 1);
                                if(card == null) {
                                    return;
                                }
                                cnt.removeComponent(card);
                                cnt.addComponent(cnt.getComponentCount() - (3 - iter),SolitaireLayout.DECK_0, card);
                                cnt.animateLayoutAndWait(100);
                                card.flip();
                            }
                        } else {
                            CardComponent card = componentFromCard(cnt, c);
                            cnt.removeComponent(card);
                            cnt.addComponent(SolitaireLayout.DECK_0, card);
                            cnt.animateLayoutAndWait(100);
                            card.flip();
                        }
                    }
                });
                Solitaire.checkAutoMoves(cnt);
            }
        });
    }    

    public void setImages(Image front, Image back) {
        this.front = front;
        this.back = back;
        if(faceingUp) {
            setIcon(front);
        } else {
            setIcon(back);
        }
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible); 
        if(visible) {
            // this could be a result of a drag operation getting canceled
            java.util.List<CardComponent> grp = (java.util.List<CardComponent>)getClientProperty("dragGroup");
            if(grp != null) {
                for(CardComponent c : grp) {
                    c.setVisible(true);
                }
            }
        }
    }

    
    @Override
    protected Image getDragImage() {
        putClientProperty("dragGroup", null);
        SolitaireLayout.Constraint cons = (SolitaireLayout.Constraint)getParent().getLayout().getComponentConstraint(this);
        if(cons.getPosition() == SolitaireLayout.Position.TABLEAU) {
            CardComponent crd = Solitaire.getTopOf(getParent(), SolitaireLayout.Position.TABLEAU, cons.getOffset());
            if(crd != this) {
                // we have a card group...
                java.util.List<CardComponent> l = Solitaire.getTableauColumn(getParent(), cons.getOffset());
                while(l.size() > 0 && l.get(0) != this) {
                    l.remove(0);
                }
                l.remove(0);
                
                int twoMM = Display.getInstance().convertToPixels(2, false);
                Image m = Image.createImage(front.getWidth(), front.getHeight() + twoMM * (l.size() + 1), 0);
                Graphics g = m.getGraphics();
                g.drawImage(front, 0, 0);
                g.translate(0, twoMM);
                for(CardComponent current : l) {
                    g.drawImage(current.front, 0, 0);
                    g.translate(0, twoMM);
                    current.setVisible(false);
                }
                putClientProperty("dragGroup", l);
                return m;
            }
        }
        return front;
    }
    
    public void flip() {
        faceingUp = !faceingUp;
        if(getParent() != null) {
            getParent().replaceAndWait(this, new CardComponent(c, front, back, faceingUp), new FlipTransition(-1, 150));
        }
    }
    
    public boolean isFacingUp() {
        return faceingUp;
    }
    
    public void setFacingUp(boolean b) {
        if(faceingUp != b) {
            if(getParent() != null) {
                flip();
            } else {
                faceingUp = b;
                setIcon(back);
            }
        }
    }
    
    public Card getCard() {
        return c;
    }
}
