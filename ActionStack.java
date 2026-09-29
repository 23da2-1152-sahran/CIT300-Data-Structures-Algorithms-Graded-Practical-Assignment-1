import java.util.LinkedList;

// Stack (LIFO) to track recent actions / history
public class ActionStack {
    private LinkedList<String> stack = new LinkedList<>();
    private static final int MAX_HISTORY = 50;

    public void push(String action) {
        stack.addFirst(action);
        if (stack.size() > MAX_HISTORY) stack.removeLast();
    }

    public String pop() {
        if (stack.isEmpty()) return null;
        return stack.removeFirst();
    }

    public void displayRecent() {
        if (stack.isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("---- Recent Actions (Most Recent First) ----");
        int count = 1;
        for (String action : stack) {
            System.out.println(count++ + ". " + action);
        }
    }

    public boolean isEmpty() { return stack.isEmpty(); }
}
