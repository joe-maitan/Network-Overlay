package csx55.overlay;

import java.io.*;
import java.net.*;

public class TCPSender implements Runnable {

    private Socket socket;
    private DataOutputStream data_out;

    public TCPSender(Socket socket) throws IOException {
        this.socket = socket;
        
    } // End TCPSender(socket) constructor

    public void send(Socket new_socket, byte[] msg) {

        data_out = new DataOutputStream(new_socket.getData);
        data_out.send(msg);
        data_out.flush();
    } // End send() method
    
} // End TCPSender class
