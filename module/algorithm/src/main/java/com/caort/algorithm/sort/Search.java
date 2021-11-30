package com.caort.algorithm.sort;

/**
 * @author Caort.
 * @date 2021/11/25 上午8:52
 */
public class Search {
    public static void main(String[] args) {
        int[] arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int i = binarySearch(arr, 7);
        System.out.println(i);
    }

    /**
     * 二分查找
     * 不包含重复数据
     *
     * @param arr
     * @param value
     * @return
     */
    public static int binarySearch(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = low + (high - low >> 1);
            if (arr[mid] > value) {
                high = mid - 1;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
