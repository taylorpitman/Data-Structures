import java.util.*;
            /*
            GRAPH FUNDAMENTALS:
            ------------------
            1. Types of Graphs:
            - Undirected: Edges have no direction (like Facebook friendships)
            - Directed (Digraph): Edges have direction (like Twitter follows)
            - Weighted: Edges have values/weights (like road distances)
            - Cyclic: Contains at least one cycle
            - Acyclic: No cycles (like dependencies)

            2. Common Applications:
            - Social Networks
            - GPS/Navigation
            - Network Routing
            - Dependency Management
            - Game AI pathfinding

            KEY METHODS & COMPLEXITY:
            ------------------------
            */
            
// Graph class represents an undirected graph using adjacency list
public class GraphUndirected<T> implements Graph<T> {

    // Using HashMap to store vertices and their adjacent vertices
    // Adjacency List: O(|V| + |E|) space complexity
    // Better for sparse graphs (fewer edges)
    private Map<T, List<T>> adjacencyList;

    // Constructor
    public GraphUndirected() {
        adjacencyList = new HashMap<>();
    }

    /* addVertex(T vertex): O(1)
     * - Used to add new nodes to graph
     * - Important for building graph structure */
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    /* addEdge(T source, T destination): O(1)
     * - Creates connection between vertices
     * - For undirected: adds both directions */
    
    // Add an edge between two vertices
    public void addEdge(T source, T destination) {
        // Add vertices if they don't exist
        addVertex(source);
        addVertex(destination);
        
        // Add the edge
        adjacencyList.get(source).add(destination);
        // For undirected graph, add the reverse edge
        adjacencyList.get(destination).add(source);
    }

    /* removeEdge(T source, T destination): O(E)
     * - Removes connection between vertices
     * - Must check both vertices exist
     */
    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source) && adjacencyList.containsKey(destination)) {
            adjacencyList.get(source).remove(destination);
            adjacencyList.get(destination).remove(source);
        }
    }

    /* removeVertex(T vertex): O(V + E)
     * - Must remove all edges connected to vertex
     * - Then remove vertex itself
     * - Important for maintaining graph integrity
     */
    public void removeVertex(T vertex) {
        // Remove all edges pointing to this vertex
        for (List<T> adjacentVertices : adjacencyList.values()) {
            adjacentVertices.remove(vertex);
        }
        // Remove the vertex and its adjacency list
        adjacencyList.remove(vertex);
    }

    /* getAdjacent(T vertex): O(1)
     * - Returns neighbors of a vertex
     * - Critical for traversal algorithms
     * - Used in BFS/DFS
     */
    public List<T> getAdjacent(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /* hasVertex/hasEdge: O(1)
     * - Validation methods
     * - Important before operations
     * - Prevents null pointer exceptions */
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    // Check if there's an edge between two vertices
    public boolean hasEdge(T source, T destination) {
        return adjacencyList.containsKey(source) && adjacencyList.get(source).contains(destination);
    }

    // Get the number of vertices
    public int getVertexCount() {
        return adjacencyList.size();
    }

    // Get all vertices
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    // Print the graph
    public void printGraph() {
        for (T vertex : adjacencyList.keySet()) {
            System.out.print("Vertex " + vertex + " is connected to: ");
            for (T adjacent : adjacencyList.get(vertex)) {
                System.out.print(adjacent + " ");
            }
            System.out.println();
        }
    }

     /* IMPORTANT GRAPH ALGORITHMS */

     /* 1. Breadth-First Search (BFS): O(V + E)
     *    - Level by level traversal
     *    - Shortest path in unweighted graphs
     *    - Uses Queue data structure */

    // BFS traversal starting from a source vertex
    public void bfs(T source) {

        // Check if source exists
        if (!adjacencyList.containsKey(source)) {
            return;
        }

        // Set to keep track of visited vertices
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();

        // Start BFS from source
        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            System.out.print(vertex + " ");

            for (T neighbor : adjacencyList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    /* 2. Depth-First Search (DFS): O(V + E)
     *    - Explore as far as possible along each branch
     *    - Detect cycles in graphs
     *    - Uses Stack or Recursion */

    // DFS traversal starting from a source vertex
    public void dfs(T source) {

        // Check if source exists
        if (!adjacencyList.containsKey(source)) {
            return;
        }

        // Set to keep track of visited vertices
        Set<T> visited = new HashSet<>();

        // Start DFS from source
        dfs(source, visited);
    }   

    // Recursive DFS helper function
    private void dfs(T vertex, Set<T> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");

        for (T neighbor : adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }
    }

    /* 3. Dijkstra's Algorithm: O((V + E) log V)
    *    - Shortest path in weighted graphs
    *    - Uses Priority Queue */

    // Dijkstra's Algorithm to find the shortest path
    public Map<T, Integer> dijkstra(T source) {

        // Check if source exists
        if (!adjacencyList.containsKey(source)) {
            return new HashMap<>();
        }

        // Initialize distances with infinity
        Map<T, Integer> distances = new HashMap<>();
        for (T vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        // Set to keep track of visited vertices
        Set<T> visited = new HashSet<>();

        // Priority Queue for vertices
        PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            visited.add(vertex);

            for (T neighbor : adjacencyList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(vertex) + 1; // Change 1 to edge weight
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return distances;
    }



}

    /* COMMON INTERVIEW TOPICS:
    -----------------------
    1. Graph Traversal (BFS/DFS)
    2. Shortest Path Problems
    3. Cycle Detection
    4. Topological Sort
    5. Connected Components
    6. Bipartite Graph Verification

    IMPLEMENTATION CHOICES:
    ----------------------
    1. Adjacency List: Better for sparse graphs
    - Space: O(V + E)
    - Good for most real-world applications

    2. Adjacency Matrix: Better for dense graphs
    - Space: O(V²)
    - Good for quick edge lookups */