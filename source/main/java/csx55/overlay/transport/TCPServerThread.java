package csx55.overlay;

import java.io.*;
import java.net.*;

public class TCPServerThread {

    static ServerSocket our_server;

    public void run() {
        initialize_server();
    }

    public static void initialize_server() {
        int port_num = 1025;
        while () {
            try {
                our_server = new ServerSocket(port_num);
            } catch (SocketException e) {
                System.err.println(e.getMessage());
            } // End try-catch block
        } // End while loop
    } // End initialize_server() method

    public static void initialize_server(final int PORT_NUM) {
        /* validate the port number read in from the terminal is within bounds */
        try {
            if (PORT_NUM < 1024 || PORT_NUM > 65535) {
                throw new Exception("TCPServerThread.java - .initialize_server(PORT_NUM) - Port number is out of bounds");
            } // End if statement
        } catch (Exception e) {
            e.printStackTrace();
        } // End try-catch block

    } // End initialize_server()
    
} // End TCPServerThread class
