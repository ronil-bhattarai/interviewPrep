package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {

    public static void main(String[] args) {
        // Given height[], find two lines that together with the x-axis form a container that holds the most water.
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(height));
        // Time: O(n) Space: O(1)

        // Given an integer array nums[], return all unique triplets [nums[i], nums[j], nums[k]] such that: such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(nums));
        // Time: O(n^2) Space: O(1)

        //Given a sorted array nums[], remove the duplicates in-place such that each element appears only once and return the new length.
        int[] nums1 = {0, 0, 1, 1, 1, 2, 2, 3, 4, 4};
        System.out.println(removeDuplicates(nums1));
        // Time: O(n) Space: O(1)

        // Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(height1));
        // Time: O(n) Space: O(1)

        // Given two strings s and t, check whether s is a subsequence of t.
        String s = "abc", t = "ahbgdc";
        System.out.println(isSubsequence(s, t));
        // Time: O() Space: O()
    }

    private static int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxArea = 0;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            int area = h * w;
            maxArea = Math.max(maxArea, area);

            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxArea;
    }

    private static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (nums[left] == nums[left + 1]) left++;
                    while (nums[right] == nums[right - 1]) right--;
                    left--;
                    right--;
                } else if (sum < 0) left++;
                else right--;
            }
        }
        return result;
    }

    private static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int len = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[len]) {
                len++;
                nums[len] = nums[j];
            }
        }
        return len + 1;
    }

    private static int trap(int[] height) {
        int left = 0, right = height.length - 1, leftMax = 0, rightMax = 0, water = 0;

        while (left <= right) {
            if (height[left] <= height[right]) {
                if (height[left] >= leftMax) leftMax = height[left];
                else water += leftMax - height[left];
                left++;
            } else {
                if (height[right] >= rightMax) rightMax = height[right];
                else water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }

    private static boolean isSubsequence(String s, String t) {
        int i = 0, j = 0;

        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) i++;
            j++;
        }
        return i == s.length();
    }

}
