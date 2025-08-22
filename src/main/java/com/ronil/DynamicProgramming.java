package com.ronil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming {
    public static void main(String[] args) {
        // Given an integer n, compute the nth Fibonacci number
        int n = 5;
        System.out.println(fibonacciTopDown(n));
        // Time: O(n) Space: O(n)
        System.out.println(fibonacciBottomUp(n));
        // Time: O(n) Space: O(n)
        System.out.println(fibonacciOptimized(n));
        // Time: O(n) Space: O(1)

        // You are climbing a staircase. Each time you can climb either 1 step or 2 steps.
        // Given n steps, how many distinct ways can you climb to the top?
        int steps = 5;
        System.out.println(climbStairsBottomUp(steps));
        // Time: O(n) Space: O(n)
        System.out.println(climbStairsOptimized(steps));
        // Time: O(n) Space: O(1)

        // Given an integer array nums, return the length of the longest increasing subsequence (LIS)
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(lengthOfLIS(nums));
        // Time: O(n^2) Space: O(n)

        // Given two strings word1 and word2, return the minimum number of operations required to convert word1 into word2.
        // Allowed operations: Insert, Delete, Replace a character
        String word1 = "horse";
        String word2 = "ros";
        System.out.println(minDistance(word1, word2));
        // Time: O(m * n) Space: O(m * n)

        // Given weights and values of N items, and capacity W, find the max value you can carry.
        int[] values = {6, 10, 12};
        int[] weights = {1, 2, 3};
        int W = 5;
        System.out.println(knapsack(weights, values, W));
        // Time: O(n * w) Space: O(n * w)

        // Given a string of digits, count how many ways to decode it where '1' -> 'A', '2' -> 'B', …, '26' -> 'Z'
        String s = "226";
        System.out.println(numDecodings(s));
        // Time: O(n) Space: O(n)
    }

    private static int fibonacciTopDown(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return fibonacciHelper(n, memo);
    }

    private static int fibonacciHelper(int n, Map<Integer, Integer> memo) {
        if (n <= 1)
            return n;

        if (memo.containsKey(n))
            return memo.get(n);

        int result = fibonacciHelper(n - 1, memo) + fibonacciHelper(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    private static int fibonacciBottomUp(int n) {
        if (n <= 1)
            return n;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    private static int fibonacciOptimized(int n) {
        if (n <= 1)
            return n;

        int prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    private static int climbStairsBottomUp(int n) {
        if (n <= 2)
            return n;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    private static int climbStairsOptimized(int n) {
        if (n <= 2)
            return n;

        int oneStepBefore = 2;
        int twoStepBefore = 1;
        int allWays = 0;

        for (int i = 3; i <= n; i++) {
            allWays = oneStepBefore + twoStepBefore;
            twoStepBefore = oneStepBefore;
            oneStepBefore = allWays;
        }
        return allWays;
    }

    private static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return n;

        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++)
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
        }

        int maxLIS = 1;
        for (int val : dp)
            maxLIS = Math.max(maxLIS, val);

        return maxLIS;
    }

    private static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();

        int dp[][] = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1], // replace
                            Math.min(dp[i - 1][j], // delete
                                    dp[i][j - 1]) // insert
                    );
            }
        }
        return dp[m][n];
    }

    private static int knapsack(int[] weights, int[] values, int W) {
        int n = weights.length;
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                if (weights[i - 1] <= j)
                    dp[i][j] = Math.max(
                            dp[i - 1][j], // don't take
                            values[i - 1] + dp[i - 1][j - weights[i - 1]] // take
                    );
                else
                    dp[i][j] = dp[i - 1][j]; // too heavy
            }
        }
        return dp[n][W];
    }

    private static int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0')
            return 0;

        int n = s.length();
        int[] dp = new int[n + 1];

        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            if (oneDigit >= 1 && oneDigit <= 9)
                dp[i] += dp[i - 1];

            int twoDigit = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26)
                dp[i] += dp[i - 2];
        }
        return dp[n];
    }
}
