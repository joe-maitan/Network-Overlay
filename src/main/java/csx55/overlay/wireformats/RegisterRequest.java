package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterRequest implements Event {

    String ipAddress;
    int portNumber;

    public RegisterRequest() {} // End default constructor

    /* This constructor servers as our setBytes() method */
    public RegisterRequest(String ip, int port) {
        ipAddress = ip;
        portNumber = port;
    } // End Register() constructor

    public RegisterRequest(DataInputStream din) {
        setBytes(din);
    } // End RegisterRequest

    public int getPort() {
        return this.portNumber;
    } // End getPort() method

    public String getAddress() {
        return this.ipAddress;
    } // End getAddress() method
    
    @Override
    public int getType() {
        return Protocol.REGISTER_REQUEST;
    } // End getType() method

    @Override
    public byte[] getBytes() {
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        try {
            dout.writeInt(getType());
            
            byte[] ipAddressByte = ipAddress.getBytes();
            int ipAddressLength = ipAddressByte.length;
            dout.writeInt(ipAddressLength);
            dout.write(ipAddressByte);
            
            dout.writeInt(portNumber);
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
            byte[] ip_str = new byte[din.readInt()];
            din.readFully(ip_str);
            ipAddress = new String(ip_str);
            portNumber = din.readInt();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End setBytes() method

    public static void main(String[] args) {
        RegisterRequest rq = new RegisterRequest("joe", 72);
        byte[] arr = rq.getBytes(); /* marshalling the data */
        ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));
        
        int msg_type = 0;
        try{
            msg_type = din.readInt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        RegisterRequest other = new RegisterRequest(din); /* unmarshalling */
        
        if (rq.getType() == msg_type && rq.ipAddress.equals(other.ipAddress) && rq.portNumber == other.portNumber) {
            System.out.println("RegisterReqest - Success");
        } else{
            System.out.println("RegisterRequest - Unsuccessful");
        }

    }

} // End RegisterRequest class
