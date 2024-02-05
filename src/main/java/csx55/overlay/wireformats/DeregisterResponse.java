package csx55.overlay.wireformats;

import java.io.DataInputStream;
import java.io.IOException;

public class DeregisterResponse implements Event {
    
    byte statusCode;
    String additionalInfo;

    public DeregisterResponse() {} // End DeregisterResponse() default constructor

    public DeregisterResponse(byte status, String info) {
        statusCode = status;
        additionalInfo = info;
    } // End DeregisterResponse() constructor
    
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
    
} // End DeregisterResponse class
