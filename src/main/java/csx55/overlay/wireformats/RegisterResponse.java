package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterResponse implements Event {

    byte statusCode; // 0 for success and 1 for unsuccessful
    String additionalInfo;

    public RegisterResponse() {} // End default Register() constructor
    
    public RegisterResponse(boolean status, String info) {
        statusCode = (status == true) ? (byte) 0 : (byte) 1;
        additionalInfo = info;
    } // End RegisterResponse(status, info) constructor
    
    /* TODO: Finish the RegisterResponse buildMessage() method */
    public void buildMessage(byte status, String info) {

    } // End buildMessage() method

    @Override
    public int getType() {
        return Protocol.REGISTER_RESPONSE;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            // dout.writeInt(getType());
            dout.writeInt(statusCode);
            dout.writeChars(additionalInfo);

            marshalledBytes = baOutputStream.toByteArray();
            baOutputStream.close();

            dout.flush();
            dout.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }

        return marshalledBytes;
    } // End getBytes() method

    @Override
    public void setBytes(DataInputStream din) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBytes'");
    } // End setBytes() method


    
    
} // End RegisterResponse class
