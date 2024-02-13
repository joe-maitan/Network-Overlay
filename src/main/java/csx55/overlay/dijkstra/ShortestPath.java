package csx55.overlay.dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class ShortestPath {

    private ArrayList<String> edges; /* list of all the edges connecting the nodes */
    private HashMap<String, Integer> graph; /* Map of all the edges and their corresponding weights */

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
    public void calculateShortestPath(String sourceNode, String sinkNode) {
        System.out.println("Our source node: " + sourceNode);
        System.out.println("Our sinkNode: " + sinkNode);
        
        String[] nodes = edges.get(0).split(" - ");

        

        // System.out.println(nodes[0]);
        // System.out.println(nodes[1]);
    } // End calculateShortestPath() method
 
} // End ShortestPath class
