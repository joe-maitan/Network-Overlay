package csx55.overlay.wireformats;

public class Protocol {
    
    final static int REGISTER = 0;
    final static int DEREGISTER = 1;
    final static int LINKWEIGHTS = 2;
    final static int MESSAGE = 3;
    final static int MESSAGINGNODESLIST = 4;
    final static int TASKCOMPLETE = 5;
    final static int TASKINITIATE = 6;
    final static int TASKSUMMARYREQUEST = 7;
    final static int TASKSUMMARYRESPONSE = 8;

    private final int value;

    Protocol(int value) {
        this.value = value;
    } // End Protocol constructor

    public int getValue() {
        return value;
    } // End getValue() method

} // End Protocol enum
