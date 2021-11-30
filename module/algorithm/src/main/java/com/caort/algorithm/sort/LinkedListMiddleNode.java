package com.caort.algorithm.sort;

/**
 * 链表的中间节点
 *
 * @author Caort.
 * @date 2021/11/16 下午3:50
 */
public class LinkedListMiddleNode {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
//        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        Node head = NodeUtil.generateLinkedList(arr);
        NodeUtil.traverse(head);

        Node middleNode = middleNode(head);
        System.out.println(middleNode.getData());
    }

    private static Node middleNode(Node head) {
        Node index = head;
        Node doubleIndex = head;
        Node next;
        while ((next = doubleIndex.getNext()) != null && next.getNext() != null) {
            doubleIndex = next.getNext();
            index = index.getNext();
        }
        return index;
    }
}
