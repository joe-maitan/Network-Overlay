package csx55.overlay.util;

import java.util.Random;

import csx55.overlay.node.Vertex;

public class OverlayCreator {

    // public void construct_overlay(int numberOfConnections, int numberOfRegisteredNodes) {
    // /* This is what connects the other MessagingNodes to one another, given a list of other messaging node sockets, they would connect to each. */
    //     /* start at the first messaging node socket */
    //     Vertex currVertex = new Vertex(0);
    //     Random linkWeightGenerator = new Random();

    //     /* For each vertex, create neighbors for the vertices */
    //     for (int j = 1; j < numberOfRegisteredNodes; ++j) {
    //         int weight = linkWeightGenerator.nextInt(10) + 1;
           
    //         Vertex neighborVertex = new Vertex(j);
    //         currVertex.addNeighbor(neighborVertex);
    //         neighborVertex.addNeighbor(currVertex);
            
    //         currVertex.addWeight(weight);
    //         neighborVertex.addWeight(weight);
            
    //         vertices.add(currVertex);
    //         currVertex = neighborVertex;
    //     } // End for loop

    //     for (int i = 0; i < numberOfConnections; ++i) {
    //         if (currVertex.getNeighborsSize() != numberOfConnections) {
    //             int numberOfNeighborsNeeded = numberOfConnections - currVertex.getNeighborsSize();

    //             for (int k = 0; k < numberOfNeighborsNeeded; ++k) {
    //                 Vertex newNeighbor = vertices.get(k);
    //                 currVertex.addNeighbor(newNeighbor);
    //             } // End for loop
    //         } // End for loop
    //     } // End for loop
    
    // End construct_overlay
    
} // End OverlayCreator class
