package csx55.overlay.wireformats;

import java.io.IOException;

public interface Event {
    
    /* these are the two methods Shrideep told us to define */
    public int getType(); // Gives us the protocol type
    public byte[] getBytes() throws IOException; // Marshalling
    
    /* Dan told me to implement this method. This allows us to parse in a byte array arr */
    public void setBytes(byte[] arr); // Unmarshalling

} // End Event interface
