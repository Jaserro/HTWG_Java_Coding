package quicksort;
import andere.*;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Test<T>{

    private static ArrayList<Integer> countLI = new ArrayList<>();
    private static ArrayList<String> countLS = new ArrayList<>();
    private static ArrayList<Card> countLC = new ArrayList<>();


    public static void main(String[] args) throws IOException {

        testZahlen(200, 100);
        testkafka();
        testCard(1000,"gemischt"); // "rot", "schwarz", "gemischt"

        Integer[] countI = new Integer[countLI.size()];
        countLI.toArray(countI);

        String[] countS = new String[countLS.size()];
        countLS.toArray(countS);

        Card[] countC = new Card[countLC.size()];
        countLC.toArray(countC);



        long start = System.nanoTime(); // aktuelle Zeit in nsec

//        System.out.println(java.util.Arrays.toString(countI));
//        System.out.println(java.util.Arrays.toString(countS));
        System.out.println(java.util.Arrays.toString(countC));
        //unser sort

//        QuickSort.hybQuickSort(countI);
//        QuickSort.hybQuickSort3Median(countI);

//        QuickSort.hybQuickSort(countS);
//        QuickSort.hybQuickSort3Median(countS);

        QuickSort.hybQuickSort(countC);
//        QuickSort.hybQuickSort3Median(countC);


        long end = System.nanoTime();
        double elapsedTime = (double)(end-start)/1.0e06; // Zeit in msec


//        System.out.println(java.util.Arrays.toString(countI));
//        System.out.println(java.util.Arrays.toString(countS));
        System.out.println(java.util.Arrays.toString(countC));

        System.out.println("Zeit: "+ elapsedTime);
    }

    private static void testZahlen(int pAnz, int m){
        for (int i = 0; i < pAnz; i++) {
            countLI.add((int) (Math.random() * m));
        }
    }

    private static void testkafka() throws IOException {

        LineNumberReader in;
        in = new LineNumberReader(new FileReader("de/QuickSort/Kafka_Der_Prozess.txt"));
        String line;

        // Text einlesen und Häufigkeiten aller Wörter bestimmen:
        while ((line = in.readLine()) != null) {
            String[] wf = line.split("[^a-z^A-Z^ß^ä^ö^ü^Ä^Ö^Ü]+");
            for (String w: wf) {
                if (w.length() == 0 || w.length() == 1)
                    continue;
                if(!countLS.contains(w)){
                    countLS.add(w);
                }
            }
        }
    }

    private static void testCard(int pAnz, String pArt){
        switch (pArt){
            case"rot":
                for (int i = 0; i < pAnz; i++) {
                    RedCard c = new RedCard();
                    countLC.add(c);
                }
                return;
            case"schwarz":
                for (int i = 0; i < pAnz; i++) {
                    BlackCard c = new BlackCard();
                    countLC.add(c);
                }
                return;
            case"gemischt":
                Random rand = new Random();
                for (int i = 0; i < pAnz; i++) {
                    if (rand.nextInt() % 2 == 0) {
                        RedCard c = new RedCard();
                        countLC.add(c);
                    } else {
                        BlackCard c = new BlackCard();
                        countLC.add(c);
                    }
                }
                return;
            default: throw new IllegalArgumentException();
        }
    }

}
