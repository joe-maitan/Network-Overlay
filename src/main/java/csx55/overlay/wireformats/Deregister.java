package csx55.overlay.wireformats;

public class Deregister implements Event {

    int message_type;
    String IP_address;
    int port_number;

    public String getType(){
        String s = "";
        
        return s;
    } // Gives us the protocol type

    public int getBytes(){
        return 0;
    } // Marshalling

    public void setBytes(byte[] arr){

    } // Unmarshalling
    
} // End Deregister class
