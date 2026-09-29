import java.util.LinkedList;

// Hash table (chaining) for efficient Student ID search
public class HashTable {
    private static final int TABLE_SIZE = 20;
    private LinkedList<Student>[] table;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) table[i] = new LinkedList<>();
    }

    private int hash(String id) {
        return Math.abs(id.hashCode()) % TABLE_SIZE;
    }

    public void insert(Student student) {
        int index = hash(student.getStudentId());
        table[index].removeIf(s -> s.getStudentId().equals(student.getStudentId()));
        table[index].add(student);
    }

    public Student search(String id) {
        int index = hash(id);
        for (Student s : table[index]) {
            if (s.getStudentId().equals(id)) return s;
        }
        return null;
    }

    public void remove(String id) {
        int index = hash(id);
        table[index].removeIf(s -> s.getStudentId().equals(id));
    }
}
