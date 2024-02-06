package csx55.overlay.wireformats;

import java.io.*;

public class TaskSummaryResponse implements Event {

    int nodeIPAddress;
    int nodePortNumber;
    
    int numberOfMsgsSent;
    int sumOfMsgsSent;

    int numberOfMsgsReceived;
    int sumOfMsgsReceived;

    int numberOfMsgsRelayed;

    @Override
    public int getType() {
        return Protocol.TASK_SUMMARY_RESPONSE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBytes'");
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes() method

} // End TaskSummaryResponse class
