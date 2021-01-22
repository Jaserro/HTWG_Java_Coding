package QuickSort.src.andere;

import java.util.Objects;

public abstract class Card implements Comparable{
    protected enum Rank{SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ASS}
    protected enum Suit{HEARTS, DIAMONDS, SPADES, CLUBS}


    protected Rank wert;
    protected Suit farbe;

    public Rank getWert() {
        return wert;
    }

    public Suit getFarbe() {
        return farbe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return wert == card.wert &&
                farbe == card.farbe;
    }

    @Override
    public String toString() {
        return "{" + wert +", "+ farbe + '}';
    }

    @Override
    public int hashCode() {
        int tmpHash = 0;
        if(this.farbe == Suit.DIAMONDS){
            tmpHash += 100;
        }else if (this.farbe == Suit.HEARTS){
            tmpHash += 200;
        }
        else if (this.farbe == Suit.SPADES){
            tmpHash += 300;
        }
        else if (this.farbe == Suit.CLUBS){
            tmpHash += 400;
        }

        if (this.wert == Rank.SEVEN){
            tmpHash += 7;
        }else if (this.wert == Rank.EIGHT){
            tmpHash += 8;
        }else if (this.wert == Rank.NINE){
            tmpHash += 9;
        }else if (this.wert == Rank.TEN){
            tmpHash += 10;
        }else if (this.wert == Rank.JACK){
            tmpHash += 11;
        }else if (this.wert == Rank.QUEEN){
            tmpHash += 12;
        }else if (this.wert == Rank.KING){
            tmpHash += 13;
        }else if (this.wert == Rank.ASS){
            tmpHash += 14;
        }
        return tmpHash;
    }

    @Override
    public int compareTo(Object o) {
        //DIAMONDS < HEARTS < SPADES < CLUBS
        if (o instanceof Card){
            return this.hashCode() - o.hashCode();
        }
        throw new IllegalArgumentException();
    }

}
