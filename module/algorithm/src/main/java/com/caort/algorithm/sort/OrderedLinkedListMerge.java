package com.caort.algorithm.sort;

/**
 * 两个有序单链表合并
 *
 * @author Caort.
 * @date 2021/11/16 下午1:38
 */
public class OrderedLinkedListMerge {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 5, 6, 8};
        int[] arr2 = new int[]{1, 3, 4, 7, 9, 10};
        Node head1 = NodeUtil.generateLinkedList(arr1);
        Node head12 = NodeUtil.generateLinkedList(arr1);
        NodeUtil.traverse(head1);
        System.out.println();

        Node head2 = NodeUtil.generateLinkedList(arr2);
        Node head22 = NodeUtil.generateLinkedList(arr2);
        NodeUtil.traverse(head2);
        System.out.println();

        Node merge1 = mergeByIteration(head1, head2);
        NodeUtil.traverse(merge1);
        System.out.println();

        Node merge2 = mergeByRecursion(head12, head22);
        NodeUtil.traverse(merge2);

    }

    private static Node mergeByIteration(Node head1, Node head2) {
        Node newPreHead = new Node(-1, null);
        Node newPre = newPreHead;
        while (head1 != null && head2 != null) {
            if (head1.getData() <= head2.getData()) {
                newPre.setNext(head1);
                head1 = head1.getNext();
            } else {
                newPre.setNext(head2);
                head2 = head2.getNext();
            }
            newPre = newPre.getNext();
        }
        newPre.setNext(head1 == null ? head2 : head1);
        return newPreHead.getNext();
    }

    private static Node mergeByRecursion(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        if (head1.getData() <= head2.getData()) {
            head1.setNext(mergeByRecursion(head1.getNext(), head2));
            return head1;
        } else {
            head2.setNext(mergeByRecursion(head1, head2.getNext()));
            return head2;
        }
    }
}
