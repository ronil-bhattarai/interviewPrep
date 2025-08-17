package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {

    public static void main(String[] args) {

        // Given a collection of intervals, merge all overlapping intervals
        int[][] intervals = {{1, 3}, {8, 10}, {2, 6}, {15, 18}};
        int[][] mergeResult = merge(intervals);
        for (int[] interval : mergeResult) {
            System.out.println(Arrays.toString(interval));
        }
        // Time: O(n log n) Space: O(n)

        // Given a list of non-overlapping, sorted intervals, insert a new interval and merge if necessary
        int[][] intervals1 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = {4, 8};
        int[][] insertResult = insert(intervals1, newInterval);
        for (int[] interval : insertResult) {
            System.out.println(Arrays.toString(interval));
        }
        // Time: O(n) Space: O(n)

        // Given a list of meeting intervals, determine if a person could attend all meetings (i.e., no two meetings overlap).
        int[][] intervals2 = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(canAttendMeetings(intervals2));
        // Time: O(n log n) Space: O(1)

        // Given a list of intervals, remove as few intervals as possible so that the remaining intervals do not overlap.
        int[][] intervals3 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(removeOverlapIntervals(intervals3));
        // Time: O(n log n) Space: O(1)

        // Given a schedule of employees (each with a list of busy intervals), find the common free times
        List<List<Interval>> schedule = new ArrayList<>();
        schedule.add(Arrays.asList(new Interval(1, 2), new Interval(5, 6)));
        schedule.add(List.of(new Interval(1, 3)));
        schedule.add(List.of(new Interval(4, 10)));

        List<Interval> freeTimes = employeeFreeTime(schedule);
        for (Interval interval : freeTimes) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
        // Time: O(n log n) Space: O(n)
    }

    private static int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) return intervals;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();

        for (int[] interval : intervals) {
            if (merged.isEmpty() || merged.getLast()[1] < interval[0]) merged.add(interval);
            else merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
        }
        return merged.toArray(new int[merged.size()][]);
    }

    private static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        int i = 0, n = intervals.length;

        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);

        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    private static boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1) return true;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }
        return true;
    }

    private static int removeOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        int count = 0, end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) count++;
            else end = intervals[i][1];
        }
        return count;
    }

    private static List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> allIntervals = new ArrayList<>();

        for (List<Interval> emp : schedule) allIntervals.addAll(emp);

        allIntervals.sort(Comparator.comparingInt(a -> a.start));

        List<Interval> result = new ArrayList<>();
        Interval prev = allIntervals.getFirst();

        for (int i = 1; i < allIntervals.size(); i++) {
            Interval curr = allIntervals.get(i);

            if (curr.start > prev.end) {
                result.add(new Interval(prev.end, curr.start));
                prev = curr;
            } else prev.end = Math.max(prev.end, curr.end);
        }
        return result;
    }
}
