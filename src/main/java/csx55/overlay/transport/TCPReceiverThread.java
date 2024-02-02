package csx55.overlay.transport;

import java.io.*;
import java.net.*;

import csx55.overlay.node.Node;
import csx55.overlay.wireformats.*;

public class TCPReceiverThread implements Runnable {
    private Socket socket;
    private DataInputStream din;
    public int index;
    
    public TCPReceiverThread(Socket s, int array_list_index) throws IOException {
        this.socket = s;
        din = new DataInputStream(socket.getInputStream());
        this.index = array_list_index;
    } // End TCPReceiver(socket) constructor
    
    public void run() {
        int data_length;

        System.out.println("Entering the .run() of TCPReceieverThread");
        while (this.socket != null) {
            System.out.println("inside the while loop of my .run() method");
            try {
                System.out.println("Entering the try statement");
                this.socket = new Socket();
                data_length = din.readInt();

                System.out.println("Data length: " + data_length);

                byte[] data = new byte[data_length];
                din.readFully(data, 0, data_length);

                /* TODO: get the event protocol */
                System.out.println("Creating new EventFactory()");
                EventFactory event_fac = new EventFactory();
                
                Event new_event = event_fac.event_factory(data);
                Node.onEvent(new_event, index);
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