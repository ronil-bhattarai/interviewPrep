package com.ronil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeTraversal {

    private static int diameter = 0;

    public static void main(String[] args) {

        // Given the root of a binary tree, return the level order traversal of its nodes’ values. (i.e., from left to right, level by level)
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        for (List<Integer> level : levelOrder(root)) {
            System.out.println(level);
        }
        // Time: O(n) Space: O(n)

        // Traverse a binary tree level-by-level, but alternate the direction: Left → Right on level 0. Right → Left on level 1. Left → Right on level 2 and so on…
        for (List<Integer> level : zigzagLevelOrder(root)) {
            System.out.println(level);
        }
        //Time: O(n) Space: O(n)

        // Given the root of a binary tree and an integer targetSum, return true if there exists a root-to-leaf path such that the sum of the node values equals targetSum
        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(8);
        root1.left.left = new TreeNode(11);
        root1.left.left.left = new TreeNode(7);
        root1.left.left.right = new TreeNode(2);
        root1.right.left = new TreeNode(13);
        root1.right.right = new TreeNode(4);
        int targetSum = 22;
        System.out.println(hasPathSum(root1, targetSum));
        // Time: O(n) Space: O(h) height of tree

        // Find the length of the longest path between any two nodes in a binary tree. This path may or may not pass through the root. The path is measured in number of edges, not nodes.
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);
        System.out.println(diameterOfBinaryTree(root2));
        // Time: O(n) Space: O(h)

        // Given the root of a binary tree and two nodes p and q, find their lowest common ancestor (LCA). The LCA of two nodes p and q is the lowest node in the tree that has both p and q as descendants (a node can be a descendant of itself).
        TreeNode root3 = new TreeNode(3);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(1);
        root3.left.left = new TreeNode(6);
        root3.left.right = new TreeNode(2);
        root3.right.left = new TreeNode(0);
        root3.right.right = new TreeNode(8);
        root3.left.right.left = new TreeNode(7);
        root3.left.right.right = new TreeNode(4);

        TreeNode p = root3.left;
        TreeNode q = root3.right;

        System.out.println(lowestCommonAncestor(root3, p, q).val);
        // Time: O(n) Space: O(h)
    }

    private static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                level.add(current.val);

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            result.add(level);
        }
        return result;
    }

    private static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> level = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (leftToRight) level.addLast(current.val);
                else level.addFirst(current.val);

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }

    private static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        if (root.left == null && root.right == null) return targetSum == root.val;

        int remainingSum = targetSum - root.val;
        return hasPathSum(root.left, remainingSum) || hasPathSum(root.right, remainingSum);
    }

    private static int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        height(root);
        return diameter;
    }

    private static int height(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        diameter = Math.max(diameter, leftHeight + rightHeight);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;

        return left != null ? left : right;
    }
}

