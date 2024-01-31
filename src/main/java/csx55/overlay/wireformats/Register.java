package csx55.overlay.wireformats;
import csx55.overlay.node.*;

public class Register implements Event {

    // We marshall these fields
    int message_type;
    String IP_address;
    int port_number;

    public Register(MessagingNode new_msg_node) {
        this.IP_address = new_msg_node.mn_ip_address;
        this.port_number = new_msg_node.mn_port_number;
        // this.message_type = Protocol.REGISTER;
    } // End Register() constructor
    
    @Override
    public int getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    }
    @Override
    public void setBytes(byte[] arr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    }


} // End Register class
