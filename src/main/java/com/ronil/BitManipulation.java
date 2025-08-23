package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitManipulation {
    public static void main(String[] args) {
        // Given a non-empty array of integers, every element appears twice except for one. Find that single one.
        int[] nums = {2, 3, 5, 3, 2};
        System.out.println(singleNumber(nums));
        // Time: O(n) Space: O(1)

        // Given an integer array where every element appears three times except for one, which appears exactly once, find the single one
        int[] nums1 = {2, 2, 3, 2};
        System.out.println(singleNumber1(nums1));
        // Time: O(n) Space: O(1)

        // Given a number n, return an array ans where ans[i] is the number of 1’s in the binary representation of i,
        // for all i in the range [0, n].
        int n = 5;
        System.out.println(Arrays.toString(countBits(n)));
        // Time: O(n) Space: O(n)

        // Given an integer n, return true if it is a power of two. Otherwise, return false.
        int[] testCases = {1, 2, 3, 4, 5, 8, 16, 18};
        for (int x : testCases)
            System.out.println(isPowerOfTwo(x));
        // Time: O(1) Space: O(1)

        // Reverse the bits of a 32-bit unsigned integer
        int input = 43261596;
        int output = reverseBits(input);
        System.out.println(output + " " + Integer.toBinaryString(output));
        // Time: O(1) Space: O(1)

        // Given an array of distinct integers, return all possible subsets (the power set).
        int[] nums2 = {1, 2, 3};
        for (List<Integer> subset : subsets(nums2))
            System.out.println(subset);
        // Time: O(n * 2^n) Space: O(n * 2^n)
    }

    private static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums)
            result ^= num;
        return result;
    }

    private static int singleNumber1(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }
        return ones;
    }

    private static int[] countBits(int n) {
        int[] result = new int[n + 1];
        for (int i = 1; i <= n; i++)
            result[i] = result[i >> 1] + (i & 1);
        return result;
    }

    private static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    private static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>= 1;
        }
        return result;
    }

    private static List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        int total = 1 << n;
        List<List<Integer>> result = new ArrayList<>();

        for (int mask = 0; mask < total; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0)
                    subset.add(nums[i]);
            }
            result.add(subset);
        }
        return result;
    }
}
