package csx55.overlay.wireformats;

public enum Protocol {

    REGISTER(0),
    DEREGISTER(1),
    LINKWEIGHTS(2),
    MESSAGE(3),
    MESSAGINGNODESLIST(4),
    TASKCOMPLETE(5),
    TASKINITIATE(6),
    TASKSUMMARYREQUEST(7),
    TASKSUMMARYRESPONSE(10);

    private final int value;

    Protocol(int value) {
        this.value = value;
    } // End Protocol constructor

    public int getValue() {
        return value;
    } // End getValue() method

} // End Protocol enum
