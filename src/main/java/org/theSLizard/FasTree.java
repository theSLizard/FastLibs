package org.theSLizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FasTree<T> {

    private final class Node {

        public final Basket<?> entry;
        public Node leftBranch;
        public Node rightBranch;

        public Node(Basket<?> entry) {
            this.entry = entry;
        }
    }

    public FasTree(Basket root, Basket node1, Basket node2) {
        this.root = new Node(root);
        this.distributeBaskets(root, node1, node2);
        // recursive variant
        /*
        Objects.requireNonNull(root);
        addBasketRecursive(root);          // start with the root
        if (node1 != null) addBasketRecursive(node1);
        if (node2 != null) addBasketRecursive(node2);*/
    }

    private Node root = null;

    public List<Basket<T>> inorder() {
        List<Basket<T>> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    public List<Basket<T>> postorder() {
        List<Basket<T>> result = new ArrayList<>();
        postorderRec(root, result);
        return result;
    }

    private void inorderRec(Node n, List<Basket<T>> out) {
        if (n == null) return;          // nothing to do for an empty branch
        inorderRec(n.leftBranch,   out); // left subtree first
        @SuppressWarnings("unchecked")
        Basket<T> b = (Basket<T>) n.entry;
        out.add(b);                     // visit the node itself
        inorderRec(n.rightBranch,  out); // then right subtree
    }

    private void postorderRec(Node n, List<Basket<T>> out) {
        if (n == null) return;
        postorderRec(n.leftBranch,  out);
        postorderRec(n.rightBranch, out);
        @SuppressWarnings("unchecked")
        Basket<T> b = (Basket<T>) n.entry;
        out.add(b);                     // node after its children
    }

    /* ---------- recursive helpers -------------------------------- */

    /** Recursively search for an entry with the given key. */
    private Basket<?> search(Node n, Integer index) {
        if (n == null) return null;                    // reached a leaf – not found

        int cmp = index.compareTo(n.entry.getIndex());
        if      (cmp == 0) return n.entry;
        else if (cmp <  0) return search(n.leftBranch , index);
        else               return search(n.rightBranch, index);
    }

    /** Recursively insert a basket and return the new subtree root. */
    private Node insert(Node n, Basket<?> b) {
        if (n == null) return new Node(b);             // place to insert

        int cmp = b.getIndex().compareTo(n.entry.getIndex());
        if      (cmp < 0) n.leftBranch  = insert(n.leftBranch , b);
        else if (cmp > 0) n.rightBranch = insert(n.rightBranch, b);
        /* if cmp == 0 we already have a node with this key – do nothing */
        return n;
    }

    public Basket<T> retrieveBasketByIndexRecursive(Integer index) {
        Objects.requireNonNull(index);
        @SuppressWarnings("unchecked")
        Basket<T> res = (Basket<T>) search(root, index);
        return res;
    }

    public Basket<T> retrieveBasketByIndex(Integer index) {
        Node tree = this.root;

        while (true) {

            if (index == tree.entry.getIndex()) {
                return (Basket<T>) tree.entry;
            }

            if (index < tree.entry.getIndex()) {
                if (null != tree.leftBranch) {
                    tree = tree.leftBranch;
                } else {
                    return null;
                }
            } else {
                if (null != tree.rightBranch) {
                    tree = tree.rightBranch;
                } else {
                    return null;
                }
            }
        }
    }

    private void distributeBaskets(Basket parent, Basket child1, Basket child2) {
        Node leftNode = null;
        Node rightNode = null;
        Basket leftBasket = null;
        Basket rightBasket = null;

        if (child1.getIndex() == child2.getIndex()) return;
        if (child1.getIndex() == parent.getIndex()) return;
        if (child2.getIndex() == parent.getIndex()) return;

        if (child1.getIndex() < child2.getIndex()) {
            leftBasket = child1;
            rightBasket = child2;
        } else {
            leftBasket = child2;
            rightBasket = child1;
        }

        if (leftBasket.getIndex() > parent.getIndex()) return;
        if (rightBasket.getIndex() < parent.getIndex()) return;

        Node node = this.root;

        if (child1.getIndex() < child2.getIndex())
            if (leftBasket.getIndex() < node.entry.getIndex()) {
                leftNode = new Node(leftBasket);
                node.leftBranch = leftNode;
            } else {
                rightNode = new Node(rightBasket);
                node.rightBranch = rightNode;
            }

        if (rightBasket.getIndex() > node.entry.getIndex()) {
            rightNode = new Node(rightBasket);
            node.rightBranch = rightNode;
        } else {
            leftNode = new Node(leftBasket);
            node.leftBranch = leftNode;
        }
    }

    public void addBasketRecursive(Basket<T> basket) {
        Objects.requireNonNull(basket);
        this.root = insert(this.root, basket);
    }

    public void addBasket(Basket basket) {
        Objects.requireNonNull(basket);
        Node node = this.root;

        if (null == node.leftBranch && basket.getIndex() < node.entry.getIndex()) {
            node.leftBranch = new Node(basket);
            return;
        }
        if (null == node.rightBranch && basket.getIndex() > node.entry.getIndex()) {
            node.rightBranch = new Node(basket);
            return;
        }

        while (true) {
            if (basket.getIndex() < node.entry.getIndex()) {
                if (null != node.leftBranch) {
                    node = node.leftBranch;
                } else {
                    node.leftBranch = new Node(basket);
                    break;
                }
            } else {
                if (null != node.rightBranch) {
                    node = node.rightBranch;
                } else {
                    node.rightBranch = new Node(basket);
                    break;
                }
            }
        }

    }
}
