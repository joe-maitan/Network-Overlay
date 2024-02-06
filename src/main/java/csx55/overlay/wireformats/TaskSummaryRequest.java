package csx55.overlay.wireformats;

import java.io.*;

public class TaskSummaryRequest implements Event {

    @Override
    public int getType() {
        return Protocol.TASK_SUMMARY_REQUEST;
    } // End getType() method

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

} // End TaskSummaryRequest class
