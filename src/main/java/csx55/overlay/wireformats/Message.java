package csx55.overlay.wireformats;

import java.io.*;

public class Message implements Event {

    // Source Node (From)
    // Sink Node (To)

    @Override
    public int getType() {
        return Protocol.MESSAGE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    }

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    }
    
} // End Message class
