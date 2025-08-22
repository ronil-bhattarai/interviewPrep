package com.ronil;

public class FastSlowPointer {

    public static void main(String[] args) {

        // Given the head of a linked list, determine if it contains a cycle
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;
        System.out.println(hasCycle(head));
        // Time: O(n) Space: (1)

        // Determine if a number eventually reaches 1 when replaced by the sum of squares of its digits.
        int n = 19;
        System.out.println(isHappy(n));
        // Time: O(log n) Space: O(1)

        // Given the head of a singly linked list, return the middle node.
        // If the list has an even number of nodes, return the second middle node.
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = null;
//        head1.next.next.next.next.next = new ListNode(6);
//        head1.next.next.next.next.next.next = null;
        System.out.println(middleNode(head1));
        // Time: O(n) Space: O(1)

        // Given the head of a linked list, if there is a cycle, return the node where the cycle begins
        System.out.println(detectCycle(head).val);
        // Time: O(n) Space: O(1)

        // Given the head of a singly linked list, determine if it is a palindrome
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(2);
        head2.next.next.next = new ListNode(1);
        head2.next.next.next.next = null;
        System.out.println(isPalindrome(head2));
        // Time: O(n) Space: O(1)
    }

    private static boolean hasCycle(ListNode head) {
        ListNode fast = head, slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow)
                return true;
        }
        return false;
    }

    private static boolean isHappy(int n) {
        int slow = n;
        int fast = getNext(n);

        while (fast != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }
        return fast == 1;
    }

    private static int getNext(int n) {
        int sum = 0;

        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    private static int middleNode(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.val;
    }

    private static ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                ListNode ptr1 = head;
                ListNode ptr2 = slow;

                while (ptr1 != ptr2) {
                    ptr1 = ptr1.next;
                    ptr2 = ptr2.next;
                }
                return ptr1;
            }
        }
        return null;
    }

    private static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode secondHalf = reverse(slow);

        ListNode fistHalf = head;

        while (secondHalf != null) {
            if (fistHalf.val != secondHalf.val)
                return false;
            fistHalf = fistHalf.next;
            secondHalf = secondHalf.next;
        }
        return true;
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
