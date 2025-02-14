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

public interface Graph<T> {
    void addVertex(T vertex);
    void addEdge(T source, T destination);
    void removeVertex(T vertex);
    void removeEdge(T source, T destination);
    boolean hasVertex(T vertex);
    boolean hasEdge(T source, T destination);
    List<T> getAdjacent(T vertex);
    int getVertexCount();
    Set<T> getVertices();
    void printGraph();
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