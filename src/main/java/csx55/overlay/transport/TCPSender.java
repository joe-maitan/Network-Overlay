package csx55.overlay;

import java.io.*;
import java.net.*;

public class TCPSender {

    // private Socket socket;
    // private DataOutputStream data_out;

    public TCPSender() throws IOException {
    } // End TCPSender(socket) constructor

    // public void send(Socket new_socket, byte[] msg) {

    //     data_out = new DataOutputStream(new_socket.getData);
    //     data_out.send(msg);
    //     data_out.flush();
    // } // End send() method

    public void send_data(Socket s, byte[] arr) {
        // get the output stream of s
        // to send that byte array over that socket
    } // End send_data() method

} // End TCPSender class
