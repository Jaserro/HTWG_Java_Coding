package GenericFrequencyTables.src.genericFrequencyTables;

import java.util.Random;

public class RedCard extends Card {
    public RedCard (Suit pSuit, Rank pRank){
        if (pSuit == Card.Suit.HEARTS || pSuit == Card.Suit.DIAMONDS){
            farbe = pSuit;
            wert = pRank;
            return;
        }
        throw new IllegalArgumentException();
    }

    public RedCard(){
        Random rand = new Random();
        wert = Rank.values()[rand.nextInt(8)]; //0-7
        farbe = Suit.values()[rand.nextInt(2)]; //0, 1
    }
}
