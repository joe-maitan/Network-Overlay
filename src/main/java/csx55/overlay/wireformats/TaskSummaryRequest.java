package csx55.overlay.wireformats;

import java.io.*;

public class TaskSummaryRequest implements Event {

    private boolean taskComplete;

    public TaskSummaryRequest() {} // End default constructor

    public TaskSummaryRequest(boolean value) {
        this.taskComplete = value;
    } // End TaskSummaryRequest(bool) constructor

    public TaskSummaryRequest(DataInputStream din) {
        setBytes(din);
    } // End TaskSummaryRequest(din) constructor

    @Override
    public int getType() {
        return Protocol.TASK_SUMMARY_REQUEST;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeBoolean(taskComplete);
            dout.flush();

            marshalledBytes = baOutputStream.toByteArray();
            baOutputStream.close();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        try {
            taskComplete = din.readBoolean();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes() method

} // End TaskSummaryRequest class
