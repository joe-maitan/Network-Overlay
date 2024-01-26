package csx55.overlay.wireformats;

public class TaskSummaryRequest implements Event {

    public String getType(){
        String s = "";
        
        return s;
    } // Gives us the protocol type

    public int getBytes(){
        return 0;
    } // Marshalling

    public void setBytes(byte[] arr){

    } // Unmarshalling
    
} // End TaskSummaryRequest class
