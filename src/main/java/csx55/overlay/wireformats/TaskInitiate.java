package csx55.overlay.wireformats;

import java.io.*;

public class TaskInitiate implements Event {

    int rounds;

    @Override
    public int getType() {
        return Protocol.TASK_INITIATE;
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

} // End TaskInitiate class
