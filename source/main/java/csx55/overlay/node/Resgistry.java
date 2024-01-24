package csx55.overlay;

import java.io.DataOutputStream;
import java.io.IOException;

public class Resgistry {

    /* The DataInputStream and DataOutputStream are arrays because we are
     * going to be communicating with a lot of nodes
     */
    static DataInputStream[] registry_read;
    static DataOutputStream[] registry_send;
    static ServerSocket server_socket; // The Registry is our one server
    static Socket[] clients;

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
        final int port = Integer.parseInt(args[0]);
        
        try {
            if (port < 1024 || port > 65535) {
                throw new Exception("Resigstry.java - main method - Port number is out of bounds");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // End main method

} // End Registry class
