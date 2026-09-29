// Custom singly linked list for managing Student records
public class StudentLinkedList {

    private class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    private Node head;
    private int size;

    public boolean add(Student student) {
        if (contains(student.getStudentId())) {
            System.out.println("Error: Student ID already exists.");
            return false;
        }
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node curr = head;
            while (curr.next != null) curr = curr.next;
            curr.next = newNode;
        }
        size++;
        return true;
    }

    public boolean update(String id, String name, String programme, Double marks) {
        Node curr = head;
        while (curr != null) {
            if (curr.data.getStudentId().equals(id)) {
                if (name != null && !name.isEmpty()) curr.data.setName(name);
                if (programme != null && !programme.isEmpty()) curr.data.setProgramme(programme);
                if (marks != null) curr.data.setMarks(marks);
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public Student delete(String id) {
        Node curr = head, prev = null;
        while (curr != null) {
            if (curr.data.getStudentId().equals(id)) {
                if (prev == null) head = curr.next;
                else prev.next = curr.next;
                size--;
                return curr.data;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public Student find(String id) {
        Node curr = head;
        while (curr != null) {
            if (curr.data.getStudentId().equals(id)) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    public boolean contains(String id) {
        return find(id) != null;
    }

    public void displayAll() {
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("---- All Student Records (Linked List) ----");
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.data);
            curr = curr.next;
        }
    }

    public int size() { return size; }
}
