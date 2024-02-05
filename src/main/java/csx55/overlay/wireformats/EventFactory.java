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

            int messageProtocol = din.readInt();

            // TODO Add if statemnt for every protocol
            if (messageProtocol == Protocol.REGISTER_REQUEST) {
                return new RegisterRequest(din);
            } else if (messageProtocol == Protocol.DEREGISTER_REQUEST) {
                return new DeregisterRequest(); // return new DeregisterRequest(din);
            } 

            
        } catch (IOException err) {
            System.err.println(err.getMessage());
        }
       
        return null;
    } // End createEvent() method

} // End EventFactory class
