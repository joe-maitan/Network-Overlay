package csx55.overlay.wireformats;

import java.io.*;

public interface Event {
    
    public int getType(); // Gets the message protocol
    public byte[] getBytes() throws IOException; // Encodes the data for transmission (Marshalling)
    public void setBytes(DataInputStream din); // Decodes the data from the transmission (Unmarshalling)
    
} // End Event interface
