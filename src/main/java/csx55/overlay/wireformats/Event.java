package csx55.overlay.wireformats;

import java.io.IOException;

public interface Event {
    
    /* these are the two methods Shrideep told us to define */
    public int getType(); // TODO: Do we return an int or the protocol of the message type?
    public byte[] getBytes() throws IOException; // Marshalling
    
    /* Dan told me to implement this method. This allows us to parse in a byte array arr */
    public void setBytes(byte[] arr); // Unmarshalling

} // End Event interface
