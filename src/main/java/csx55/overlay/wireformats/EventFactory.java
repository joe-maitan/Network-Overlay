package csx55.overlay.wireformats;

import java.io.*;

public class EventFactory {
    
    private EventFactory() {}

    private static final EventFactory instance = new EventFactory();

    public static EventFactory getInstance() {
        return instance;
    } // End getInstance() method

    public Event createEvent(byte[] arr) {
        try {
            ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
            DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

            int messageProtocol = din.readInt(); /* Read in the messageType */

            if (messageProtocol == Protocol.REGISTER_REQUEST) {
                return new RegisterRequest(din);
            } else if (messageProtocol == Protocol.REGISTER_RESPONSE) {
                return new RegisterResponse(din);
            } else if (messageProtocol == Protocol.DEREGISTER_REQUEST) {
                return new DeregisterRequest(din);
            } else if (messageProtocol == Protocol.DEREGISTER_RESPONSE) {
                return new DeregisterResponse(din);
            } else if (messageProtocol == Protocol.LINK_WEIGHTS) {
                return new LinkWeights(din);
            } else if (messageProtocol == Protocol.MESSAGE) {
                return new Message(din);
            } else if (messageProtocol == Protocol.MESSAGING_NODES_LIST) {
                return new MessagingNodesList(din);
            } else if (messageProtocol == Protocol.TASK_INITIATE) {
                return new TaskInitiate(din);
            } else if (messageProtocol == Protocol.TASK_SUMMARY_REQUEST) {
                return new TaskSummaryRequest(din);
            } else if (messageProtocol == Protocol.TASK_SUMMARY_RESPONSE) {
                return new TaskSummaryResponse(din);
            } else if (messageProtocol == Protocol.TASK_COMPLETE) {
                return new TaskComplete(din);
            } // End if-else statements
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return null;
    } // End createEvent() method

} // End EventFactory class
