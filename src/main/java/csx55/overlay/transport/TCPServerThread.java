package csx55.overlay;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerThread implements runnable {

    static ServerSocket our_server;
    public ArrayList<Socket> sockets = new ArrayList<>();
    volatile boolean done = false;

    /* This TCPServerThread takes in a port number passed in from the MessagingNode/Node it has spawned from.
     * From there, we check if the PORT_NUM passed in is a valid port number, else, we increment the new port #
     * While our_server is not intialized and the new_port_num is less that 65546 we keep incrementing it until
     * a valid port is found and the ServerSocket is initialized.
     */
    public TCPServerThread(final int PORT_NUM) {
        if (PORT_NUM > 1024 && PORT_NUM < 65536) {
            /* the given port is valid */
        } else {
            int new_port_num = 1025;

            while (our_server == null && new_port_num < 65536) {
                try {
                    our_server = new ServerSocket(new_port_num);
                } catch (SocketException e) {
                    ++new_port_num; // Increment the port_num every time we cannot initialize our_server
                } // End try-catch block
            } // End the while loop
        }

        Thread t = new Thread(TCPServerThread);
    } // End TCPServerThread

    public void run() {
    
    } // End run() method

    
    
} // End TCPServerThread class
