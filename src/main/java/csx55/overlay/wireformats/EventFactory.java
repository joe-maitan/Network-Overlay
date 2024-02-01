package csx55.overlay.wireformats;

import csx55.overlay.wireformats.*;

public class EventFactory {
    
    public Event event_factory(Protocol protocol) {
        Event temp = null;

        switch (protocol) {
            case REGISTER:
                // temp = new Register();
                break;
            case DEREGISTER:
                temp = new Deregister();
                break;
            case LINKWEIGHTS:
                temp = new LinkWeights();
                break;
            case MESSAGE:
                temp = new Message();
                break;
            case MESSAGINGNODESLIST:
                temp = new MessagingNodesList();
                break;
            // case PROTOCOL:
            //     temp = new Protocol();
            //     break;
            case TASKCOMPLETE:
                temp = new TaskComplete();
                break;
            case TASKINITIATE:
                temp = new TaskInitiate();
                break;
            case TASKSUMMARYREQUEST:
                temp = new TaskSummaryRequest();
                break;
            case TASKSUMMARYRESPONSE:
                temp = new TaskSummaryResponse();
                break;
        } // End switch protocol

        return temp;
    } // End event_factory() method

} // End EventFactory class
