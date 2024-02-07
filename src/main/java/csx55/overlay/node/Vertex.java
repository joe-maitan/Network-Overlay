package csx55.overlay.node;

import java.util.ArrayList;
import java.util.Random;

import csx55.overlay.node.*;
import csx55.overlay.transport.*;

public class Vertex {

    /* Every MessagingNode correlates to a Vertex. This class will keep track of a Nodes neighbors
     * and the weight on those connections
     */
    private int vertexIndex;
    private ArrayList<Vertex> neighbors = new ArrayList<>();
    private ArrayList<Integer> neighborWeights = new ArrayList<>();

    Random linkWeightGenerator = new Random();

    public Vertex(int index) {
        this.vertexIndex = index;
    } // End Link() constructor

    public int getIndex() {
        return this.vertexIndex;
    } // End getIndex() method

    public boolean addNeighbor(Vertex vertexToAdd) {
        if (!neighbors.contains(vertexToAdd)) {
            neighbors.add(vertexToAdd);
            return true;
        } else {
            return false;
        } // End if-else statements
    } // End addNeighbor(new_vertex) method

    public void addWeight(int weight) {
        this.neighborWeights.add(weight);
    } // End assignLinkWeight() method

    public boolean removeNeighbor(Vertex vertexToRemove) {
        if (neighbors.contains(vertexToRemove)) {
            neighbors.remove(vertexToRemove);
            neighborWeights.remove(neighbors.indexOf(vertexToRemove));
            return true;
        } else {
            return false;
        } // End if-else statements
    } // End removeNeighbor() method

    public Vertex getNeighbor(int indexOfNeighbor) {
        return neighbors.get(indexOfNeighbor);
    } // End getNeighbor() method

    public int getNeighborsSize() {
        return neighbors.size();
    } // End getNeighborsSize() method
    
} // End Links class
