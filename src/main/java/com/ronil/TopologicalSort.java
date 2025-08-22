package com.ronil;

import java.util.*;

public class TopologicalSort {
    public static void main(String[] args) {
        // Can you finish all courses given prerequisites? Return true if you can finish all, otherwise false.
        int numCourses = 4;
        int[][] prerequisites = {{1, 0}, {2, 1}, {3, 2}};
        System.out.println(canFinishCourse(numCourses, prerequisites));
        // Time: O(numCourses + prerequisites.length) Space: O(numCourses + prerequisites.length)

        // You’re given numCourses and a list of prerequisite pairs.
        // Return a valid ordering of courses to take all of them. If it’s not possible (cycle exists), return an empty array.
        int numCourses1 = 4;
        int[][] prerequisites1 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println(Arrays.toString(findCourseOrder(numCourses1, prerequisites1)));
        // Time: O(numCourses + prerequisites.length) Space: O(numCourses + prerequisites.length)

        // Given a sorted dictionary from an alien language, determine the order of the alphabet.
        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};
        System.out.println(alienOrder(words));
        // Time: O(number of words * average length of word) Space: O(number of unique char * number of constrains)

        // You are given: A list of integers: org → original sequence.
        // A list of sequences: seqs, each a subsequence of org.
        // Return true if org is the unique shortest common supersequence of seqs, else return false.
        int[] org = {1, 2, 3};
        List<List<Integer>> seqs1 = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(1, 3)
        );
        System.out.println(sequenceReconstruction(org, seqs1));
        List<List<Integer>> seqs2 = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(2, 3)
        );
        System.out.println(sequenceReconstruction(org, seqs2));
        // Time: O(Number of unique elements + total number of edges) Space: O(Number of unique elements + total number of edges)

        // You are given an undirected tree with n nodes labeled from 0 to n-1,
        // And an edge list. Return all roots that produce Minimum Height Trees (MHTs).
        int n = 6;
        int[][] edges = {
                {0, 1}, {0, 2}, {0, 3}, {3, 4}, {4, 5}
        };
        System.out.println(findMinHeightTrees(n, edges));
        // Time: O(n) Space: O(n)

        // You’re given: n tasks (numbered 0 to n - 1) & n array of prerequisites, where each pair [a, b] means:
        // Task a depends on task b (i.e., you must do b before a)
        int numTasks1 = 4;
        int[][] prerequisites2 = {{1, 0}, {2, 1}, {3, 2}};
        System.out.println(canFinishTasks(numTasks1, prerequisites2));
        int numTasks2 = 3;
        int[][] prerequisites3 = {{0, 1}, {1, 2}, {2, 0}};
        System.out.println(canFinishTasks(numTasks2, prerequisites3));
        // Time: O(N + E) Space: O(N + E)
    }

    private static boolean canFinishCourse(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++)
            graph.add(new ArrayList<>());

        for (int[] pair : prerequisites) {
            int course = pair[0], prereq = pair[1];
            graph.get(prereq).add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++)
            if (indegree[i] == 0)
                queue.offer(i);

        int completedCourses = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            completedCourses++;

            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0)
                    queue.offer(neighbor);
            }
        }
        return completedCourses == numCourses;
    }

    private static int[] findCourseOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++)
            graph.add(new ArrayList<>());

        for (int[] pair : prerequisites) {
            int course = pair[0], prereq = pair[1];
            graph.get(prereq).add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++)
            if (indegree[i] == 0)
                queue.offer(i);

        int[] result = new int[numCourses];
        int index = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result[index++] = current;

            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0)
                    queue.offer(neighbor);
            }
        }
        return result;
    }

    private static String alienOrder(String[] words) {
        Map<Character, List<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();

        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new ArrayList<>());
                indegree.putIfAbsent(c, 0);
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];

            if (first.length() > second.length() && first.startsWith(second))
                return "";

            for (int j = 0; j < Math.min(first.length(), second.length()); j++) {
                char from = first.charAt(j), to = second.charAt(j);
                if (from != to) {
                    graph.get(from).add(to);
                    indegree.put(to, indegree.get(to) + 1);
                    break;
                }
            }
        }

        Queue<Character> queue = new LinkedList<>();
        for (char c : indegree.keySet())
            if (indegree.get(c) == 0)
                queue.offer(c);

        StringBuilder order = new StringBuilder();
        while (!queue.isEmpty()) {
            char current = queue.poll();
            order.append(current);

            for (char neighbor : graph.get(current)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0)
                    queue.offer(neighbor);
            }
        }
        return order.length() == indegree.size() ? order.toString() : "";
    }

    private static boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();

        for (List<Integer> seq : seqs) {
            for (int num : seq) {
                graph.putIfAbsent(num, new HashSet<>());
                indegree.putIfAbsent(num, 0);
            }
        }

        for (List<Integer> seq : seqs) {
            for (int i = 1; i < seq.size(); i++) {
                int from = seq.get(i - 1), to = seq.get(i);
                if (graph.get(from).add(to))
                    indegree.put(to, indegree.get(to) + 1);
            }
        }

        if (indegree.size() != org.length)
            return false;
        for (int num : org)
            if (!indegree.containsKey(num))
                return false;

        Queue<Integer> queue = new LinkedList<>();
        for (int node : indegree.keySet())
            if (indegree.get(node) == 0)
                queue.offer(node);

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            if (queue.size() > 1)
                return false;

            int current = queue.poll();
            result.add(current);

            for (int neighbor : graph.get(current)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0)
                    queue.offer(neighbor);
            }
        }

        if (result.size() != org.length)
            return false;

        for (int i = 0; i < org.length; i++)
            if (result.get(i) != org[i])
                return false;

        return true;
    }

    private static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<>();
        if (n <= 0)
            return result;
        if (n == 1) {
            result.add(0);
            return result;
        }

        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new HashSet<>());

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (graph.get(i).size() == 1)
                leaves.offer(i);

        while (n > 2) {
            int size = leaves.size();
            n -= size;

            for (int i = 0; i < size; i++) {
                int leaf = leaves.poll();
                int neighbor = graph.get(leaf).iterator().next();

                graph.get(neighbor).remove(leaf);
                if (graph.get(neighbor).size() == 1)
                    leaves.offer(neighbor);
            }
        }
        result.addAll(leaves);
        return result;
    }

    private static boolean canFinishTasks(int numTasks, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numTasks];

        for (int i = 0; i < numTasks; i++)
            graph.add(new ArrayList<>());

        for (int[] taskPair : prerequisites) {
            int task = taskPair[0], prereq = taskPair[1];
            graph.get(prereq).add(task);
            indegree[task]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numTasks; i++)
            if (indegree[i] == 0)
                queue.offer(i);

        int completed = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            completed++;

            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0)
                    queue.offer(neighbor);
            }
        }
        return completed == numTasks;
    }
}
