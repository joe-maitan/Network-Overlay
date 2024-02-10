package csx55.overlay.wireformats;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;

public class LinkWeights implements Event {

    // Number of links
    // link info 1
    // link info 2
    // ...
    // link info 3

    @Override
    public int getType() {
        return Protocol.LINK_WEIGHTS;
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

    
    
} // End LinkWeights class