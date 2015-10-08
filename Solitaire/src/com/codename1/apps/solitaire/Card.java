package com.codename1.apps.solitaire;

/**
 * Represents a logical playing card, not the actual component. This is useful for game logic
 */
public class Card {
    private char suite;
    private int rank;

    public Card(char suite, int rank) {
        this.suite = suite;
        this.rank = rank;
    }

    public boolean isAce() {
        return rank == 14;
    }
    
    public boolean isKing() {
        return rank == 13;
    }
    
    public boolean isNextCardFoundation(Card c) {
        if(suite != c.suite) {
            return false;
        }
        if(isAce()) {
            return c.rank == 2;
        }
        return rank == c.rank - 1;
    }

    public boolean isNextCard(Card c) {
        if(isAce()) {
            return c.rank == 2;
        }
        return rank == c.rank - 1;
    }

    public int numericRank() {
        if(isAce()) {
            return 1;
        }
        return rank;
    }
    
    public boolean isBlack() {
        return suite == 's' || suite == 'c';
    }
    
    public String toString() {
        return rankToString() + " of " + suite;
    }
    
    private String rankToString() {
        if (rank > 10) {
            switch (rank) {
                case 11:
                    return "j";
                case 12:
                    return "q";
                case 13:
                    return "k";
                case 14:
                    return "a";
            }
        }
        return "" + rank;
    }

    public String getFileName() {
        return rankToString() + suite + ".png";
    }
    
}
