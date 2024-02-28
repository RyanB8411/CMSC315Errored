package Burkhardt_Project_4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {

    private Map<Character, Vertex> vertices = new HashMap<>();
    private Map<Integer, Edge> edges = new HashMap<>();
    private char[] names = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private int vertIndex = 0;
    private int edgeIndex = 0;

    // Construct the empty graph
    public Graph() {
    }

    // method to add a vertex
    public Vertex addVertex(Double x, Double y) {
        Vertex v = new Vertex(names[vertIndex], x, y);
        vertices.put(names[vertIndex], v);
        vertIndex += 1;
        return v;
    }
    //method to get a vertex by its name
    public Vertex getVertex(char c) {
        for (Map.Entry<Character, Vertex> entry : vertices.entrySet()) {
            if (entry.getKey().equals(c)) {
                return entry.getValue();
            }
        }
        return null; // return null if the vertex is not found
    }

    // method to add an edge
    public Edge addEdge(Vertex vert1, Vertex vert2) {
        Edge e = new Edge(edgeIndex, vert1, vert2);
        edges.put(edgeIndex, e);
        edgeIndex += 1;
        return e;
    }

    ArrayList<Edge> getConnectedEdges(Vertex vertex) {
        ArrayList<Edge> result = new ArrayList<>();
        for (Map.Entry<Integer, Edge> edge : edges.entrySet()) {
            if (edge.getValue().v1 == vertex || edge.getValue().v2 == vertex)
                result.add(edge.getValue());
        }
        return result;
    }

    ArrayList<Vertex> getConnectedVertices(Vertex vertex) {
        ArrayList<Vertex> result = new ArrayList<>();
        for (Map.Entry<Integer, Edge> edge : edges.entrySet()) {
            if (edge.getValue().v1 == vertex)
                result.add(edge.getValue().v2);
            else if (edge.getValue().v2 == vertex)
                result.add(edge.getValue().v1);
        }
        return result;
    }

    private boolean checkCycle(Vertex vertex, Vertex original) {
        ArrayList<Edge> connections = getConnectedEdges(vertex);
        for (Edge con : connections) {
            if (!visited.contains(con)) {
                System.out.println(vertex.getVertexName());
                visited.add(con);
                if (con.v1 == original || con.v2 == original)
                    return true;
                Vertex conVert = con.v1;
                if (conVert == vertex) // We want the other vertex, not the current one
                    conVert = con.v2;
                if (checkCycle(conVert, original))
                    return true;
            }
        }
        return false;
    }

    // method that checks whether the graph has cycles
    ArrayList<Edge> visited;

    public boolean checkCycle() {
        for (Map.Entry<Integer, Edge> edge : edges.entrySet()) {
            visited = new ArrayList<>();
            visited.add(edge.getValue());
            if (checkCycle(edge.getValue().v2, edge.getValue().v1))
                return true;
        }
        return false;
    }

    ArrayList<Vertex> visitedVert;
    public Boolean isConnected(){
        visitedVert = new ArrayList<>();
        Vertex startPoint = vertices.get('A');
        visitedVert.add(startPoint);
        for (int i = 0; i < visitedVert.size(); i++){
            for(Vertex vertex : getConnectedVertices(visitedVert.get(i))){
                if (!visitedVert.contains(vertex)){
                    visitedVert.add(vertex);
                }
            }

        }
        return visitedVert.size() == vertices.size();
    }

    // method returns a list of vertices resulting from a depth-first graph search
    public String depthFirstSearch() {
        Stack<Vertex> dfsStack = new Stack<>();
        Set<Vertex> visited = new HashSet<>();
        String dfsString = "";
    
        dfsStack.push(vertices.get('A')); // Starting from vertex 'A'
        visited.add(vertices.get('A'));
        dfsString += "Visited: ";
    
        while (!dfsStack.isEmpty()) {
            Vertex currentVertex = dfsStack.pop();
            dfsString += currentVertex.getVertexName()+ " ";
    
            // Get connected vertices and add them to the stack if not visited
            for (Vertex neighbor : getConnectedVertices(currentVertex)) {
                if (!visited.contains(neighbor)) {
                    dfsStack.push(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return dfsString;
    }

    // method returns a list of vertices resulting from a breadth-first search
    public String breadthFirstSearch() {
        Queue<Vertex> bfsQueue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        String bfsString = "";
    
        bfsQueue.offer(vertices.get('A')); // Starting from vertex 'A'
        visited.add(vertices.get('A'));
        bfsString += "Visited: ";
    
        while (!bfsQueue.isEmpty()) {
            Vertex currentVertex = bfsQueue.poll();
            bfsString += currentVertex.getVertexName()+ " ";
    
            // Get connected vertices and add them to the queue if not visited
            for (Vertex neighbor : getConnectedVertices(currentVertex)) {
                if (!visited.contains(neighbor)) {
                    bfsQueue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return bfsString;
    }
}
class Edge {
    Integer index;
    Vertex v1;
    Vertex v2;

    Edge(Integer index, Vertex v1, Vertex v2) {
        this.index = index;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getEdgeStart(){
        return v1;
    }

    public Vertex getEdgeEnd(){
        return v2;
    }

    public String toString() {
        return (v1.getVertexName() + " to " + v2.getVertexName());
    }
}