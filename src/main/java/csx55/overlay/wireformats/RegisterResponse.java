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

    public RegisterResponse(DataInputStream din) {
        setBytes(din);
    } // End RegisterResponse(din) constructor

    public byte getStatus() {
        return statusCode;
    } // End getStatus() method

    public String getInfo() {
        return additionalInfo;
    } // End getInfo() method
    
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

    // public static void main(String[] args) {
    //     RegisterResponse rsp = new RegisterResponse(true, "Life is good");
    //     // RegisterResponse rsp_two = new RegisterResponse(false, "Live is still good");
    //     byte[] arr = rsp.getBytes();
    //     ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
    //     DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

    //     int msg_type = 0;
    //     try {
    //         msg_type = din.readInt();
    //     } catch (Exception err) {
    //         System.err.println(err.getMessage());
    //     }

    //     RegisterResponse other = new RegisterResponse(din);

    //     if (rsp.getType() == msg_type && rsp.getStatus() == other.getStatus() && rsp.getInfo().equals(other.getInfo())) {
    //         System.out.println("RegisterResponse - Success");
    //         // System.out.println(msg_type);
    //         // System.out.println(rsp.getType());

    //         // System.out.println(rsp.getStatus());
    //         // System.out.println(other.getStatus());

    //         // System.out.println(rsp.getInfo());
    //         // System.out.println(other.getInfo());
    //     } else {
    //         System.out.println("RegisterResponse - Failure");
    //         // System.out.println(msg_type);
    //         // System.out.println(rsp.getType());

    //         // System.out.println(rsp.getStatus());
    //         // System.out.println(other.getStatus());

    //         // System.out.println(rsp.getInfo());
    //         // System.out.println(other.getInfo());
    //     }
    // }

} // End RegisterResponse class
