package com.caort.algorithm.sort;

/**
 * 删除倒数第n个节点
 *
 * @author Reed
 * @date 2021/11/16 下午3:22
 */
public class DeleteReciprocalNode {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node head = NodeUtil.generateLinkedList(arr);
        NodeUtil.traverse(head);

        deleteReciprocalNode(head, 3);

        NodeUtil.traverse(head);
    }

    /**
     * 利用双指针先找到倒数第n+1个节点，然后进行删除操作
     *
     * @param head
     * @param n
     */
    private static void deleteReciprocalNode(Node head, int n) {
        Node latter = head;
        Node former = head;
        while (n-- > 0) {
            former = former.getNext();
        }
        while (former.getNext() != null) {
            former = former.getNext();
            latter = latter.getNext();
        }
        Node next = latter.getNext().getNext();
        latter.setNext(next);
    }
}