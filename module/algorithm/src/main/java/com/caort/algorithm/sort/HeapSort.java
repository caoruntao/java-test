package com.caort.algorithm.sort;

import java.util.Arrays;

/**
 * @author Reed
 * @date 2021/11/10 下午2:03
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] array = new int[]{10, 4, 8, 1, 9, 3, 4, 6, 5, 2};
//        int[] array = new int[] {3, 5, 3, 0, 8, 6, 1, 5, 8, 6, 2, 4, 9, 4, 7, 0, 1, 8, 9, 7, 3, 1, 2, 5, 9, 7, 4, 0, 2, 6};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array) {
        int len = array.length;
        // 最后一个非叶子节点
        int lastParentNode = (len >> 1) - 1;
        for (int i = lastParentNode; i >= 0; i--) {
            adjustNode(i, len, array);
        }

        for (int i = len - 1; i > 0; i--) {
            // 将堆顶元素于末尾元素交换，形成有序区
            swap(0, i, array);
            // 将置换后的节点重新排序
            adjustNode(0, i, array);
        }
    }

    /**
     * @param index  当前要排序的父节点下标
     * @param length 待排序区的长度
     * @param array
     */
    public static void adjustNode(int index, int length, int[] array) {
        int li = (index << 1) + 1;
        if (li > length - 1) {
            return;
        }
        int ri = length > li + 1 ? li + 1 : -1;
        // 因为是从非叶子节点进行调整的，肯定会有左子节点，但不一定会有右子节点
        int maxChildIndex = ri < 0 ? li
                : array[li] > array[ri] ? li : ri;
        // 大堆顶。如果父节点小于子节点，则交换
        if (array[index] < array[maxChildIndex]) {
            swap(index, maxChildIndex, array);
            int lastParentNode = (length >> 1) - 1;
            // 将置换后的非叶子节点重新排序
            if (maxChildIndex < lastParentNode) {
                adjustNode(maxChildIndex, length, array);
            }

        }
    }

    public static void swap(int sourceIndex, int targetIndex, int[] array) {
        int temp = array[sourceIndex];
        array[sourceIndex] = array[targetIndex];
        array[targetIndex] = temp;
    }
}
