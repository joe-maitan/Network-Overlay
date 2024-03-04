package csx55.overlay.transport;

import java.io.*;
import java.net.*;

import csx55.overlay.node.*;

public class TCPSender {

    private Socket socket;
    private DataOutputStream dout;
    private int index;
    private Node referenceNode;

    public DataOutputStream getDout() {
        return this.dout;
    } // End getDout() method
    
    public TCPSender(Socket s, int array_list_index, Node node) {
        try {
            this.socket = s; /* this is going to the socket that we are sending data to */
            this.dout = new DataOutputStream(socket.getOutputStream());
            this.index = array_list_index;
            this.referenceNode = node;
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End TCPSender(socket) constructor

    public void sendData(byte[] arr) {
        int dataLength = 0;
        
        try {
            dataLength = arr.length;
            getDout().writeInt(dataLength);
            getDout().write(dataToSend, 0, dataLength);
            getDout().flush();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End sendData(data) method

} // End TCPSender class
