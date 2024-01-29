package csx55.overlay.wireformats;

public interface Event {
    
    /* these are the two methods Shrideep told us to define */
    public int getType(); // Gives us the protocol type
    public byte[] getBytes(); // Marshalling
    public void setBytes(byte[] arr); // Unmarshalling

} // End Event interface
