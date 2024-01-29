package csx55.overlay.wireformats;

import java.io.*;
import java.net.*;

public class WireFormatWidget {

    private int type;
    private long timestamp;
    private String identifier;
    private int tracker;

    public byte[] getBytes() throws IOException implements Event { /* marshalls/encodes our message */
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        dout.writeInt(type);
        dout.writeLong(timestamp);

        byte[] identifierBytes = identifier.getBytes();
        int elementLength = identifierBytes.length;
        dout.writeInt(elementLength);
        dout.write(identifierBytes);

        dout.writeInt(tracker);

        dout.flush();
        marshalledBytes = baOutputStream.toByteArray();
        baOutputStream.close();
        dout.close();
        return marshalledBytes;
    } // End getBytes() method

    /* reads in the message from the marshalledBytes array and creates a WireFormatWidget object */
    public WireFormatWidget(byte[] marshalledBytes) throws IOException { /* unmarshalls our byte array */
        ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));
        
        type = din.readInt();
        timestamp = din.readLong();
    
        int identifierLength = din.readInt();
        byte[] identifierBytes = new byte[identifierLength];
        din.readFully(identifierBytes);
        identifier = new String(identifierBytes);
    
        tracker = din.readInt();
        baInputStream.close();
        din.close();
    } // End unmarshalling() method
    
} // End WireFormatWidget class
