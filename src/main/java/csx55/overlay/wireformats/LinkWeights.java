package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class LinkWeights implements Event {

    private int numberOfLinks; /* number of neighbors the given vertex is connected to */
    private ArrayList<String> linkInfo; /* format of hostNameA:portNumA - hostNameB:portNumB - weight */
    ArrayList<String> edges = new ArrayList<>();
    private HashMap<String, Integer> map = new HashMap<>();

    private int weightOfLink;
    private Random weightGenerator = new Random();

    public HashMap<String, Integer> getEdges() {
        return this.map;
    } // End getEdges() method

    public LinkWeights() {} // End default constructor

    public LinkWeights(ArrayList<Vertex> list) {
        numberOfLinks = list.size();
        linkInfo = new ArrayList<>();
        
        System.out.println("Entering the LinkWeights constructor");
        System.out.println("# of Links: " + numberOfLinks);
        System.out.println();
        
        String currVector = "";
        String neighborVector = "";
        String key = "";
        for (Vertex curr : list) {
            for (Vertex neigh : curr.getNeighbors()) {
                currVector = curr.getRegisterRequest().getAddress() + ":" + curr.getRegisterRequest().getPort();
                neighborVector = neigh.getRegisterRequest().getAddress() + ":" + neigh.getRegisterRequest().getPort();

                if (currVector.compareTo(neighborVector) <= 0) {
                    key = currVector + " - " +  neighborVector;
                } else {
                    key = neighborVector + " - " +  currVector;
                } // End if-else statement

                if (!edges.contains(key)) {
                    edges.add(key);
                    // System.out.println(key);
                } // End if statement
            } // End for each loop      
        } // End for each loop

        // System.out.println("Adding weight to the edges");

        // int prevWeight = 0;
        for (String link : edges) {
            int weight = weightGenerator.nextInt(10) + 1;
            // while (weight != prevWeight) { weight = weightGenerator.nextInt(10) + 1; }
            
            link += " - " + weight;
            System.out.println(link);

            // prevWeight = weight;
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

            // Serialize linkInfo
            for (String link : linkInfo) {
                dout.writeUTF(link);
            } // End for each loop
            
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
           // Deserialize linkInfo
            linkInfo = new ArrayList<>();
            for (int i = 0; i < numberOfLinks; i++) {
                linkInfo.add(din.readUTF());
            }
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method
    public static void main(String[] commandLineArgs) {

    } // End main method

} // End LinkWeights class