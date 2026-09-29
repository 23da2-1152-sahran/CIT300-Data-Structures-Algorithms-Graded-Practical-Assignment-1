import java.util.LinkedList;

// Queue (FIFO) to manage student service requests in order of arrival
public class ServiceQueue {
    private LinkedList<String> queue = new LinkedList<>();

    public void enqueue(String request) {
        queue.addLast(request);
    }

    public String dequeue() {
        if (queue.isEmpty()) {
            System.out.println("No pending service requests.");
            return null;
        }
        return queue.removeFirst();
    }

    public void displayAll() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("---- Pending Service Requests ----");
        int pos = 1;
        for (String r : queue) System.out.println(pos++ + ". " + r);
    }

    public boolean isEmpty() { return queue.isEmpty(); }
}
