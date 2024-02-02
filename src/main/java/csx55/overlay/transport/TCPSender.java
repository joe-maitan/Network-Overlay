package csx55.overlay.transport;

import java.io.*;
import java.net.*;

public class TCPSender {

    private Socket socket;
    private DataOutputStream dout;
    public int index;
    
    public TCPSender(Socket s, int array_list_index) throws IOException {
        this.socket = s; /* this is going to the socket that we are sending data to */
        dout = new DataOutputStream(socket.getOutputStream());
        this.index = array_list_index;
    } // End TCPSender(socket) constructor

    public void sendData(byte[] dataToSend) throws IOException {
        int dataLength = dataToSend.length;

        System.out.println("TCPSender.java - dataToSend.length: " + dataToSend.length);
        System.out.println("TCPSender.java - dataLength: " + dataLength);

        dout.writeInt(dataLength);
        dout.write(dataToSend, 0, dataLength);
        dout.flush();
    } // End sendData(data) method

} // End TCPSender class
