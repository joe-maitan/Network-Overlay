package csx55.overlay.wireformats;

import java.io.*;

public class TaskInitiate implements Event {

    private int numberOfRounds;

    public TaskInitiate() {} // End default constructor

    public TaskInitiate(int rounds) {
        numberOfRounds = rounds;
    } // End TaskInitaite(rounds) constructor

    public TaskInitiate(DataInputStream din) {
        setBytes(din);
    } // End TaskInitiate(din) constructor

    public int getNumRounds() {
        return this.numberOfRounds;
    } // End getNumRounds() method

    @Override
    public int getType() {
        return Protocol.TASK_INITIATE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(numberOfRounds);
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
            numberOfRounds = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes(din) method

} // End TaskInitiate class
