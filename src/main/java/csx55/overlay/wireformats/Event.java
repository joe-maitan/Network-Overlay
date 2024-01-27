package csx55.overlay.wireformats;

public interface Event {
    
    /* these are the two methods Shrideep told us to define */
    public String getType(); // Gives us the protocol type
    public int getBytes(); // Marshalling

    public void setBytes(byte[] arr); // Unmarshalling

} // End Event interface
