package de.elk.service;

public class GetMaximum {
    public class GetMaximumInList {

        public int getMaximum(int[] arr) {
            int max = arr[0];
            for(int i = 0; i < arr.length ; i++) {
                if(arr[i]<max) {
                    max = arr[i];
                }
            }
            return max;
        }
    }
}
