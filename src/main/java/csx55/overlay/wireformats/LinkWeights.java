package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class LinkWeights implements Event {

    private int numberOfLinks; /* number of neighbors the given vertex is connected to */
    private ArrayList<String> edges = new ArrayList<>(); /* format of hostNameA:portNumA - hostNameB:portNumB - weight */
    private HashMap<String, Integer> map = new HashMap<>();

    private Random weightGenerator = new Random();

    public HashMap<String, Integer> getMap() {
        return this.map;
    } // End getEdges() method

    public ArrayList<String> getEdges() {
        return this.edges;
    } // End getEdges() method

    public LinkWeights() {} // End default constructor

    public LinkWeights(ArrayList<Vertex> list) {
        numberOfLinks = list.size();

        String currVector = "";
        String neighborVector = "";
        String newEdge = "";
        
        for (Vertex curr : list) {
            for (Vertex neigh : curr.getNeighbors()) {
                currVector = curr.getRegisterRequest().getAddress() + ":" + curr.getRegisterRequest().getPort();
                neighborVector = neigh.getRegisterRequest().getAddress() + ":" + neigh.getRegisterRequest().getPort();

                if (currVector.compareTo(neighborVector) <= 0) {
                    newEdge = currVector + " - " +  neighborVector;
                } else {
                    newEdge = neighborVector + " - " +  currVector;
                } // End if-else statement

                if (!edges.contains(newEdge)) {
                    int weight = weightGenerator.nextInt(10) + 1;
                    map.put(newEdge, weight); /* Store the value of the edge in a hash map with it being the key, and storing the weight as its corresponding value */
                    edges.add(newEdge);
                    // System.out.println(newEdge + " - " + weight);
                } // End if statement
            } // End for each loop      
        } // End for each loop
    } // End LinkWeights(numLinks, list) constructor

    public LinkWeights(DataInputStream din) {
        setBytes(din);
    } // End LinkWeights(din) constructor

    @Override
    public int getType() {
        return Protocol.LINK_WEIGHTS;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(edges.size());

            for (String link : edges) {
                byte[] linkInfoByte = link.getBytes();
                int linkInfoLength = linkInfoByte.length;
                dout.writeInt(linkInfoLength);
                dout.write(linkInfoByte);
            }
            
            dout.flush();
            marshalledBytes = baOutputStream.toByteArray();

            baOutputStream.close();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        try {
            numberOfLinks = din.readInt();
    
            edges = new ArrayList<>();
            String info = "";
            for (int i = 0; i < numberOfLinks; i++) {
                int linkLength = din.readInt();
                byte[] link = new byte[linkLength];
                din.readFully(link);
                info = new String(link);
                edges.add(info);
            } // End for loop
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method
    
} // End LinkWeights class