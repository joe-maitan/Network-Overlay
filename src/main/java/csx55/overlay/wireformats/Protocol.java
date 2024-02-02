package csx55.overlay.wireformats;

public class Protocol {
    
    final static int REGISTER_REQUEST = 0;
    final static int REGISTER_RESPONSE = 1;
    final static int DEREGISTER_REQUEST = 2;
    final static int DEREGISTER_RESPONSE = 3;
    final static int LINK_WEIGHTS = 4;
    final static int MESSAGE = 5;
    final static int MESSAGING_NODES_LIST = 6;
    final static int TASK_INITIATE = 7;
    final static int TASK_COMPLETE = 8;
    final static int TASK_SUMMARY_REQUEST = 9;
    final static int TASK_SUMMARY_RESPONSE = 10;

    private final int value;

    Protocol(int value) {
        this.value = value;
    } // End Protocol constructor

    public int getValue() {
        return value;
    } // End getValue() method

} // End Protocol enum
