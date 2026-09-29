import java.util.Scanner;

public class Main {
    private static StudentLinkedList studentList = new StudentLinkedList();
    private static ActionStack history = new ActionStack();
    private static ServiceQueue serviceQueue = new ServiceQueue();
    private static BSTree bst = new BSTree();
    private static HashTable hashTable = new HashTable();
    private static CampusGraph graph = new CampusGraph();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: studentList.displayAll(); break;
                case 5: addServiceRequest(); break;
                case 6: processServiceRequest(); break;
                case 7: history.displayRecent(); break;
                case 8: bst.displayInOrder(); break;
                case 9: searchStudent(); break;
                case 10: addCampusLocation(); break;
                case 11: removeCampusLocation(); break;
                case 12: addCampusConnection(); break;
                case 13: removeCampusConnection(); break;
                case 14: graph.displayGraph(); break;
                case 15: traverseCampus(); break;
                case 16: System.out.println("Exiting... Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 16);
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== University Student & Campus Management System =====");
        System.out.println("1. Add Student Record");
        System.out.println("2. Update Student Record");
        System.out.println("3. Delete Student Record");
        System.out.println("4. Display All Records (Linked List)");
        System.out.println("5. Add Service Request to Queue");
        System.out.println("6. Process Next Service Request");
        System.out.println("7. Display Recent Actions (Stack)");
        System.out.println("8. Display Students (BST - sorted by ID)");
        System.out.println("9. Search Student (Hashing)");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations (BFS/DFS)");
        System.out.println("16. Exit");
    }

    // ---------- Student record operations ----------

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty() || studentList.contains(id)) {
            System.out.println("Invalid or duplicate Student ID.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Programme: ");
        String programme = sc.nextLine().trim();
        double marks = readDouble("Enter Marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks. Must be between 0 and 100.");
            return;
        }
        Student student = new Student(id, name, programme, marks);
        if (studentList.add(student)) {
            bst.insert(student);
            hashTable.insert(student);
            history.push("Added student: " + id);
            System.out.println("Student added successfully.");
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine().trim();
        if (!studentList.contains(id)) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter new Name (leave blank to keep unchanged): ");
        String name = sc.nextLine().trim();
        System.out.print("Enter new Programme (leave blank to keep unchanged): ");
        String programme = sc.nextLine().trim();
        System.out.print("Enter new Marks (leave blank to keep unchanged): ");
        String marksInput = sc.nextLine().trim();
        Double marks = null;
        if (!marksInput.isEmpty()) {
            try {
                marks = Double.parseDouble(marksInput);
                if (marks < 0 || marks > 100) {
                    System.out.println("Invalid marks. Update aborted.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks format. Update aborted.");
                return;
            }
        }

        studentList.update(id, name, programme, marks);
        Student updated = studentList.find(id);
        bst.insert(updated);
        hashTable.insert(updated);
        history.push("Updated student: " + id);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine().trim();
        Student removed = studentList.delete(id);
        if (removed == null) {
            System.out.println("Student not found.");
            return;
        }
        bst.delete(id);
        hashTable.remove(id);
        history.push("Deleted student: " + id);
        System.out.println("Student deleted successfully.");
    }

    private static void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = sc.nextLine().trim();
        Student result = hashTable.search(id);
        if (result != null) System.out.println("Found: " + result);
        else System.out.println("Student not found.");
    }

    // ---------- Service queue operations ----------

    private static void addServiceRequest() {
        System.out.print("Enter service request description: ");
        String request = sc.nextLine().trim();
        if (request.isEmpty()) {
            System.out.println("Request description cannot be empty.");
            return;
        }
        serviceQueue.enqueue(request);
        history.push("Service request queued: " + request);
        System.out.println("Request added to queue.");
    }

    private static void processServiceRequest() {
        String request = serviceQueue.dequeue();
        if (request != null) {
            System.out.println("Processed request: " + request);
            history.push("Processed request: " + request);
        }
    }

    // ---------- Campus graph operations ----------

    private static void addCampusLocation() {
        System.out.print("Enter new campus location name: ");
        String loc = sc.nextLine().trim();
        if (loc.isEmpty()) {
            System.out.println("Location name cannot be empty.");
            return;
        }
        if (graph.addLocation(loc)) {
            history.push("Added campus location: " + loc);
            System.out.println("Location added.");
        }
    }

    private static void removeCampusLocation() {
        System.out.print("Enter campus location to remove: ");
        String loc = sc.nextLine().trim();
        if (graph.removeLocation(loc)) {
            history.push("Removed campus location: " + loc);
            System.out.println("Location removed.");
        }
    }

    private static void addCampusConnection() {
        System.out.print("Enter first location: ");
        String loc1 = sc.nextLine().trim();
        System.out.print("Enter second location: ");
        String loc2 = sc.nextLine().trim();
        if (graph.addConnection(loc1, loc2)) {
            history.push("Connected " + loc1 + " <-> " + loc2);
            System.out.println("Connection added.");
        }
    }

    private static void removeCampusConnection() {
        System.out.print("Enter first location: ");
        String loc1 = sc.nextLine().trim();
        System.out.print("Enter second location: ");
        String loc2 = sc.nextLine().trim();
        if (graph.removeConnection(loc1, loc2)) {
            history.push("Removed connection " + loc1 + " <-> " + loc2);
            System.out.println("Connection removed.");
        }
    }

    private static void traverseCampus() {
        System.out.print("Enter starting location: ");
        String start = sc.nextLine().trim();
        int choice = readInt("Choose traversal (1=BFS, 2=DFS): ");
        if (choice == 1) graph.bfs(start);
        else if (choice == 2) graph.dfs(start);
        else System.out.println("Invalid choice.");
    }

    // ---------- Input validation helpers ----------

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            System.out.print(prompt);
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            System.out.print(prompt);
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}
