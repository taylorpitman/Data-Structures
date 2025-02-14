
import java.util.*;

/*
 * Directed Graph implementation using adjacency list
 * Supports topological sorting, cycle detection, and in-degree calculation
 * Time Complexity: Most operations O(V + E) where V = vertices, E = edges 
 */

public class GraphDirected<T> implements Graph<T> {
    private Map<T, List<T>> adjacencyList;

    public GraphDirected() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a new vertex to the graph
     * Time Complexity: O(1)
     */
    @Override
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * Adds a directed edge from source to destination
     * Note: In directed graph, edge goes only one way (source → destination)
     * Time Complexity: O(1)
     */
    @Override
    public void addEdge(T source, T destination) {
        // Only add edge in one direction
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(destination);
    }

    @Override
    public void removeVertex(T vertex) {
        // Remove vertex from all adjacency lists
        for (List<T> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
        // Remove vertex and its list
        adjacencyList.remove(vertex);
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
    }

    @Override
    public List<T> getAdjacent(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(T source, T destination) {
        return adjacencyList.containsKey(source) && 
               adjacencyList.get(source).contains(destination);
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public void printGraph() {
        for (T vertex : adjacencyList.keySet()) {
            System.out.print("Vertex " + vertex + " -> ");
            System.out.println(adjacencyList.get(vertex));
        }
    }

    // Additional methods specific to directed graphs

    /**
     * Performs topological sort using DFS
     * Used for: Dependency resolution, task scheduling, build systems
     * Requirements: Graph must be DAG (Directed Acyclic Graph)
     * Time Complexity: O(V + E)
     */
    public List<T> topologicalSort() {
        Stack<T> stack = new Stack<>();
        Set<T> visited = new HashSet<>();
        
        for (T vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                topologicalSortUtil(vertex, visited, stack);
            }
        }

        List<T> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void topologicalSortUtil(T vertex, Set<T> visited, Stack<T> stack) {
        visited.add(vertex);

        for (T neighbor : adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }

        stack.push(vertex);
    }

    /**
     * Detects if graph contains a cycle
     * Uses DFS with a recursion stack to track back edges
     * Time Complexity: O(V + E)
     */
    public boolean hasCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recursionStack = new HashSet<>();

        for (T vertex : adjacencyList.keySet()) {
            if (hasCycleUtil(vertex, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleUtil(T vertex, Set<T> visited, Set<T> recursionStack) {
        if (recursionStack.contains(vertex)) {
            return true;
        }
        if (visited.contains(vertex)) {
            return false;
        }

        visited.add(vertex);
        recursionStack.add(vertex);

        for (T neighbor : adjacencyList.get(vertex)) {
            if (hasCycleUtil(neighbor, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(vertex);
        return false;
    }

    /**
     * Calculates number of edges pointing to a vertex
     * Used in: Kahn's algorithm, determining source vertices
     * Time Complexity: O(V + E)
     */
    public int getInDegree(T vertex) {
        int count = 0;
        for (List<T> neighbors : adjacencyList.values()) {
            if (neighbors.contains(vertex)) {
                count++;
            }
        }
        return count;
    }
}