package csx55.overlay.wireformats;

import java.io.*;

public interface Event {
    
    /* these are the two methods Shrideep told us to define */
    public int getType();
    public byte[] getBytes() throws IOException; // Marshalling
    
    /* Dan told me to implement this method. This allows us to parse in a byte array arr */
    public void setBytes(DataInputStream din); // Unmarshalling
} // End Event interface
