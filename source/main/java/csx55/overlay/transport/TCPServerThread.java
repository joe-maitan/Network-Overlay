package csx55.overlay;

import java.io.*;
import java.net.*;

public class TCPServerThread {

    static ServerSocket our_server;

    public void run(final int PORT_NUM) {
        initialize_server(PORT_NUM);
    } // End run() method

    public static void initialize_server() {
        int port_num = 1025;
        while (our_server == null) {
            try {
                our_server = new ServerSocket(port_num);
            } catch (SocketException e) {
                // System.err.println(e.getMessage());
                ++port_num; // Increment the port_num every time we cannot initialize our_server
            } // End try-catch block
        } // End while loop
    } // End initialize_server() method

    public static void initialize_server(final int PORT_NUM) {
        if (PORT_NUM == null) { /* port number is empty */
            int port_num = 1025;
            while (our_server == null) {
                try {
                    our_server = new ServerSocket(port_num);
                } catch (SocketException e) {
                    // System.err.println(e.getMessage());
                    ++port_num; // Increment the port_num every time we cannot initialize our_server
                } // End try-catch block
            } // End while loop
        } else {
            /* validate the port number read in from the terminal is within bounds */
            try {
                if (PORT_NUM < 1024 || PORT_NUM > 65535) {
                    throw new Exception("TCPServerThread.java - .initialize_server(PORT_NUM) - Port number is out of bounds");
                } // End if statement
            } catch (Exception e) {
                e.printStackTrace();
            } // End try-catch block
        } // End if-else statement
        
    } // End initialize_server()
    
} // End TCPServerThread class
