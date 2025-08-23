package com.ronil;

import java.util.*;

public class UnionFind {
    public static void main(String[] args) {
        // Given: An integer n representing n nodes labeled from 0 to n-1 and,
        // An array edges where each edge[i] = [ai, bi] represents an undirected edge.
        // Return the number of connected components.
        int n = 7;
        int[][] edges = {{0, 1}, {6, 0}, {2, 4}, {2, 3}, {3, 4}};
        System.out.println(countComponents(n, edges));
        // Time: O(n + E) Space: O(n)

        // You are given a list of accounts, where each account is a list:
        // First element = account holder’s name and Rest of the elements = emails
        // Merge accounts that share at least one email and return the merged accounts in the format:
        // [ Name, email1, email2, ...] with emails sorted lexicographically.
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        accounts.add(Arrays.asList("John", "johnnybravo@mail.com"));
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        accounts.add(Arrays.asList("Mary", "mary@mail.com"));
        for (List<String> account : accountsMerge(accounts)) {
            System.out.println(account);
        }
        // Time: O(n log n) Space: O(n)

        // You’re given a graph with N nodes labeled from 1 to N, forming a tree plus one extra edge.
        // The input is a list of edges. The graph is connected and has a cycle.
        // Return the redundant edge that causes the cycle (the one you would remove to form a valid tree).
        int[][] edges1 = {{1, 2}, {1, 3}, {2, 3}};
        System.out.println(Arrays.toString(findRedundantConnection(edges1)));
        // Time: O(n) Space: O(n)

        // You’re given an adjacency matrix isConnected where:
        // isConnected[i][j] == 1 → person i and j are directly friends.
        // A friend circle (province) is a group of people directly or indirectly connected.
        int[][] isConnected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        System.out.println(findCircleNum(isConnected));
        // Time: O(n^2) Space: O(n)
    }

    private static int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];

        for (int i = 0; i < n; i++)
            parent[i] = i;

        int component = n;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];

            int rootU = findRoot(parent, u);
            int rootV = findRoot(parent, v);

            if (rootU != rootV) {
                parent[rootU] = rootV;
                component--;
            }
        }
        return component;
    }

    private static int findRoot(int[] parent, int x) {
        if (parent[x] != x)
            parent[x] = findRoot(parent, parent[x]);
        return parent[x];
    }

    private static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, String> parent = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();

        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                parent.putIfAbsent(email, email);
                emailToName.put(email, name);
                emailUnion(parent, email, account.get(1));
            }
        }

        Map<String, TreeSet<String>> unions = new HashMap<>();
        for (String email : parent.keySet()) {
            String root = findEmailRoot(parent, email);
            unions.computeIfAbsent(root, x -> new TreeSet<>()).add(email);
        }

        List<List<String>> merged = new ArrayList<>();
        for (String root : unions.keySet()) {
            List<String> emails = new ArrayList<>();
            emails.add(emailToName.get(root));
            emails.addAll(unions.get(root));
            merged.add(emails);
        }

        return merged;
    }

    private static String findEmailRoot(Map<String, String> parent, String email) {
        if (!parent.get(email).equals(email))
            parent.put(email, findEmailRoot(parent, parent.get(email)));
        return parent.get(email);
    }

    private static void emailUnion(Map<String, String> parent, String a, String b) {
        String rootA = findEmailRoot(parent, a);
        String rootB = findEmailRoot(parent, b);
        if (!rootA.equals(rootB))
            parent.put(rootA, rootB);
    }

    private static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];

        for (int i = 1; i <= n; i++)
            parent[i] = i;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            int rootU = findRoot(parent, u);
            int rootV = findRoot(parent, v);

            if (rootU == rootV)
                return edge;

            parent[rootU] = rootV;
        }

        return new int[0];
    }

    private static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];

        for (int i = 0; i < n; i++)
            parent[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++)
                if (isConnected[i][j] == 1)
                    provinceUnion(parent, i, j);
        }

        Set<Integer> uniqueParents = new HashSet<>();
        for (int i = 0; i < n; i++)
            uniqueParents.add(findRoot(parent, i));

        return uniqueParents.size();
    }

    private static void provinceUnion(int[] parent, int u, int v) {
        int rootU = findRoot(parent, u);
        int rootV = findRoot(parent, v);
        if (rootU != rootV)
            parent[rootU] = rootV;
    }
}
