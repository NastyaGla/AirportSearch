package org.example.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word, int indexValue) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.addChild(c);
        }
        node.indexValues.add(indexValue);
    }

    public List<Integer> searchByPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.getChild(c);
            if (node == null) return Collections.emptyList();
        }
        return collectAllIndexes(node);
    }

    private List<Integer> collectAllIndexes(TrieNode node) {
        List<Integer> result = new ArrayList<>(node.indexValues);
        for (TrieNode child : node.children.values()) {
            result.addAll(collectAllIndexes(child));
        }
        return result;
    }
}
