package csx55.overlay.wireformats;

public interface Event {
    
    public String getType(); // Gives us the protocol type
    public int getBytes(); // Marshalling
    public int setBytes(byte[] arr); // Unmarshalling

} // End Event interface
