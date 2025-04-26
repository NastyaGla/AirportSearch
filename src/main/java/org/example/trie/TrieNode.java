package org.example.trie;

import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    List<Integer> indexValues = new ArrayList<>();

    TrieNode getChild(char c) {
        return children.get(c);
    }

    TrieNode addChild(char c) {
        return children.computeIfAbsent(c, k -> new TrieNode());
    }
}
