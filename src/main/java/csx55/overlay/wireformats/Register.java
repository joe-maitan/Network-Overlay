package csx55.overlay.wireformats;

public class Register implements Event {

    // We marshall these fields
    int message_type;
    String IP_address;
    int port_number;

    public String getType(){
        String s = "";
        
        return s;
    } // Gives us the protocol type

    public int getBytes() { // Marshalling
        return 0;
    } // End getBytes() method

    public void setBytes(byte[] arr) { // Unmarshalling

    } // End setBytes() method
    
} // End Register class
