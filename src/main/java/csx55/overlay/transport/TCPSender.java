package csx55.overlay.transport;

import java.io.*;
import java.net.*;

public class TCPSender {

    private Socket socket;
    private DataOutputStream dout;
    
    public TCPSender(Socket socket) throws IOException {
        this.socket = socket; /* this is going to the socket that we are sending data to */
        dout = new DataOutputStream(socket.getOutputStream());
    } // End TCPSender(socket) constructor

    public void sendData(byte[] dataToSend) throws IOException {
        int dataLength = dataToSend.length;
        dout.writeInt(dataLength);
        dout.write(dataToSend, 0, dataLength);
        dout.flush();
    } // End sendData(data) method

} // End TCPSender class
