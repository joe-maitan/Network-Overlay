package csx55.overlay.transport;
import csx55.overlay.node.Registry;

import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread implements Runnable {

    private static ServerSocket server = null; /* originally had static */
    public volatile boolean done = false;

    int server_port_number = 1024;
    String server_ip_address;

    public ArrayList<Socket> socket_connetions = new ArrayList<>();

    // public TCPServerThread() {
    //     System.out.println("TCPServerThread(): Creating new server");
    //     /* this creates a server thread with no specifed port # */
    //     int new_port_num = 1025;
    //     while (server == null && new_port_num < 65536) {
    //         try {
    //             server = new ServerSocket(new_port_num);
    //             connection_is_active = true;
    //         } catch (IOException e) {
    //             ++new_port_num; // Increment the port_num every time we cannot initialize our_server
    //         } // End try-catch block
    //     } // End the while loop 

    //     System.out.println("TCPServerThread(): Finished creating new server");
    //     System.out.println("host name: " + server.getInetAddress().getHostAddress());
    // } // End TCPReceiverThread() default constructor

    public TCPServerThread(final int PORT_NUM) {
        System.out.println("TCPServerThread(port): Creating a new server");

        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                this.server_port_number = PORT_NUM;
                
                server = new ServerSocket(this.server_port_number); 
                // this.server_ip_address = string(server.getInetAddress()); Figure out how to parse the IP address
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } else {
            boolean not_set = false;

            while (!not_set) {
                try {
                    server = new ServerSocket(server_port_number);
                    not_set = !not_set;
                    System.out.println("Created new server thread at port #: " + this.server_port_number);
                    System.out.println("IP address " + server.getInetAddress()); /* cannot read your own IP address */
                } catch (IOException err) {
                    ++this.server_port_number;
                } // End try-catch block
            }
        } 

        System.out.println("TCPServerThread(port): Finished creating new server");
        System.out.println("Host name: " + server.getInetAddress());
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
                    socket_connetions.add(s); /* keep track of the sockets we are connected to */
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                } // End try-catch block
                } // End while loop
            
        } // End if statment

    } // End run() method

} // End TCPServerThread class
