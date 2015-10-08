package com.codename1.apps.solitaire;

import com.codename1.ui.Component;
import com.codename1.ui.Container;

/**
 * All changes in the game happen via a game move that is both doable and undoable. 
 */
public abstract class GameMove {
    private boolean hidden;
    public GameMove() {
    }
    
    public GameMove(boolean hidden) {
        this.hidden = hidden;
    }
    
    /**
     * Allows hiding a game move, which should be undoable but might leave the user in a state that doesn't make sense
     * e.g. flipping a card automatically in the tableau which should be undoable but we don't need to reach that state
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Since a card component can be "flipped' its reference might change and break the undo we should only work with
     * Cards and convert them dynamically
     */
    public CardComponent componentFromCard(Container cnt, Card crd) {
        for(Component c : cnt) {
            if(((CardComponent)c).getCard() == crd) {
                return (CardComponent)c;
            }
        }
        throw new RuntimeException("Invalid card");
    }
    
    public abstract void doMove();
    public abstract void undoMove();
}
