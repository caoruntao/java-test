package com.caort.algorithm.sort;

import java.util.Arrays;

/**
 * @author Caort.
 * @date 2021/11/18 上午8:56
 */
public class Sort {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 3, 2, 7, 4, 2, 6, 9, 0};
//        bubbleSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

//        insertionSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

//        selectSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();


//        mergeSort1(arr, 0, arr.length - 1);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

//        partitionSort(arr, 0, arr.length - 1);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

        heapSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();
    }

    public static void bubbleSort(int[] arr) {
        boolean flag;
        for (int i = 0; i < arr.length; i++) {
            flag = false;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static int[] mergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return new int[]{arr[end]};
        }
        int middle = (end - start >> 1) + start;
        int[] leftArr = mergeSort(arr, start, middle);
        int[] rightArr = mergeSort(arr, middle + 1, end);
        return merge(leftArr, rightArr);
    }

    public static int[] merge(int[] leftArr, int[] rightArr) {
        int[] tempArr = new int[leftArr.length + rightArr.length];
        int leftIndex = 0, rightIndex = 0, arrIndex = 0;
        while (leftIndex < leftArr.length && rightIndex < rightArr.length) {
            tempArr[arrIndex++] = leftArr[leftIndex] <= rightArr[rightIndex]
                    ? leftArr[leftIndex++] : rightArr[rightIndex++];
        }

        if (leftIndex < leftArr.length) {
            for (int i = arrIndex; i < tempArr.length; i++) {
                tempArr[arrIndex++] = leftArr[leftIndex++];
            }
        }
        if (rightIndex < rightArr.length) {
            for (int i = arrIndex; i < tempArr.length; i++) {
                tempArr[arrIndex++] = rightArr[rightIndex++];
            }
        }
        return tempArr;
    }


    public static void mergeSort1(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int middle = (end - start >> 1) + start;
        mergeSort1(arr, start, middle);
        mergeSort1(arr, middle + 1, end);
        merge1(arr, start, middle, end);
    }

    public static void merge1(int[] oriArr, int left, int middle, int right) {
        int[] tempArr = new int[right - left + 1];
        int leftIndex = left, rightIndex = middle + 1, tempArrIndex = 0;
        while (leftIndex <= middle && rightIndex <= right) {
            tempArr[tempArrIndex++] = oriArr[leftIndex] <= oriArr[rightIndex]
                    ? oriArr[leftIndex++] : oriArr[rightIndex++];
        }

        if (leftIndex <= middle) {
            for (int i = tempArrIndex; i < tempArr.length; i++) {
                tempArr[tempArrIndex++] = oriArr[leftIndex++];
            }
        }
        if (rightIndex <= right) {
            for (int i = tempArrIndex; i < tempArr.length; i++) {
                tempArr[tempArrIndex++] = oriArr[rightIndex++];
            }
        }

        for (int i = 0; i < tempArr.length; i++) {
            oriArr[left + i] = tempArr[i];
        }
    }

    public static void partitionSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int partitionPoint = partition(arr, start, end);
        partitionSort(arr, start, partitionPoint - 1);
        partitionSort(arr, partitionPoint + 1, end);
    }

    public static int partition(int[] arr, int left, int right) {
        int swapPoint = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < arr[right]) {
                swap(arr, i, swapPoint++);
            }
        }
        swap(arr, right, swapPoint);
        return swapPoint;
    }

    public static void swap(int[] arr, int source, int target) {
        if (source == target) {
            return;
        }
        int temp = arr[source];
        arr[source] = arr[target];
        arr[target] = temp;
    }

    public static void heapSort(int[] arr) {
        int parentNodeCnt = arr.length >> 1;
        for (int i = parentNodeCnt - 1; i >= 0; i--) {
            swapNode(arr, i, arr.length);
        }

        int length = arr.length - 1;
        while (length > 0) {
            swap(arr, 0, length);
            swapNode(arr, 0, length--);
        }
    }

    public static void swapNode(int[] arr, int idx, int length) {
        int leftChildIdx = (idx << 1) + 1;
        if (leftChildIdx >= length) {
            return;
        }
        int rightChildIdx = (idx << 1) + 2;

        int maxChildIdx = rightChildIdx >= length ? leftChildIdx
                : arr[leftChildIdx] > arr[rightChildIdx] ? leftChildIdx : rightChildIdx;
        if (arr[maxChildIdx] > arr[idx]) {
            swap(arr, maxChildIdx, idx);
            int parentNodeCount = arr.length >> 1;
            if (maxChildIdx < parentNodeCount) {
                swapNode(arr, maxChildIdx, length);
            }
        }
    }


}
