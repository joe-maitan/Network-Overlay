import java.io.*;

public class TCPReceiverThread implements Runnable {
    private Socket socket;
    private DataInputStream data_in;

    public TCPReceiverThread(Socket socket) throws IOException {
        this.socket = socket;
        data_in = new DataInputStream;
    } // End TCPRecieverThread constructor

    public void run() {
        int data_length;

        while (socket != null) {
            try {
                data_length = data_in.readInt();

                byte[] data = new byte[data_length];
                data_in.readFully(data, 0, data_length)
            } catch (SocketException se) {
                System.out.println("SocketException - TCPRecieverThread class - public void run().\n");
                break;
            } catch (IOException ioe) {
                System.out.println("IOException - TCPRecieverThread class - public void run().\n");
                break;
            } // End try-catch block
        } // End while loop
    } // End run() method

} // End TCPReceiverThread class