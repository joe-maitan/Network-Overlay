package csx55.overlay.node;

import java.util.ArrayList;
import java.util.Random;

import csx55.overlay.node.*;
import csx55.overlay.transport.*;

public class Vertex {

    /* Every MessagingNode is going to have a Vertex or Vertices. This class will keep track of a Nodes neighbors
     * and the weight on those connections
     */
    private int socketIndex;
    private ArrayList<Vertex> neighbors = new ArrayList<>();
    private ArrayList<Integer> neighborWeights = new ArrayList<>();
    private int linkWeight;

    Random linkWeightGenerator = new Random();

    public Vertex(int s_index) {
        this.socketIndex = s_index;
    } // End Link() constructor

    public int getIndex() {
        return this.socketIndex;
    } // End getIndex() method

    public boolean addNeighbor(Vertex vertexToAdd) {
        if (!neighbors.contains(vertexToAdd)) {
            neighbors.add(vertexToAdd);
            return true;
        } else {
            return false;
        } // End if-else statements
    } // End addNeighbor(new_vertex) method

    public void assignLinkWeight() {
        this.linkWeight = linkWeightGenerator.nextInt(10) + 1;
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
    
} // End Links class
