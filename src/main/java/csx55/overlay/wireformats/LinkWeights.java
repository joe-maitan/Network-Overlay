package csx55.overlay.wireformats;

import java.io.*;
import java.util.*;

import csx55.overlay.node.*;

public class LinkWeights implements Event {

    private int numberOfLinks; // number of neighbors the given vertex is connected to
    private ArrayList<Vertex> vertices;

    public LinkWeights() {} // End default constructor

    public LinkWeights(int numLinks, ArrayList<Vertex> list) {
        numberOfLinks = numLinks;
        vertices = new ArrayList<>();

        for (Vertex v : list) {
            vertices.add(v);
        } // End for-each loop
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
            dout.writeInt(numberOfLinks);

            // for (Vertex v : vertices) {
            //     byte[] vertexByte = v.getBytes();
            //     int vertexLength = vertexByte.length;

            //     dout.writeInt(vertexLength);
            //     dout.write(vertexByte);
            // } // End for each loop

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