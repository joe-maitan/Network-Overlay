package csx55.overlay.wireformats;

public class Register implements Event {

    // We marshall these fields
    int message_type;
    String IP_address;
    int port_number;

    // public void register_node() {

    // } // End register_node() method

    // public void deregister_node() {

    // } // End deregister_node() method

    public String getType(){
        String s = "";
        
        return s;
    } // Gives us the protocol type

    public int getBytes(){
        return 0;
    } // Marshalling

    public void setBytes(byte[] arr){

    } // Unmarshalling
    
} // End Register class
