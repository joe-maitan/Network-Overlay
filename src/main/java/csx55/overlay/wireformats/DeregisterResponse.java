package csx55.overlay.wireformats;

import java.io.*;

public class DeregisterResponse implements Event {
    
    byte statusCode; // 0 for success and 1 for unsuccessful
    String additionalInfo;

    public DeregisterResponse() {} // End DeregisterResponse() default constructor

    public DeregisterResponse(boolean status, String info) {
        statusCode = (status == true) ? (byte) 0 : (byte) 1;
        additionalInfo = info; 
    } // End DeregisterResponse() constructor

    public DeregisterResponse(DataInputStream din) {
        setBytes(din);
    } // End DeregisterResponse(din) constructor

    public byte getStatus() {
        return statusCode;
    } // End getStatus() method

    public String getInfo() {
        return additionalInfo;
    } // End getInfo() method
    
    @Override
    public int getType() {
        return Protocol.DEREGISTER_RESPONSE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            
            dout.writeByte(statusCode);
            
            byte[] additionalInfoByte = additionalInfo.getBytes();
            int additionalInfoLength = additionalInfoByte.length;
            dout.writeInt(additionalInfoLength);
            dout.write(additionalInfoByte);

            dout.flush();

            marshalledBytes = baOutputStream.toByteArray();
           
            baOutputStream.close();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }

        return marshalledBytes;
    } // End getBytes() method
    
    @Override
    public void setBytes(DataInputStream din) {
        try {
            statusCode = din.readByte();
            byte[] infoStr = new byte[din.readInt()];
            din.readFully(infoStr);
            additionalInfo = new String(infoStr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes(din) method

} // End DeregisterResponse class
