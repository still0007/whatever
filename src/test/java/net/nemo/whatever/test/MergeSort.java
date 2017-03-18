package net.nemo.whatever.test;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by tonyshi on 2017/3/16.
 */
public class MergeSort {

    static int[] merge(int[] ary, int lo, int mid, int hi){
        int[] aux = new int[ary.length];

        for (int k = lo; k <= hi; k++)
        {
            aux[k] = ary[k];
        }

        int i = 0, j = mid+1;
        for(int k = lo; k <= hi; k++){
            if (i > mid) {
                ary[k] = aux[j++];
            }
            else if( j > hi){
                ary[k] = aux[i++];
            }
            else if(ary[i] < ary[j]){
                ary[k] = aux[i++];
            }
            else if(ary[i] >= ary[j]){
                ary[k] = aux[j++];
            }
        }

        return aux;
    }

    public static void sort(int[] ary, int lo, int hi){

        if(lo>=hi) return;

        int mid =  lo + (hi - lo) / 2;;

        sort(ary, 0, mid);
        sort(ary, mid + 1, hi);
        merge(ary, 0, mid, hi);
    }

    public static void main(String[] args) {
        int[] ary = {2, 4, 6, 3, 1};

        //sort(ary, 0, ary.length-1);

        //System.out.println(ArrayUtils.toString(ary));
        QuickSort1.sort(ary, 0 ,4);
        System.out.println(ArrayUtils.toString(ary));
    }
}

class QuickSort1{

    static int partition(int[] ary, int lo, int hi){

        int v = lo, i = 0, j = hi + 1;

        while(true){
            while(i<hi){
                if(ary[++i] > ary[v])
                    break;
            }

            while(j>lo){
                if(ary[--j] < ary[v])
                    break;
            }

            if(i>=j) break;

            swap(ary, i, j);
        }

        swap(ary, lo, j);

        return j;

    }

    static void swap(int[] ary, int i, int j){
        int tmp = ary[i];
        ary[i] = ary[j];
        ary[j] = tmp;
    }

    static void sort(int[] ary, int lo, int hi){
        if(lo >= hi) return;

        int v = partition(ary, lo, hi);
        sort(ary, lo, v-1);
        sort(ary, v+1, hi);
    }
}
