package csx55.overlay.wireformats;

import java.io.IOException;

public class Deregister implements Event {

    int message_type;
    String IP_address;
    int port_number;
    
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
    public void setBytes(byte[] arr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    }

    
} // End Deregister class
