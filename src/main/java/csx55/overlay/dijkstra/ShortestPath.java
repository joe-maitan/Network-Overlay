package csx55.overlay.dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class ShortestPath {

    // Start Node
    // list of connections and weights
    // sink node

    private ArrayList<String> edges;
    private HashMap<String, Integer> map;

    public ShortestPath(ArrayList<String> e, HashMap<String, Integer> m) {
        this.edges = e;
        this.map = m;
    } // End ShortestPath() constructor

    public Map<String, Integer> calculateShortestPath(String source) {
        Map<String, Integer> shortestDistances = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(shortestDistances::get));
        
        for (String edge : edges) {
            shortestDistances.put(edge, Integer.MAX_VALUE);
        }

        shortestDistances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visited.add(current);

            for (String neighbor : edges) {
                if (neighbor.startsWith(current)) {
                    String[] vertices = neighbor.split(" - ");
                    String next = vertices[0].equals(current) ? vertices[1] : vertices[0];
                    if (!visited.contains(next)) {
                        int weight = map.get(neighbor);
                        int newDistance = shortestDistances.get(current) + weight;
                        if (newDistance < shortestDistances.get(next)) {
                            shortestDistances.put(next, newDistance);
                            queue.add(next);
                        }
                    }
                }
            }
        }
        return shortestDistances;
    } // End calculateShortestPath() method
    
} // End ShortestPath class
