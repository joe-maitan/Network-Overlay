package csx55.overlay.wireformats;

import csx55.overlay.wireformats.*;
import java.io.*;

public class EventFactory {
    
    // public Event event_factory(byte[] arr) {
    //     ByteArrayInputStream baInputStream = new ByteArrayInputStream(arr);
    //     DataInputStream din = new DataInputStream(baInputStream);

    //     int protocol = 0;
        
    //     try {
    //         protocol = din.readInt();
    //     } catch (IOException err) {
    //         System.err.println(err.getMessage());
    //     } // End try-catch block

    //     Event new_event = null;

    //     switch (protocol) {
    //         case 0:
    //             new_event = new RegisterRequest();
    //             break;
    //         case 1:
    //             new_event = new RegisterResponse();
    //             break;
    //         case 2:
    //             new_event = new DeregisterRequest();
    //             break;
    //         case 3:
    //             new_event = new DeregisterResponse();
    //             break;
    //         case 4:
    //             new_event = new LinkWeights();
    //             break;
    //         case 5: 
    //             new_event = new Message();
    //             break;
    //         case 6:
    //             new_event = new MessagingNodesList();
    //             break;
    //         case 7:
    //             new_event = new TaskInitiate();
    //             break;
    //         case 8:
    //             new_event = new TaskComplete();
    //             break;
    //         case 9:
    //             new_event = new TaskSummaryRequest();
    //             break;
    //         case 10:
    //             new_event = new TaskSummaryResponse();
    //             break;
    //     } // End switch protocol

    //     new_event.setBytes(din);
    //     return new_event;
    // } // End event_factory() method

    private EventFactory() {}

    private static final EventFactory instance = new EventFactory();

    public static EventFactory getInstance() {
        return instance;
    } // End getInstance() method

    public Event createEvent(byte[] arr) {
        try {
            ByteArrayInputStream baIn = new ByteArrayInputStream(arr);
            DataInputStream din = new DataInputStream(new BufferedInputStream(baIn));

            /* Grabs the messageProtocol from the arr. This allows us to parse in the other information 
             * without needing to read the messageProtocol for every wireformat 
             */
            int messageProtocol = din.readInt();

            
            // TODO Add if statemnt for every protocol and make sure we call the right constructor
            if (messageProtocol == Protocol.REGISTER_REQUEST) {
                return new RegisterRequest(din);
            } else if (messageProtocol == Protocol.REGISTER_RESPONSE) {
                return new RegisterResponse();
            } else if (messageProtocol == Protocol.DEREGISTER_REQUEST) {
                return new DeregisterRequest(); // return new DeregisterRequest(din);
            } else if (messageProtocol == Protocol.DEREGISTER_RESPONSE) {

            } else if (messageProtocol == Protocol.LINK_WEIGHTS) {

            } else if (messageProtocol == Protocol.MESSAGE) {

            } else if (messageProtocol == Protocol.MESSAGING_NODES_LIST) {

            } else if (messageProtocol == Protocol.TASK_INITIATE) {

            } else if (messageProtocol == Protocol.TASK_SUMMARY_REQUEST) {

            } else if (messageProtocol == Protocol.TASK_SUMMARY_RESPONSE) {

            } else if (messageProtocol == Protocol.TASK_COMPLETE) {

            } // End if-else statements
        } catch (IOException err) {
            System.err.println(err.getMessage());
        } // End try-catch block

        return null;
    } // End createEvent() method

} // End EventFactory class
