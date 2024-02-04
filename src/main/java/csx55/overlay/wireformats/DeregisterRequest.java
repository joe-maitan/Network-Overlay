package csx55.overlay.wireformats;

import java.io.*;

public class DeregisterRequest implements Event {

    int message_type = Protocol.DEREGISTER_REQUEST;
    String node_ip_Address;
    int node_port_number;
    
    @Override
    public int getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }
    @Override
    public byte[] getBytes() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    }
    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    }

    
    
} // End DeregisterRequest class
