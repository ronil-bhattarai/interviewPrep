package com.ronil;

import java.util.*;

public class TwoHeapsPriorityQueue {

    static PriorityQueue<Integer> maxHeapMedian = new PriorityQueue<>(Collections.reverseOrder());
    static PriorityQueue<Integer> minHeapMedian = new PriorityQueue<>();

    public static void main(String[] args) {
        // Find the Kth largest element in an unsorted array.
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        System.out.println(findKthLargest(nums, k));
        // Time: O(n log k) Space: O(k)

        // Given an array of 2D points (as int[][] points),
        // And you need to return the K closest points to the origin (0, 0) using Euclidean distance.
        int[][] points = {{1, 3}, {-2, 2}, {5, 8}, {0, 1}};
        int k1 = 2;
        for (int[] point : kClosest(points, k1)) System.out.println(Arrays.toString(point));
        // Time: O(n log k) Space: O(k)

        // Continuously find the median in a stream of integers.
        int[] stream = {1, 2, 3, 4, 5};
        for (int num : stream) {
            addNum(num);
            System.out.println(findMedian());
        }
        // Time: O(log n) Space: O(n)

        // Given tasks and cooling period n, find the least units of time required.
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n = 2;
        System.out.println(leastInterval(tasks, n));
        // Time: O(n) Space: O(1)

        // Given an integer array nums and an integer k, return the k most frequent elements.
        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int k2 = 2;
        System.out.println(Arrays.toString(topKFrequent(nums1, k2)));
        // Time: O(n log k) Space: O(n)
    }

    private static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        return minHeap.peek();
    }

    private static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(distance(b), distance(a))
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k)
                maxHeap.poll();
        }

        int result[][] = new int[k][2];
        for (int i = 0; i < k; i++)
            result[i] = maxHeap.poll();

        return result;

    }

    private static int distance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    private static void addNum(int num) {
        maxHeapMedian.offer(num);
        minHeapMedian.offer(maxHeapMedian.poll());

        if (minHeapMedian.size() > maxHeapMedian.size())
            maxHeapMedian.offer(minHeapMedian.poll());
    }

    private static double findMedian() {
        if (maxHeapMedian.size() == minHeapMedian.size())
            return (maxHeapMedian.peek() + minHeapMedian.peek()) / 2.0;

        return maxHeapMedian.peek();
    }

    private static int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char task : tasks)
            freq[task - 'A']++;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int f : freq)
            if (f > 0)
                maxHeap.offer(f);

        int time = 0;

        while (!maxHeap.isEmpty()) {
            int cycle = n + 1;
            List<Integer> temp = new ArrayList<>();

            for (int i = 0; i < cycle && !maxHeap.isEmpty(); i++) {
                int curr = maxHeap.poll();
                if (curr > 1)
                    temp.add(curr - 1);
                time++;
            }

            for (int remaining : temp)
                maxHeap.offer(remaining);

            if (!maxHeap.isEmpty())
                time += (cycle - temp.size());
        }
        return time;
    }

    private static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums)
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        int[] result = new int[k];
        int i = 0;

        while (!minHeap.isEmpty())
            result[i++] = minHeap.poll().getKey();

        return result;
    }
}
