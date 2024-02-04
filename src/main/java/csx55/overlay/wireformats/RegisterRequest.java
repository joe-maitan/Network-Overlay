package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterRequest implements Event {

    int messageType = Protocol.REGISTER_REQUEST;
    String ipAddress;
    int portNumber;
    String hostName;

    public RegisterRequest() {} // End default constructor

    public RegisterRequest(MessagingNode new_msg_node) {
        System.out.println("Inside of the RegisterRequest constructor");
        ipAddress = new_msg_node.messageNodeIP;
        portNumber = new_msg_node.messageNodePort;
        System.out.println("IP:" + ipAddress);
        System.out.println("PORT: " + portNumber);
    } // End Register() constructor

    public int getPort() {
        return this.portNumber;
    }

    public String getAddress() {
        return this.ipAddress;
    }
    
    @Override
    public int getType() {
        return this.messageType;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            dout.writeInt(portNumber);
            dout.writeChars(ipAddress);
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
            portNumber = din.readInt();
            byte[] ip_str = new byte[din.readInt()];

            din.readFully(ip_str);
            ipAddress = new String(ip_str);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method


    
} // End RegisterRequest class
