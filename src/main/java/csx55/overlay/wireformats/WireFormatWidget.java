package csx55.overlay.wireformats;

import java.io.*;
import java.net.*;

public class WireFormatWidget implements Event {

    /* THIS IS AN EXAMPLE OF WHAT THE FORMAT WOULD LOOK LIKE */

    private int type;
    private long timestamp;
    private String identifier;
    private int tracker;

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
    } // End WireFormatWidget() constructor

    public byte[] getBytes() { /* marshalls/encodes our message */
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
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
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
        
        return marshalledBytes;
    } // End getBytes() method

    @Override
    public int getType() {
        return this.type;
    } // End getType() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    }

} // End WireFormatWidget class
