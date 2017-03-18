package net.nemo.whatever.test;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by tonyshi on 2017/3/16.
 */
public class QuickSort {

    static int partition(int[] ary, int low, int hi){

        int v = low, i = low, j = hi + 1;

        while(true){

            while(i<hi){
                if(ary[++i] > ary[v]) break;
            }

            while(j>low){
                if(ary[--j] < ary[v]) break;
            }

            if(i>=j) break;

            swap(ary, i, j);
        }

        swap(ary, v, j);

        return j;
    }

    static void swap(int[] ary, int i, int j){
        System.out.println("SWAP "+ ary[i] + " AND " + ary[j]);
        System.out.println("BEFORE: " + ArrayUtils.toString(ary));
        int tmp = ary[i];
        ary[i] = ary[j];
        ary[j] = tmp;
        System.out.println("AFTER: " + ArrayUtils.toString(ary));
    }

    static void sort(int ary[], int low, int hi){
        if(low >= hi) return;

        int v = partition(ary, low, hi);
        System.out.println(ary[v]);
        sort(ary, low, v - 1);
        sort(ary, v + 1, hi);
    }


    public static void main(String[] args) {
        int[] ary = {8, 3, 10, 4, 1, 7, 6, 5, 9, 2};
        sort(ary, 0, ary.length-1);
        //System.out.println(ArrayUtils.toString(ary));
    }

}
