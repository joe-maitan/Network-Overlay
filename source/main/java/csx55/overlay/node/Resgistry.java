package csx55.overlay;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Resgistry {

    /* The DataInputStream and DataOutputStream are arrays because we are
     * going to be communicating with a lot of nodes
     */
    static DataInputStream[] registry_read;
    static DataOutputStream[] registry_send;
    static ServerSocket server_socket; // The Registry is our one server
    static Socket[] client_sockets = new Socket[2];
    static ArrayList<MessagingNode> list_of_nodes = new ArrayList<>();

    public static void send_message(String msg, int node_index) {

    } // End send_message() method

    public static void recieve_message(int node_index) {

    } // End recieve_message() method

    public static void clean_up() {
        try {

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End clean_up() method

    public static void start_server(final int PORT_NUM) {
        /* validate the port number read in from the terminal is within bounds */
        try {
            if (PORT_NUM < 1024 || PORT_NUM > 65535) {
                throw new Exception("Resigstry.java - main method - Port number is out of bounds");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } // End try-catch block

        try {
            /* Create our server socket, wait for connections from other machines (nodes)
             * and then start having them communciate
             */
            System.out.println("Host name: " + InetAddress.getLocalHost());

            server_socket = new ServerSocket(PORT_NUM);

            System.out.println("Waiting for clients to join...");
            /* minimum of 10 clients can join the server */
            // write code for a minimum of 10 clients but for now will be working with 2 nodes/clients
            for (int i = 0; i < num_clients; ++i) {
                client_sockets[i] = server_socket.accept();
                // Do we create the new messagingNode here or in MessagingNode?
                // register the node with the registry here
            } // End for loop
            System.out.println("Clients (nodes) are connected and registered");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    }

    public static void main(String[] args) throws IOException {
        final int PORT_NUM = Integer.parseInt(args[0]);

        start_server(PORT_NUM); /* start the server, and accept connections from clients (different machines) */

        
    } // End main method

} // End Registry class
