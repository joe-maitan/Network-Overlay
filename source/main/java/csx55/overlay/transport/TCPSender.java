package csx55.overlay;

import java.io.*;
import java.net.*;

public class TCPSender implements Runnable {

    private Socket socket;
    private DataOutputStream data_out;

    public TCPSender(Socket socket) throws IOException {
        this.socket = socket;
        data_out = new DataOutputStream(data_out);
    } // End TCPSender(socket) constructor

    public void run() {

    } // End run() method
    
} // End TCPSender class
