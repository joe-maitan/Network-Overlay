package csx55.overlay.node;

import java.util.ArrayList;
import java.util.Random;

import csx55.overlay.node.*;
import csx55.overlay.transport.*;
import csx55.overlay.wireformats.RegisterRequest;

public class Vertex {

    /* Every MessagingNode correlates to a Vertex. This class will keep track of a Nodes neighbors
     * and the weight on those connections
     */
    private int vertexIndex; /* where it is in our vertices ArrayList in Registry.java */
    private ArrayList<Vertex> neighbors = new ArrayList<>();
    private ArrayList<Integer> neighborWeights = new ArrayList<>();

    private RegisterRequest registerRequest;

    Random linkWeightGenerator = new Random();

    public Vertex(int index, RegisterRequest msgNodeRegisterRequest) {
        this.vertexIndex = index;
        this.registerRequest = msgNodeRegisterRequest;
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

    public int getWeight(int search) {
        return this.neighborWeights.get(search);
    } // End getWeight() method

    public boolean removeNeighbor(Vertex vertexToRemove) {
        if (neighbors.contains(vertexToRemove)) {
            this.neighbors.remove(vertexToRemove);
            this.neighborWeights.remove(neighbors.indexOf(vertexToRemove));
            return true;
        } else {
            return false;
        } // End if-else statements
    } // End removeNeighbor() method

    public Vertex getNeighbor(int indexOfNeighbor) {
        return this.neighbors.get(indexOfNeighbor);
    } // End getNeighbor() method

    public int getNeighborsSize() {
        return this.neighbors.size();
    } // End getNeighborsSize() method

    public boolean hasNeighbor(int vertexIndex) {
        for (Vertex search : this.neighbors) {
            if (search.getIndex() == vertexIndex) {
                return true;
            }
        } // End for-each loop

        return false;
    } // End isNeighbor() method

    public RegisterRequest getRegisterRequest() {
        return this.registerRequest;
    } // End getRegisterRequest() method

    public ArrayList<Vertex> getNeighbors() {
        return this.neighbors;
    }
    
} // End Links class
