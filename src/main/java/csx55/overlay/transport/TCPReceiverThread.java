package csx55.overlay.transport;

import java.io.*;
import java.net.*;

import csx55.overlay.wireformats.EventFactory;

public class TCPReceiverThread implements Runnable {
    private Socket socket;
    private DataInputStream din;
    public int index;
    
    public TCPReceiverThread(Socket socket, int array_list_index) throws IOException {
        this.socket = socket;
        din = new DataInputStream(socket.getInputStream());
        this.index = array_list_index;
    } // End TCPReceiver(socket) constructor
    
    public void run() {
        int data_length;

        System.out.println("Entering the run of TCPReceieverThread");
        while (socket != null) {
            try {
                data_length = din.readInt();

                byte[] data = new byte[data_length];
                din.readFully(data, 0, data_length);

                System.out.println("Read in data:" + data);

                /* TODO: get the event protocol */
                EventFactory event_fac = new EventFactory();
                // EventFactory.event_fac(din);
            } catch (SocketException se) {
                System.out.println(se.getMessage());
                break;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                break;
            } // End try-catch block
        } // End while loop
    } // End run() method

} // End TCPReceiverThread class