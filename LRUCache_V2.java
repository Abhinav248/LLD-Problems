package abhinav.code;

import java.util.HashMap;

public class LRUCache_V2 {

    // Node for Doubly Linked List
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
    }

    private int pageSize;
    private HashMap<Integer, Node> cache;
    private Node head, tail;

    public LRUCache_V2(int pageSize) {
        this.pageSize = pageSize;
        this.cache = new HashMap<>();

        // Dummy head and tail nodes
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    // Add node to the front (just after head)
    private void addNodeToFront(Node node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    // Remove node from list
    private void removeNode(Node node) {
        Node before = node.prev;
        Node after = node.next;

        before.next = after;
        after.prev = before;
    }

    // Move an existing node to front
    private void moveToFront(Node node) {
        removeNode(node);
        addNodeToFront(node);
    }

    // Remove least recently used node (from tail)
    private Node removeLRUNode() {
        Node lru = tail.prev;
        removeNode(lru);
        return lru;
    }

    // Get value from cache
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1; // Not present
        }

        // Move accessed node to front
        moveToFront(node);
        return node.value;
    }

    // Put value in cache
    public void put(int key, int value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            moveToFront(node);
        } else {
            Node newNode = new Node();
            newNode.key = key;
            newNode.value = value;

            cache.put(key, newNode);
            addNodeToFront(newNode);

            if (cache.size() > pageSize) {
                Node lru = removeLRUNode();
                cache.remove(lru.key);
            }
        }
    }
}
