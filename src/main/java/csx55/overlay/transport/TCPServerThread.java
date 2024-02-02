package csx55.overlay.transport;

// import csx55.overlay.node.*;
// import csx55.overlay.wireformats.RegisterRequest;

import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread implements Runnable {

    private static ServerSocket server = null;
    private static TCPSender send = null;
    private static TCPReceiverThread read = null;
    public volatile boolean done = false;

    public String ip_address;
    public int server_port_number;

    public ArrayList<Socket> socket_connetions = new ArrayList<>();
    public ArrayList<TCPSender> senders = new ArrayList<>();
    public ArrayList<TCPReceiverThread> readers = new ArrayList<>();

    public TCPServerThread(final int PORT_NUM) {
        // System.out.println("TCPServerThread(port): Creating a new server");

        if (PORT_NUM > 1024 && PORT_NUM < 65536) { /* given a valid port number create the ServerSocket */
            try {
                this.server_port_number = PORT_NUM;
                server = new ServerSocket(this.server_port_number);
                this.ip_address = server.getInetAddress().getHostAddress();
            } catch (IOException err) {
                System.out.println(err.getMessage());
            } // End try-catch block
        } else if (PORT_NUM == 0) {
            boolean not_set = false;

            this.server_port_number = 1024; /* start at 1024 */

            while (!not_set && this.server_port_number < 65536) {
                try {
                    server = new ServerSocket(server_port_number);
                    this.ip_address = server.getInetAddress().getHostAddress();
                    not_set = !not_set;
                    System.out.println("Created new MessagingNode server thread at port #: " + this.server_port_number);
                } catch (IOException err) {
                    ++this.server_port_number;
                } // End try-catch block
            } // End while loop
        } // End if-else statement
      
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
                    String client_ip = s.getInetAddress().toString();
                    int client_port_num = s.getPort();
                    client_ip = client_ip.substring(1);
                    System.out.println("[Node]: " + client_ip + " has connected at port: " + client_port_num); /* validation that something that has connected */
                    add_socket(s);
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                } // End try-catch block
                } // End while loop
            
        } // End if statment
    } // End run() method

    public void send_msg(int i, byte[] arr) {
        try {
            senders.get(i).sendData(arr);
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
    } // End send_msg

    public int get_socket_index(Socket s) {
        return socket_connetions.indexOf(s);
    } // End get_socket_index() method

    public void add_socket(Socket s) {
        socket_connetions.add(s); /* Add the socket to the ArrayList containing them */

        try {
            send = new TCPSender(s, socket_connetions.indexOf(s)); /* Assign the TCPSender obj to send messages  */
            senders.add(send); /* Keep track of how many nodes have TCPSender objects */
            read = new TCPReceiverThread(s, socket_connetions.indexOf(s)); /* Assign the TCPReceieverThread obj to read messages */
            readers.add(read); /* Keep track of how many nodes have TCPReceiver objects */


            /* TODO: I feel like I am opening the Receiver thread too soon and need to actually send data before receving any back */
            // send.sendData(RegisterRequest.getBytes());

            /* Create TCP Receiver start the thread for the new reader to check that if the thread */
            Thread server_read_thread = new Thread(read);
            server_read_thread.start(); /* starts the TCPReceiver thread and begins to read in information while it has information to read */
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block
                       
    } // End add_socket
} // End TCPServerThread class
