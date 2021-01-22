package GenericFrequencyTables.src.genericFrequencyTables;

import java.util.Objects;

public abstract class Card {
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
        return "Card{" + wert +", "+ farbe + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(wert, farbe);
    }

}
