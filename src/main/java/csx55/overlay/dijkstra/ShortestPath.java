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
    private HashMap<String, Integer> map; /* Map of all the edges and their corresponding weights */

    private int status; /* 0 for success, 1 for failure */

    public ShortestPath(ArrayList<String> e, HashMap<String, Integer> m) {
        this.edges = e;
        this.map = m;
    } // End ShortestPath() constructor

    public int getStatus() {
        return this.status;
    } // End getStatus() method

    // public HashMap<String, Integer> calculateShortestPath(String source, int portNum) {
    //     /* source is the current messaging node object */
    //     String[] nodes = edges.get(0).split(" - ");

    //     /* nodes[0] = Node a (from) */
    //     /* nodes[1] */


    //     /* the destination if whwatever node we randomly choose */
    // } // End calculateShortestPath() method

    // public String calculateShortestPath(String source, int portNum) {
    //     status = 0;
    //     return "Umimplemented Dijkstras";
    // }
    
} // End ShortestPath class
