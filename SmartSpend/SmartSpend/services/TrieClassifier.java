package services;

import java.util.*;

public class TrieClassifier {
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String category;
    }

    private final TrieNode root = new TrieNode();

    public TrieClassifier() {
        insert("burger", "Food");
        insert("flight", "Travel");
        insert("rent", "Housing");
        insert("uber", "Transport");
    }

    public void insert(String word, String category) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, k -> new TrieNode());
        }
        node.category = category;
    }

    public String classify(String desc) {
        desc = desc.toLowerCase();
        for (int i = 0; i < desc.length(); i++) {
            TrieNode node = root;
            int j = i;
            while (j < desc.length() && node.children.containsKey(desc.charAt(j))) {
                node = node.children.get(desc.charAt(j));
                if (node.category != null) return node.category;
                j++;
            }
        }
        return "Misc";
    }
}
