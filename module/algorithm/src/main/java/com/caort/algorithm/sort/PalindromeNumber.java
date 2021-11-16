package com.caort.algorithm.sort;

/**
 * @author Reed
 * @date 2021/11/16 下午3:41
 */
public class PalindromeNumber {
    public static void main(String[] args) {
//        int[] arr = new int[]{};
//        int[] arr = new int[]{1,2,3,0,3,2,1};
//        int[] arr = new int[]{1,2,3,0,0,3,2,1};
        int[] arr = new int[]{1, 2, 3, 0, 0, 3, 2, 3};
        System.out.println(isPalindrome(arr));
    }

    private static boolean isPalindrome(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            if (arr[i] != arr[arr.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }
}
