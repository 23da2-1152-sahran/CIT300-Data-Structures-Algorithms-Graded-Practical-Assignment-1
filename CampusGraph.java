import java.util.*;

// Graph (adjacency list) representing campus locations and roads/connections
public class CampusGraph {
    private Map<String, List<String>> adjList = new LinkedHashMap<>();

    public boolean addLocation(String location) {
        if (adjList.containsKey(location)) {
            System.out.println("Location already exists.");
            return false;
        }
        adjList.put(location, new ArrayList<>());
        return true;
    }

    public boolean removeLocation(String location) {
        if (!adjList.containsKey(location)) {
            System.out.println("Location not found.");
            return false;
        }
        adjList.remove(location);
        for (List<String> neighbours : adjList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    public boolean addConnection(String loc1, String loc2) {
        if (!adjList.containsKey(loc1) || !adjList.containsKey(loc2)) {
            System.out.println("Both locations must exist first.");
            return false;
        }
        if (!adjList.get(loc1).contains(loc2)) adjList.get(loc1).add(loc2);
        if (!adjList.get(loc2).contains(loc1)) adjList.get(loc2).add(loc1);
        return true;
    }

    public boolean removeConnection(String loc1, String loc2) {
        if (!adjList.containsKey(loc1) || !adjList.containsKey(loc2)) {
            System.out.println("Both locations must exist.");
            return false;
        }
        adjList.get(loc1).remove(loc2);
        adjList.get(loc2).remove(loc1);
        return true;
    }

    public void displayGraph() {
        if (adjList.isEmpty()) {
            System.out.println("No campus locations added yet.");
            return;
        }
        System.out.println("---- Campus Network (Adjacency List) ----");
        for (String loc : adjList.keySet()) {
            System.out.println(loc + " -> " + adjList.get(loc));
        }
    }

    public void bfs(String start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Location not found.");
            return;
        }
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        System.out.print("BFS Traversal: ");
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            System.out.print(curr + " ");
            for (String neighbour : adjList.get(curr)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        System.out.println();
    }

    public void dfs(String start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Location not found.");
            return;
        }
        Set<String> visited = new LinkedHashSet<>();
        System.out.print("DFS Traversal: ");
        dfsHelper(start, visited);
        System.out.println();
    }

    private void dfsHelper(String node, Set<String> visited) {
        visited.add(node);
        System.out.print(node + " ");
        for (String neighbour : adjList.get(node)) {
            if (!visited.contains(neighbour)) dfsHelper(neighbour, visited);
        }
    }
}
