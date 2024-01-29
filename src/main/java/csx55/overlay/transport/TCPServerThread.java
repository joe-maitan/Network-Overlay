package csx55.overlay.transport;
import csx55.overlay.node.Registry;

import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread implements Runnable {

    private static ServerSocket server = null; /* originally had static */
    public volatile boolean done = false;

    int server_port_number;

    public ArrayList<Socket> socket_connetions = new ArrayList<>();

    public TCPServerThread(final int PORT_NUM) {
        // System.out.println("TCPServerThread(port): Creating a new server");

        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                this.server_port_number = PORT_NUM;
                
                server = new ServerSocket(this.server_port_number); 
                // this.server_ip_address = string(server.getInetAddress()); Figure out how to parse the IP address
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } else if (PORT_NUM == 0) {
            boolean not_set = false;

            this.server_port_number = 1024; /* start at 1024 */

            while (!not_set && this.server_port_number < 65536) {
                try {
                    server = new ServerSocket(server_port_number);
                    not_set = !not_set;
                    System.out.println("Created new MessagingNode server thread at port #: " + this.server_port_number);
                } catch (IOException err) {
                    ++this.server_port_number;
                } // End try-catch block
            }
        } 

        // System.out.println("TCPServerThread(port): Finished creating new server");
        // System.out.println("Host name: " + server.getInetAddress());
    } // End TCPServerThread(port) constructor

    public void close_server() {
        done = true;
        
        try {
            server.close();
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
    } // End close_server() method

    public void run() {
        if (server != null) {
            while (!done) {
                try {
                    // System.out.println("Creating a new socket");
                    Socket s = server.accept();
                    System.out.println(s.getInetAddress() + " has connected!"); /* validation that something that has connected */
                    socket_connetions.add(s); /* Add the socket to the ArrayList containing them */
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                } // End try-catch block
                } // End while loop
            
        } // End if statment

    } // End run() method

} // End TCPServerThread class
