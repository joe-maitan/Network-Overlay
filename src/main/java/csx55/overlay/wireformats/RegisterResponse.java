package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterResponse implements Event {

    

    int messageType;
    String ipAddress;
    // int portNumber;
    byte statusCode;
    String additionalInfo;


    public RegisterResponse() {} // End Register() constructor
    
    // public int getPort() {
    //     return this.portNumber;
    // } // End getPort() method

    public String getAddress() {
        return this.ipAddress;
    } // End getAddress()

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
            // dout.writeChars(IP_address);

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
