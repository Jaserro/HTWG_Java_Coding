package QuickSort.src.andere;

import java.util.Random;

public class BlackCard extends Card {
    public BlackCard (Suit pSuit, Rank pRank){
        if (pSuit == Suit.SPADES || pSuit == Suit.CLUBS){
            farbe = pSuit;
            wert = pRank;
            return;
        }
        throw new IllegalArgumentException();
    }
    public BlackCard(){
        Random rand = new Random();
        wert = Rank.values()[rand.nextInt(8)];  //0-7
        farbe = Suit.values()[rand.nextInt(2) +2];//2, 3
    }
}
