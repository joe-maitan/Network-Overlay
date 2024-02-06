package csx55.overlay.transport;

import java.io.*;
import java.net.*;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;

public class TCPReceiverThread implements Runnable {
    private Socket socket;
    private DataInputStream din;
    public int socketIndex;

    /* This allows us to know which instance of a Node object this is. 
     * Could be a instanceOf MessagingNode or Registry.
     * Helps with onEvent()
    */
    public Node referenceNode; 
    
    public TCPReceiverThread(Socket s, int array_list_index, Node node) throws IOException {
        socket = s;
        socketIndex = array_list_index;
        referenceNode = node;
    } // End TCPReceiver(socket) constructor
    
    public void run() {
        int data_length;

        while (this.socket != null) {
            try {
                /* Delete when finisihed */
                // System.out.println("Reading data from: " + socket.getInetAddress().getHostName());
                din = new DataInputStream(socket.getInputStream());
                
                data_length = din.readInt(); /* THIS IS THE LENGTH OF OUR MESSAGE */
                byte[] data = new byte[data_length]; /* We make a new byte array of that length */
                din.readFully(data, 0, data.length); /* And have the DataInput read in the information */
                
                Event new_event = EventFactory.getInstance().createEvent(data); /* Create a new Event based on the byte[] data */
                referenceNode.onEvent(new_event, socketIndex);
            } catch (SocketException se) {
                System.out.println(se.getMessage());
                break;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                break;
            } // End try-catch block
        } // End while loop

        // System.out.println("Exiting .run() method for TCPReceieverThread");
    } // End run() method

} // End TCPReceiverThread class