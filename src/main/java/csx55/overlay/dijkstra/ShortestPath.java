package csx55.overlay.dijkstra;

import java.util.*;

public class ShortestPath {

    private ArrayList<String> edges; /* list of all the edges connecting the nodes */
    private HashMap<String, Integer> graph; /* Map of all the edges and their corresponding weights */

    private ArrayList<String> towns;

    private int status; /* 0 for success, 1 for failure */

    public ShortestPath(ArrayList<String> e, HashMap<String, Integer> m) {
        this.edges = e;
        this.graph = m;
    } // End ShortestPath() constructor

    public int getStatus() {
        return this.status;
    } // End getStatus() method

    /* source is the current messaging node object */
    /* sinkNode is the node we want to send the message to */
    public void calculateShortestPath(String sourceNode, String destinationNode) {
        // System.out.println("Our source node: " + sourceNode);
        // System.out.println("Our destinationNode: " + destinationNode);
        
        String[] nodes = edges.get(0).split(" - "); /* the edges come in the format of: hostNameA:portNumA - hostNameB:portNumB */

        String nodeA = nodes[0].substring(0, nodes[0].indexOf(":"));
        String nodeB = nodes[1].substring(0, nodes[1].indexOf(":"));;

        // System.out.println("Node A: " + nodeA);
        // System.out.println("Node B: " + nodeB);

    } // End calculateShortestPath() method
 
} // End ShortestPath class
