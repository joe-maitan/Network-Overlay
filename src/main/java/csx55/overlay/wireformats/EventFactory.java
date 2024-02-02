package csx55.overlay.wireformats;

import csx55.overlay.wireformats.*;

public class EventFactory {
    
    public Event event_factory(int protocol) {
        Event new_event = null;

        switch (protocol) {
            case 0:
                new_event = new RegisterRequest(null);
                break;
            case 1:
                new_event = new RegisterResponse();
                break;
            case 2:
                new_event = new DeregisterRequest();
                break;
            case 3:
                new_event = new DeregisterResponse();
                break;
            case 4:
                new_event = new LinkWeights();
                break;
            case 5: 
                new_event = new Message();
                break;
            case 6:
                new_event = new MessagingNodesList();
                break;
            case 7:
                new_event = new TaskInitiate();
                break;
            case 8:
                new_event = new TaskComplete();
                break;
            case 9:
                new_event = new TaskSummaryRequest();
                break;
            case 10:
                new_event = new TaskSummaryResponse();
                break;
        } // End switch protocol

        return new_event;
    } // End event_factory() method

} // End EventFactory class
