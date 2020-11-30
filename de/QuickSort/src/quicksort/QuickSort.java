package quicksort;
import  andere.*;

import java.util.Iterator;

public class QuickSort<T> {

    public static <T extends Comparable<T>> void hybQuickSort(T[] a){
        hybQuickSort(a, 0, a.length -1);
    }


    public static <T extends Comparable<T>> void  hybQuickSort(T[] a, int li, int re){
        if(re -li < 100){
            insertionSort(a, li, re);
            return;
        }
        int i = partition(a, li, re);
        hybQuickSort(a, li, i-1);
        hybQuickSort(a, i+1, re);
    }

    private static<T extends Comparable<T>> int partition(T[] a, int li, int re){
        T v = a[re];
        int i = li-1;
        int j = re;

        while (true){
            do i++; while (a[i].compareTo(v) < 0);
            do j--; while (j >= li && a[j].compareTo(v) > 0);
            if (i >= j){
                break;
            }
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        a[re] = a[i];
        a[i] = v;
        return i;
    }

    //################# mit 3-Median ######################################

    public static <T extends Comparable<T>> void hybQuickSort3Median(T[] a){
        hybQuickSort3MedianA(a, 0, a.length -1);
    }

    public static <T extends Comparable<T>> void hybQuickSort3MedianA(T[] a, int li, int re){
        if(re - li < 100){
            insertionSort(a, li, re);
            return;
        }
        int i = partition3Median(a, li, re);
        hybQuickSort3MedianA(a, li, i-1);
        hybQuickSort3MedianA(a, i+1, re);
    }

    private static <T extends Comparable<T>> int partition3Median(T[] a, int li, int re) {
        int mi = (li + re)/2; //für Mitte
        T v;

        if(a[mi].compareTo(a[li]) < 0){
            v = a[mi];
            a[mi] = a[li];
            a[li] = v;
        }
        if(a[re].compareTo(a[li]) < 0){
            v = a[re];
            a[re] = a[li];
            a[li] = v;
        }
        if(a[re].compareTo(a[mi]) < 0){
            v = a[mi];
            a[mi] = a[re];
            a[re] = v;
        }

        v = a[mi];
        a[mi] = a[re];
        a[re] = v;

        v = a[re];


        int i = li-1;
        int j = re;

        while (true){
            do i++; while (a[i].compareTo(v) < 0);
            do j--; while (j >= li && a[j].compareTo(v) > 0);
            if (i >= j){
                break;
            }
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        a[re] = a[i];
        a[i] = v;
        return i;

    }



    public static <T extends Comparable<T>> void insertionSort(T[] a, int li, int re){
        for (int i = li; i <= re; i++) {
             T v = (T) a[i];
             int j = i -1;
             while (j >= 0 && a[j].compareTo(v) > 0) {
                 a[j+1] = a[j];
                 j--;
             }
             a[j+1] = v;
        }
    }

}
