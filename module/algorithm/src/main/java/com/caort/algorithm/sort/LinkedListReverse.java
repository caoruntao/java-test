package com.caort.algorithm.sort;

/**
 * 单链表反转
 *
 * @author Caort.
 * @date 2021/11/16 下午1:19
 */
public class LinkedListReverse {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        Node head = NodeUtil.generateLinkedList(arr);
        NodeUtil.traverse(head);

        head = reverseByRecursion(head, null);
        System.out.println();
        NodeUtil.traverse(head);

        head = reverseByTraverse(head);
        System.out.println();
        NodeUtil.traverse(head);
    }

    private static Node reverseByTraverse(Node cur) {
        Node pre = null;
        while (cur != null) {
            Node next = cur.getNext();
            cur.setNext(pre);

            pre = cur;
            cur = next;
        }
        return pre;
    }

    private static Node reverseByRecursion(Node cur, Node pre) {
        if (cur == null) {
            return pre;
        }
        Node next = cur.getNext();
        cur.setNext(pre);
        return reverseByRecursion(next, cur);
    }
}
