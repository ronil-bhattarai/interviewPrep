package com.ronil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backtracking {
    public static void main(String[] args) {
        // Given a set of distinct integers, return all possible subsets (the power set)
        int[] nums = {1, 2, 3};
        for (List<Integer> subset : subsets(nums)) {
            System.out.println(subset);
        }
        // Time: O(2^n) Space: O(n)

        // Given an array of distinct integers candidates[] and a target integer, return all unique combinations where the chosen numbers sum up to the target.
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        for (List<Integer> list : combinationSum(candidates, target)) {
            System.out.println(list);
        }
        // Time: O(2^t) Space: O(t) + O(2^t)

        // Given a 2D board of characters and a word, return true if the word exists in the grid. The word can be constructed from letters of sequentially adjacent cells (horizontally or vertically), and each letter cell may not be used more than once.
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        System.out.println(exist(board, word));
        // Time: O(row * column * 4^(length of word)) Space: O(l)

        // Place N queens on an N × N chessboard such that no two queens share the same row, column, or diagonal and return all possible board configurations as a list of strings.
        int n = 4;
        for (List<String> config : solveNQueens(n)) {
            for (String row : config) {
                System.out.println(row);
            }
            System.out.println();
        }
        // Time: O(n!) Space: O(n^2 * n * solutions)

        // Given a string s, partition it such that every substring of the partition is a palindrome and return all possible palindrome partitioning of s.
        String s = "aab";
        for (List<String> part : partition(s)) {
            System.out.println(part);
        }
        // Time: O(2^n * n) Space: O(2^n * n)
    }

    private static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubset(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrackSubset(List<List<Integer>> result, List<Integer> current, int[] nums, int start) {
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrackSubset(result, current, nums, i + 1);
            current.removeLast();
        }
    }

    private static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombinationSum(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private static void backtrackCombinationSum(List<List<Integer>> result, List<Integer> current,
                                                int[] candidates, int remain, int start) {
        if (remain == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (remain < 0) return;

        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]);
            backtrackCombinationSum(result, current, candidates, remain - candidates[i], i);
            current.removeLast();
        }
    }

    private static boolean exist(char[][] board, String word) {
        int rows = board.length, columns = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) if (dfs(board, word, i, j, 0)) return true;
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int row, int column, int index) {
        if (index == word.length()) return true;

        if (row < 0 || row >= board.length || column < 0 || column > board[0].length
                || board[row][column] != word.charAt(index)) return false;

        char temp = board[row][column];
        board[row][column] = '#';

        boolean found = dfs(board, word, row + 1, column, index + 1) ||
                dfs(board, word, row - 1, column, index + 1) ||
                dfs(board, word, row, column + 1, index + 1) ||
                dfs(board, word, row, column - 1, index + 1);

        board[row][column] = temp;

        return found;
    }

    private static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];

        for (char[] row : board) Arrays.fill(row, '.');

        backtrackSolveNQueens(result, board, 0);
        return result;
    }

    private static void backtrackSolveNQueens(List<List<String>> result, char[][] board, int row) {
        int n = board.length;

        if (row == n) {
            result.add(construct(board));
            return;
        }

        for (int column = 0; column < n; column++) {
            if (isSafe(board, row, column)) {
                board[row][column] = 'Q';
                backtrackSolveNQueens(result, board, row + 1);
                board[row][column] = '.';
            }
        }
    }

    private static boolean isSafe(char[][] board, int row, int column) {
        int n = board.length;

        for (int i = 0; i < row; i++)
            if (board[i][column] == 'Q') return false;
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q') return false;
        for (int i = row - 1, j = column + 1; i >= 0 && j < n; i--, j++)
            if (board[i][j] == 'Q') return false;

        return true;
    }

    private static List<String> construct(char[][] board) {
        List<String> config = new ArrayList<>();
        for (char[] row : board) config.add(new String(row));
        return config;
    }

    private static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrackPartition(result, new ArrayList<>(), s, 0);
        return result;
    }

    private static void backtrackPartition(List<List<String>> result, List<String> current, String s, int start) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String substring = s.substring(start, end);
            if (isPalindrome(substring)) {
                current.add(substring);
                backtrackPartition(result, current, s, end);
                current.removeLast();
            }
        }
    }

    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) if (s.charAt(left++) != s.charAt(right--)) return false;
        return true;
    }
}
