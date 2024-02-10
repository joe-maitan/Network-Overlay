package csx55.overlay.wireformats;

import java.io.*;

public class TaskSummaryRequest implements Event {

    private boolean taskComplete;

    public TaskSummaryRequest() {}

    public TaskSummaryRequest(boolean value) {
        this.taskComplete = value;
    }

    public TaskSummaryRequest(DataInputStream din) {
        setBytes(din);
    }

    @Override
    public int getType() {
        return Protocol.TASK_SUMMARY_REQUEST;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes() method

} // End TaskSummaryRequest class
