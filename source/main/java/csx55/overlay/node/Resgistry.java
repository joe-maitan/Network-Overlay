package csx55.overlay;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Resgistry {

    /* The DataInputStream and DataOutputStream are arrays because we are
     * going to be communicating with a lot of nodes
     */
    static DataInputStream[] registry_read;
    static DataOutputStream[] registry_send;
    static ServerSocket server_socket; // The Registry is our one server
    static Socket[] client_sockets = new Socket[2];

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

    public static void main(String[] args) throws IOException {
        final int PORT_NUM = Integer.parseInt(args[0]);
        
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
            for (int i = 0; i < num_clients; ++i) {
                client_sockets[i] = server_socket.accept();

            } // End for loop
            System.out.println("Clients (nodes) are connected");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } // End try-catch block
    } // End main method

} // End Registry class
