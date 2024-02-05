package csx55.overlay.wireformats;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class RegisterRequest implements Event {

    
    String ipAddress;
    int portNumber;
    int messageType = Protocol.REGISTER_REQUEST;

    public RegisterRequest() {} // End default constructor

    /* This constructor servers as our setBytes() method */
    public RegisterRequest(MessagingNode new_msg_node) {
        /* TODO: Figure out how to make the constructor for each messagingWidget
         * equivalent to the setBytes() method
         */

        // Validating the RegisterRequest was getting the right information from the new_msg_node
        // System.out.println("Constructing a new register request object");
        // System.out.println("[RegisterRequest]: IP: " + new_msg_node.msgNodeName);
        // System.out.println("[RegisterRequest]: Port: " + new_msg_node.msgNodePortNumber);

        try{
            DataInputStream din = new DataInputStream(new_msg_node.messaging_node_socket.getInputStream());
            setBytes(din);
        } catch (IOException err){
            System.err.println(err.getMessage());
        } // End try-catch block
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
            dout.writeChars(ipAddress);
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
        // TODO Figure out what order the data is sending
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
