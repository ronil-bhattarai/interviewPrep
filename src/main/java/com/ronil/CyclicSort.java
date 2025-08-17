package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CyclicSort {
    public static void main(String[] args) {
        // Given an array of length n containing distinct numbers in range [0, n], find the one missing number.
        int[] nums = {3, 0, 1};
        System.out.println(missingNumber(nums));
        // Time: O(n) Space: O(1)

        // Given an integer array of length n where 1 ≤ a[i] ≤ n, some elements appear twice and others appear once.
        int[] nums1 = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println(findDuplicates(nums1));
        // Time: O(n) Space: O(1)

        // Given an unsorted integer array, find the smallest missing positive integer
        int[] nums2 = {3, 4, -1, 1};
        System.out.println(firstMissingPositive(nums2));
        // Time: O(n) Space: O(1)

        // Given an array of size n containing numbers from 1 to n, where one number appears twice & one number is missing. Find both numbers
        int[] nums3 = {1, 2, 2, 4};
        int[] result = findErrorNums(nums3);
        System.out.println(Arrays.toString(result));
        // Time: O(n) Space: O(1)

        // Given an array nums of n+1 integers, where each integer is in the range 1 … n inclusive, return the duplicate number
        int[] nums4 = {1, 3, 4, 2, 2};
        System.out.println(findDuplicate(nums4));
        // Time: O() Space: O()
    }

    private static int missingNumber(int[] nums) {
        int i = 0, n = nums.length;

        while (i < n) {
            int correct = nums[i];
            if (correct < n && nums[i] != nums[correct]) swap(nums, i, correct);
            else i++;
        }

        for (i = 0; i < n; i++) if (nums[i] != i) return i;

        return n;
    }

    private static List<Integer> findDuplicates(int[] nums) {
        int i = 0, n = nums.length;

        while (i < n) {
            int correct = nums[i] - 1;
            if (nums[i] != nums[correct]) swap(nums, i, correct);
            else i++;
        }

        List<Integer> results = new ArrayList<>();

        for (i = 0; i < n; i++) if (nums[i] != i + 1) results.add(nums[i]);

        return results;
    }

    private static int firstMissingPositive(int[] nums) {
        int i = 0, n = nums.length;

        while (i < n) {
            int correct = nums[i] - 1;
            if (nums[i] > 0 && nums[i] <= n && nums[i] != nums[correct]) swap(nums, i, correct);
            else i++;
        }

        for (i = 0; i < n; i++) if (nums[i] != i + 1) return i + 1;

        return n + 1;
    }

    private static int[] findErrorNums(int[] nums) {
        int i = 0, n = nums.length;

        while (i < n) {
            int correct = nums[i] - 1;
            if (nums[i] != nums[correct]) swap(nums, i, correct);
            else i++;
        }

        for (i = 0; i < n; i++) if (nums[i] != i + 1) return new int[]{nums[i], i + 1};

        return new int[]{-1, -1};
    }

    private static int findDuplicate(int[] nums) {
        int i = 0, n = nums.length;

        while (i < n) {
            int correct = nums[i] - 1;
            if (nums[i] != nums[correct]) swap(nums, i, correct);
            else i++;
        }

        for (i = 0; i < n; i++) if (nums[i] != i + 1) return nums[i];

        return -1;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
