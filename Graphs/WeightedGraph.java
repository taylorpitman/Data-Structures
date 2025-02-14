import java.util.*;

/**
 * WeightedGraph implementation using adjacency list representation
 * Supports weighted edges and common graph operations
 * Used for: Network flow, shortest path problems, minimum spanning trees
 */
public class WeightedGraph<T> implements Graph<T> {
    /**
     * Inner Edge class to represent weighted connections
     * Each edge stores its destination vertex and associated weight
     */
    private class Edge {
        T destination;
        double weight;
        
        Edge(T destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }
    
    // Map of vertices to their list of weighted edges
    private Map<T, List<Edge>> adjacencyList;
    
    /**
     * Constructor initializes empty adjacency list
     * Time Complexity: O(1)
     */
    public WeightedGraph() {
        adjacencyList = new HashMap<>();
    }
    
    /**
     * Adds a weighted edge between two vertices
     * Time Complexity: O(1)
     * @param source Starting vertex
     * @param destination Ending vertex
     * @param weight Edge weight/cost
     */
    public void addEdge(T source, T destination, double weight) {
        addVertex(source);
        addVertex(destination);
        adjacencyList.get(source).add(new Edge(destination, weight));
    }

    /**
     * Adds a new vertex to the graph if it doesn't exist
     * Time Complexity: O(1)
     * @param vertex The vertex to add
     */
    @Override
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    /**
     * Adds unweighted edge (weight = 1.0)
     * Implements Graph interface method
     * Time Complexity: O(1)
     */
    @Override
    public void addEdge(T source, T destination) {
        addEdge(source, destination, 1.0);
    }

    /**
     * Removes a vertex and all its connections
     * Time Complexity: O(V + E) where V = vertices, E = edges
     * @param vertex The vertex to remove
     */
    @Override
    public void removeVertex(T vertex) {
        // Remove all edges pointing to this vertex
        for (List<Edge> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.destination.equals(vertex));
        }
        // Remove the vertex and its edges
        adjacencyList.remove(vertex);
    }

    /**
     * Removes an edge between two vertices
     * Time Complexity: O(E) where E = edges from source
     */
    @Override
    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).removeIf(edge -> 
                edge.destination.equals(destination));
        }
    }

    /**
     * Checks if vertex exists in graph
     * Time Complexity: O(1)
     */
    @Override
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    /**
     * Checks if edge exists between vertices
     * Time Complexity: O(E) where E = edges from source
     */
    @Override
    public boolean hasEdge(T source, T destination) {
        if (!adjacencyList.containsKey(source)) return false;
        return adjacencyList.get(source).stream()
            .anyMatch(edge -> edge.destination.equals(destination));
    }

    /**
     * Returns list of adjacent vertices (without weights)
     * Time Complexity: O(E) where E = edges from vertex
     */
    @Override
    public List<T> getAdjacent(T vertex) {
        List<T> neighbors = new ArrayList<>();
        if (adjacencyList.containsKey(vertex)) {
            for (Edge edge : adjacencyList.get(vertex)) {
                neighbors.add(edge.destination);
            }
        }
        return neighbors;
    }

    /**
     * Returns total number of vertices
     * Time Complexity: O(1)
     */
    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Returns set of all vertices
     * Time Complexity: O(1)
     */
    @Override
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    /**
     * Prints graph with vertices, edges, and weights
     * Time Complexity: O(V + E)
     */
    @Override
    public void printGraph() {
        for (Map.Entry<T, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.print("Vertex " + entry.getKey() + " -> ");
            for (Edge edge : entry.getValue()) {
                System.out.print(edge.destination + 
                    "(weight: " + edge.weight + ") ");
            }
            System.out.println();
        }
    }
}