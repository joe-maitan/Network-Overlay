package csx55.overlay.wireformats;

import java.io.*;

public class DeregisterRequest implements Event {
    
    public String hostName; // The nodes machine name
    public int portNumber; // The nodes port number

    public DeregisterRequest() {} // End DeregisterRequest() default constructor

    public DeregisterRequest(String host, int port){
        hostName = host;
        port = portNumber;
    } // End DeregisterRequest() constructor
    
    @Override
    public int getType() {
        return Protocol.DEREGISTER_REQUEST;
    } // End getType() method

    @Override
    public byte[] getBytes() throws IOException {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            
            byte[] hostNameByte = hostName.getBytes();
            int hostNameLength = hostNameByte.length;
            dout.writeInt(hostNameLength);
            dout.write(hostNameByte);

            dout.write(portNumber);

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
            byte[] hostNameStr = new byte[din.readInt()];
            din.readFully(hostNameStr);
            hostName = new String(hostNameStr);

            portNumber = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method
    
} // End DeregisterRequest class
