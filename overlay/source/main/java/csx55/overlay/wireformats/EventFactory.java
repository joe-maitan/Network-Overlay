package csx55.overlay;

public class EventFactory implements Protocol {
    
    public static Event facory(int protocol) {
        Event temp = null;

        switch (protocol) {
            case REGISTER:
                break;
            case DEREGISTER:
                break;
            case LINKWEIGHTS:

        } // End switch protocol

        return temp;
    } // End factory() method

} // End EventFactory class
