package com.ronil;

import java.util.Arrays;

public class InPlaceReversal {

    public static void main(String[] args) {
        // Given the head of a singly linked list, reverse it in-place
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        printList(reverseList(head));
        // Time: O(n) Space: O(1)

        // Reverse a portion of a linked list from position m to n
        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(3);
        head2.next.next.next = new ListNode(4);
        head2.next.next.next.next = new ListNode(5);
        int m = 2, n = 4;
        printList(reverseBetween(head2, m, n));
        // Time: O(n) Space: O(1)

        // Given an integer array nums and an integer k, rotate the array to the right by k steps
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        rotate(nums, k);
        System.out.println(Arrays.toString(nums));
        // Time: O(n) Space: O(1)

        // Given a string s, reverse the order of words in the string.
        // A word is defined as a sequence of non-space characters
        String input = "  the  sky   is  blue  ";
        System.out.println(reverseWords(input));
        // Time: O(n) Space: O(n)

        // Given the head of a linked list, reverse the nodes of the list k at a time and return the modified list.
        // If the number of nodes is not a multiple of k, leave the last group as is.
        ListNode head3 = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        int l = 3;
        printList(reverseKGroup(head, l));
        // Time: O(n) Space: O(1)
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null, current = head;

        while (current != null) {
            ListNode nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }
        return prev;
    }

    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || n == m)
            return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        for (int i = 1; i < m; i++)
            prev = prev.next;

        ListNode current = prev.next;

        for (int i = 0; i < n - m; i++) {
            ListNode temp = current.next;
            current.next = temp.next;
            temp.next = prev.next;
            prev.next = temp;
        }

        return dummy.next;
    }

    private static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        reverseArray(nums, 0, n - 1);
        reverseArray(nums, 0, k - 1);
        reverseArray(nums, k, n - 1);
    }

    private static String reverseWords(String s) {
        char[] chars = trimSpaces(s).toCharArray();

        reverseCharArray(chars, 0, chars.length - 1);

        int start = 0;
        for (int end = 0; end <= chars.length; end++) {
            if (end == chars.length || chars[end] == ' ') {
                reverseCharArray(chars, start, end - 1);
                start = end + 1;
            }
        }
        return new String(chars);
    }

    private static ListNode reverseKGroup(ListNode head, int k) {
        ListNode node = head;
        for (int i = 0; i < k; i++) {
            if (node == null)
                return head;
            node = node.next;
        }

        ListNode prev = null;
        ListNode curr = head;
        for (int i = 0; i < k; i++) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }

        head.next = reverseKGroup(curr, k);

        return prev;
    }

    private static void reverseCharArray(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left++] = chars[right];
            chars[right--] = temp;
        }
    }

    private static String trimSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        int left = 0, right = s.length() - 1;

        while (left <= right && s.charAt(left) == ' ')
            left++;
        while (left <= right && s.charAt(right) == ' ')
            right--;

        while (left <= right) {
            char c = s.charAt(left);
            if (c != ' ')
                sb.append(c);
            else if (!sb.isEmpty() && sb.charAt(sb.length() - 1) != ' ')
                sb.append(' ');
            left++;
        }
        return sb.toString();
    }

    private static void reverseArray(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left++] = nums[right];
            nums[right--] = temp;
        }
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null)
                System.out.print("->");
            head = head.next;
        }
        System.out.println();
    }
}
