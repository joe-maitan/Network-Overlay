package csx55.overlay.wireformats;

import java.io.*;

public class TaskSummaryResponse implements Event {

    private String nodeIPAddress;
    private int nodePortNumber;
    
    private int numberOfMsgsSent;
    private int sumOfMsgsSent;

    private int numberOfMsgsReceived;
    private int sumOfMsgsReceived;

    private int numberOfMsgsRelayed;

    public TaskSummaryResponse() {} // End default constructor

    public TaskSummaryResponse(RegisterRequest node, int[] msgs) {
        this.nodeIPAddress = node.getAddress();
        this.nodePortNumber = node.getPort();
        
        this.numberOfMsgsSent = msgs[0];
        this.sumOfMsgsSent = msgs[1];

        this.numberOfMsgsReceived = msgs[2];
        this.sumOfMsgsReceived = msgs[3];

        this.numberOfMsgsRelayed = msgs[4];
    } // End TaskSummaryResponse(node, int[]) constructor

    public TaskSummaryResponse(DataInputStream din) {
        setBytes(din);
    } // End TaskSummaryResponse(din) constructor

    @Override
    public int getType() {
        return Protocol.TASK_SUMMARY_RESPONSE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());

            byte[] ipAddressByte = nodeIPAddress.getBytes();
            int ipAddressLength = ipAddressByte.length;
            dout.writeInt(ipAddressLength);
            dout.write(ipAddressByte);
            dout.writeInt(nodePortNumber);

            dout.writeInt(numberOfMsgsSent);
            dout.writeInt(sumOfMsgsSent);
            
            dout.writeInt(numberOfMsgsReceived);
            dout.writeInt(sumOfMsgsReceived);
            
            dout.writeInt(numberOfMsgsRelayed);
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
            byte[] ipStr = new byte[din.readInt()];
            din.readFully(ipStr);
            nodeIPAddress = new String(ipStr);
            nodePortNumber = din.readInt();
            
            numberOfMsgsSent = din.readInt();
            sumOfMsgsSent = din.readInt();

            numberOfMsgsReceived = din.readInt();
            sumOfMsgsReceived = din.readInt();

            numberOfMsgsRelayed = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End setBytes() method

    public static void main(String[] args) {
        RegisterRequest req = new RegisterRequest("Joe", 72);
        int[] nums = {1, 2, 3, 4, 5};
        TaskSummaryResponse rsp = new TaskSummaryResponse(req, nums);
        byte[] arr = req.getBytes();

        ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

        int msg_type = 0;
        try {
            msg_type = din.readInt();
            System.out.println("Successfully read in msg_type = " + msg_type);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        TaskSummaryResponse temp = new TaskSummaryResponse(din);

        if (rsp.getType() == msg_type) {
            System.out.println("Msg type parsed in correctly");
        }
    } // End main method

} // End TaskSummaryResponse class
