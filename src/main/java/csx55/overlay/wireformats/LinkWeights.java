package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class LinkWeights implements Event {

    private int numberOfLinks; /* number of neighbors the given vertex is connected to */
    private ArrayList<String> linkInfo; /* format of hostNameA:portNumA - hostNameB:portNumB - weight */
    private int weightOfLink;

    private Random weightGenerator = new Random();

    public LinkWeights() {} // End default constructor

    public LinkWeights(ArrayList<Vertex> list) {
        linkInfo = new ArrayList<>();
        System.out.println("Is list empty: " + (list.size() == 0));
        // this.numberOfLinks = list.size();
        System.out.println("LinkWeights constructor: numberOfLinks = " + list.size());

        for (int i = 0; i < list.size(); ++i) {
            Vertex curr = list.get(i);
            Vertex neighbor = list.get(i + 1);

            String info;
            if (curr.hasNeighbor(curr.getNeighbors().indexOf(neighbor)) && neighbor.hasNeighbor(neighbor.getNeighbors().indexOf(curr))) {
                int weight = weightGenerator.nextInt(10) + 1;

                info = curr.getRegisterRequest().ipAddress + ":" + curr.getRegisterRequest().portNumber + " - " 
                    + neighbor.getRegisterRequest().ipAddress + ":" + neighbor.getRegisterRequest().portNumber 
                    + " - " + weight;

                System.out.println(info);

                linkInfo.add(info);
            }   
        }
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
         
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method

    public static void main(String[] commandLineArgs) {

    } // End main method

} // End LinkWeights class