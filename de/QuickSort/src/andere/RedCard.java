package andere;

import java.util.Random;

public class RedCard extends Card {
    public RedCard (Suit pSuit, Rank pRank){
        if (pSuit == Suit.HEARTS || pSuit == Suit.DIAMONDS){
            farbe = pSuit;
            wert = pRank;
            return;
        }
        throw new IllegalArgumentException();
    }

    public RedCard(){
        Random rand = new Random();
        farbe = Suit.values()[rand.nextInt(2)]; //0, 1
        wert = Rank.values()[rand.nextInt(8)]; //0-7
    }
}
