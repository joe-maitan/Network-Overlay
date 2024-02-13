package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.RegisterRequest;

public class LinkWeights implements Event {

    private int numberOfLinks; /* number of neighbors the given vertex is connected to */
    private ArrayList<String> linkInfo; /* format of hostNameA:portNumA - hostNameB:portNumB - weight */
    
    private ArrayList<String> edges = new ArrayList<>();
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
        System.out.println();
        numberOfLinks = list.size();
        linkInfo = new ArrayList<>();
        
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
                    edges.add(newEdge);
                    System.out.println(newEdge + " - " + weight);

                    /* Store the value of the edge in a hash map with it being the key, and storing the weight as its corresponding value */
                    map.put(newEdge, weight); 

                    /* as a backup I am adding the weight to the end of the string and adding it an array list called linkInfo */
                    newEdge += " - " + weight;
                    linkInfo.add(newEdge);
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
            dout.writeInt(linkInfo.size());

            // TODO: Figure out how to parse the list correctly
            for (String link : linkInfo) {
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
           
            // TODO: Figure out how to parse the list correctly
            linkInfo = new ArrayList<>();
            String info = "";
            for (int i = 0; i < numberOfLinks; i++) {
                int linkLength = din.readInt();
                byte[] link = new byte[linkLength];
                din.readFully(link);
                info = new String(link);
                linkInfo.add(info);
            }
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method
    public static void main(String[] commandLineArgs) {
        ArrayList<Vertex> vertices = new ArrayList<>();

        RegisterRequest r1 = new RegisterRequest("Joe", 72);
        RegisterRequest r2 = new RegisterRequest("Craig", 0325);
        RegisterRequest r3 = new RegisterRequest("Mitch", 1215);

        Vertex v1 = new Vertex(0, r1);
        Vertex v2 = new Vertex(0, r2);
        Vertex v3 = new Vertex(0, r3);

        v1.addNeighbor(v2);
        v1.addNeighbor(v3);

        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        LinkWeights test = new LinkWeights(vertices);
        byte[] arr = test.getBytes();
        ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

        int msg_type = 0;

        try {
            msg_type = din.readInt();
            System.out.println("Successfully read msg_type: " + msg_type);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        LinkWeights other = new LinkWeights(din);

        System.out.println(test.getType() == msg_type);

        System.out.println(test.getEdges().get(0));
        System.out.println(other.getEdges().size());

    } // End main method

} // End LinkWeights class