package GenericFrequencyTables.src.genericFrequencyTables;

import java.util.Random;

public class BlackCard extends Card {
    public BlackCard (Card.Suit pSuit, Card.Rank pRank){
        if (pSuit == Card.Suit.SPADES || pSuit == Card.Suit.CLUBS){
            farbe = pSuit;
            wert = pRank;
            return;
        }
        throw new IllegalArgumentException();
    }
    public BlackCard(){
        Random rand = new Random();
        wert = Card.Rank.values()[rand.nextInt(8)];  //0-7
        farbe = Card.Suit.values()[rand.nextInt(2) +2];//2, 3
    }
}
