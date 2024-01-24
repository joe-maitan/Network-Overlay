package csx55.overlay;

public enum Protocol {
    
    // All the types of messages that can be sent
    REGISTER, // Starts with 0
    DEREGISTER,
    LINKWEIGHTS,
    MESSAGE,
    MESSAGINGNODESLIST,
    PROTOCOL,
    TASKCOMPLETE,
    TASKINITIATE,
    TASKSUMMARYREQUEST,
    TASKSUMMARYRESPONSE // Ends with 10

} // End Protocol enum
