package com.caort.algorithm.sort;

/**
 * @author Caort.
 * @date 2021/11/16 下午1:58
 */
public abstract class NodeUtil {
    public static Node generateLinkedList(int[] arr){
        Node next = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            Node cur = new Node(arr[i], next);
            next = cur;
        }
        return next;
    }

    public static void traverse(Node head) {
        while (head != null) {
            System.out.print(head.getData() + ",");
            head = head.getNext();
        }
        System.out.println();
    }
}
