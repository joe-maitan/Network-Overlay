package csx55.overlay.transport;

import java.io.*;
import java.net.*;

import csx55.overlay.node.*;
import csx55.overlay.wireformats.*;

public class TCPReceiverThread implements Runnable {
    private Socket socket;
    private DataInputStream din;
    public int index;

    public Node referenceNode;
    
    public TCPReceiverThread(Socket s, int array_list_index, Node node) throws IOException {
        socket = s;
        index = array_list_index;
        referenceNode = node;
    } // End TCPReceiver(socket) constructor
    
    public void run() {
        int data_length;

        System.out.println("Entering the .run() of TCPReceieverThread");
        while (this.socket != null) {
            System.out.println("inside the while loop of  TCPReceiverThread.run() method");
            try {
                System.out.println("Entering the try statement. Reading in data");
                System.out.println("Reading data from: " + socket.getInetAddress().getHostName());
                din = new DataInputStream(socket.getInputStream());
                
                data_length = din.readInt(); /* THIS IS THE LENGTH OF OUR MESSAGE */

                System.out.println("Data length: " + data_length);

                System.out.println("About to read in the data byte[]");
                byte[] data = new byte[data_length];
                din.readFully(data, 0, data.length);
                System.out.println("Finished reading the data byte[]");

                
                System.out.println("Creating new EventFactory()");
                Event new_event = EventFactory.getInstance().createEvent(data);
                
                referenceNode.onEvent(new_event, index);
            } catch (SocketException se) {
                System.out.println(se.getMessage());
                break;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                break;
            } // End try-catch block
        } // End while loop

        System.out.println("exiting the while loop of the .run() method for TCPReceiever");
    } // End run() method

} // End TCPReceiverThread class