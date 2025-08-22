package com.ronil;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindow {
    public static void main(String[] args) {

        // Given an array of integers and a number k, find the maximum sum of a sub-array of size k.
        int[] arr = {1, 3, 2, 6, -1, 4, 1, 8, 2};
        int k = 3;
        System.out.println(maxSum(arr, k));
        // Time: O(n) Space: O(1)

        // Find the length of the longest substring without repeating characters.
        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s));
        // Time: O(n) Space: O(k) where k in number of unique chars

        // Given strings s1 and t, return the minimum window in s1 which contains all the characters of t.
        String s1 = "ADOBECODEBANC", t = "ABC";
        System.out.println(minWindow(s1, t));
        // Time: O(n+m) where n is length of s1 and m is length of t Space: O(m) frequency of t

        // Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1.
        String s2 = "ab", s3 = "eidbaooo";
        System.out.println(checkInclusion(s2, s3));
        // Time: O(n + m) where n is the length of s1 and m is length of s2 Space: O(k) where k is unique char is s1 and s2

        // Given a binary array nums and an integer k,
        // Return the maximum number of consecutive 1s in the array if you can flip at most k 0s.
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k1 = 2;
        System.out.println(longestOnes(nums, k1));
        // Time: O(n) Space(1)
    }

    private static int maxSum(int[] arr, int k) {
        int windowSum = 0, maxSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
            maxSum = windowSum;
        }

        for (int i = k; i < arr.length; i++) {
            windowSum += arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    private static int lengthOfLongestSubstring(String s) {
        int maxLength = 0, left = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                left = map.get(c) + 1;
                map.put(c, right);
                continue;
            }
            map.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    private static String minWindow(String s, String t) {
        if (s.length() < t.length())
            return "";

        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        int left = 0, minLen = Integer.MAX_VALUE, match = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) >= 0)
                    match++;
            }

            while (match == t.length()) {
                if (right - left + 1 < minLen)
                    minLen = right - left + 1;
                char lChar = s.charAt(left++);
                if (map.containsKey(lChar)) {
                    if (map.get(lChar) == 0)
                        match--;
                    map.put(lChar, map.get(lChar) + 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(left - 1, left - 1 + minLen);
    }

    private static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s1.toCharArray())
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);

        int left = 0, match = 0;
        Map<Character, Integer> windowMap = new HashMap<>();

        for (int right = 0; right < s2.length(); right++) {
            char c = s2.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
            if (freqMap.containsKey(c) && windowMap.get(c) <= freqMap.get(c))
                match++;

            if (right - left + 1 > s1.length()) {
                char lChar = s2.charAt(left);
                if (freqMap.containsKey(lChar) && windowMap.get(lChar) <= freqMap.get(lChar))
                    match--;

                windowMap.put(lChar, windowMap.get(lChar) - 1);

                if (windowMap.get(lChar) == 0)
                    windowMap.remove(lChar);
                left++;
            }
            if (match == s1.length())
                return true;
        }
        return false;
    }

    private static int longestOnes(int[] numbers, int k) {
        int left = 0, maxLen = 0, zero = 0;

        for (int right = 0; right < numbers.length; right++) {
            if (numbers[right] == 0)
                zero++;

            while (zero > k) {
                if (numbers[left] == 0)
                    zero--;
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}