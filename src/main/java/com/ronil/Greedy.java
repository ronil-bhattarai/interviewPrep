package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Greedy {
    public static void main(String[] args) {
        // Given an array nums, each element represents your max jump length.
        // Return true if you can reach the last index.
        int[] nums1 = {2, 3, 1, 1, 4};
        int[] nums2 = {3, 2, 1, 0, 4};
        System.out.println(canJump(nums1));
        System.out.println(canJump(nums2));
        // Time: O(n) Space: (n)

        // There are n gas stations in a circle. Each has gas[i], and it costs cost[i] to go to the next.
        // Find the starting index if possible.
        int[] gas1 = {1, 2, 3, 4, 5};
        int[] cost1 = {3, 4, 5, 1, 2};
        int[] gas2 = {2, 3, 4};
        int[] cost2 = {3, 4, 3};
        System.out.println(canCompleteCircuit(gas1, cost1));
        System.out.println(canCompleteCircuit(gas2, cost2));
        // Time: O(n) Space: (1)

        // Each balloon is represented as an interval. An arrow can burst all overlapping balloons.
        // Find the minimum arrows required.
        int[][] balloons1 = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        int[][] balloons2 = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        int[][] balloons3 = {{1, 10}, {2, 9}, {3, 8}, {4, 7}};
        System.out.println(findMinArrowShots(balloons1));
        System.out.println(findMinArrowShots(balloons2));
        System.out.println(findMinArrowShots(balloons3));
        // Time: O(n log n) Space: (1)

        // You are given a string s. Partition it into as many parts as possible so that each letter appears in only one part.
        // Return a list of integers representing the size of each partition.
        String s = "ababcbacadefegdehijhklij";
        System.out.println(partitionLabels(s));
        // Time: O(n) Space: (1)

        // Distribute candies such that: Each child gets at least 1 candy.
        // Children with a higher rating than a neighbor get more candies than that neighbor.
        // Return the minimum number of candies you must give.
        int[] ratings1 = {1, 0, 2};
        int[] ratings2 = {1, 2, 2};
        int[] ratings3 = {1, 3, 2, 2, 1};
        System.out.println(candy(ratings1));
        System.out.println(candy(ratings2));
        System.out.println(candy(ratings3));
        // Time: O(n) Space: (n)
    }

    private static boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest)
                return false;
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }

    private static int canCompleteCircuit(int[] gas, int[] costs) {
        int totalGas = 0, totalCost = 0, tank = 0, start = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += costs[i];
            tank += gas[i] - costs[i];

            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return totalGas < totalCost ? -1 : start;
    }

    private static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0)
            return 0;

        Arrays.sort(points, Comparator.comparingInt(a -> a[1]));

        int arrows = 1;
        int lastArrowPos = points[0][1];

        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > lastArrowPos) {
                arrows++;
                lastArrowPos = points[i][1];
            }
        }

        return arrows;
    }

    private static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];

        for (int i = 0; i < s.length(); i++)
            last[s.charAt(i) - 'a'] = i;

        List<Integer> result = new ArrayList<>();
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1;
            }
        }
        return result;
    }

    private static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        for (int i = 1; i < n; i++)
            if (ratings[i] > ratings[i - 1])
                candies[i] = candies[i - 1] + 1;

        for (int i = n - 2; i >= 0; i--)
            if (ratings[i] > ratings[i + 1])
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);

        int total = 0;
        for (int candy : candies)
            total += candy;

        return total;
    }
}
