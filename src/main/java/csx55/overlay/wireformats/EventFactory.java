package csx55.overlay.wireformats;

import csx55.overlay.wireformats.*;

public class EventFactory {
    
    public Event event_factory(int protocol) {
        Event temp = null;

        switch (protocol) {
            case 0:
                // temp = new Register();
                break;
            case 1:
                temp = new Deregister();
                break;
            case 2:
                temp = new LinkWeights();
                break;
            case 3:
                temp = new Message();
                break;
            case 4:
                temp = new MessagingNodesList();
                break;
            // case PROTOCOL:
            //     temp = new Protocol();
            //     break;
            case 5:
                temp = new TaskComplete();
                break;
            case 6:
                temp = new TaskInitiate();
                break;
            case 7:
                temp = new TaskSummaryRequest();
                break;
            case 8:
                temp = new TaskSummaryResponse();
                break;
        } // End switch protocol

        return temp;
    } // End event_factory() method

} // End EventFactory class
