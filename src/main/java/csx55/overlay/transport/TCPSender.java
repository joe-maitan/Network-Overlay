package csx55.overlay.transport;

import java.io.*;
import java.net.*;

public class TCPSender {

    private Socket sender_socket;
    private DataOutputStream data_out;

    public TCPSender(Socket s) throws IOException {
        sender_socket = s;
        data_out = new DataOutputStream(sender_socket.getOutputStream());
    } // End TCPSender(socket) constructor

    public void send_data(Socket new_socket, byte[] arr) {
        // data_out = new DataOutputStream(new_socket.getOutputStream());
        // data_out.send(arr);
        // data_out.flush();
    } // End send_data() method

} // End TCPSender class
