package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterResponse implements Event {

    String IP_address;
    int port_number;
    int message_protocol = Protocol.REGISTER_RESPONSE;

    public RegisterResponse() {} // End Register() constructor
    
    @Override
    public int getType() {
        return this.message_protocol;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(port_number);
            dout.writeChars(IP_address);

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
