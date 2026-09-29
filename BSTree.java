// Binary Search Tree keyed by Student ID
public class BSTree {

    private class Node {
        Student data;
        Node left, right;
        Node(Student data) { this.data = data; }
    }

    private Node root;

    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private Node insertRec(Node node, Student student) {
        if (node == null) return new Node(student);
        int cmp = student.getStudentId().compareTo(node.data.getStudentId());
        if (cmp < 0) node.left = insertRec(node.left, student);
        else if (cmp > 0) node.right = insertRec(node.right, student);
        else node.data = student; // refresh existing record
        return node;
    }

    public Student search(String id) {
        Node curr = root;
        while (curr != null) {
            int cmp = id.compareTo(curr.data.getStudentId());
            if (cmp == 0) return curr.data;
            curr = cmp < 0 ? curr.left : curr.right;
        }
        return null;
    }

    public void delete(String id) {
        root = deleteRec(root, id);
    }

    private Node deleteRec(Node node, String id) {
        if (node == null) return null;
        int cmp = id.compareTo(node.data.getStudentId());
        if (cmp < 0) node.left = deleteRec(node.left, id);
        else if (cmp > 0) node.right = deleteRec(node.right, id);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = node.right;
            while (successor.left != null) successor = successor.left;
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("No records in tree.");
            return;
        }
        System.out.println("---- Students Sorted by ID (BST In-Order) ----");
        inOrderRec(root);
    }

    private void inOrderRec(Node node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.println(node.data);
        inOrderRec(node.right);
    }
}
