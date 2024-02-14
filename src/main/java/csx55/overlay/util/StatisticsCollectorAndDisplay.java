package csx55.overlay.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

import csx55.overlay.wireformats.TaskSummaryResponse;

public class StatisticsCollectorAndDisplay {

    private AtomicInteger numMsgsSent;
    private AtomicInteger numMsgsReceived;

    private ArrayList<TaskSummaryResponse> responseList;


    public StatisticsCollectorAndDisplay(ArrayList<TaskSummaryResponse> list) {
        this.responseList = list;
        // processListInformation();
    } // End StatisticsCollectorAndDisplay

    // public void processListInformation() {
    //     for (TaskSummaryResponse rsp : responseList) {
    //         numMsgsSent += responseList.get(responseList.indexOf(rsp)).getMsgSent();
    //         numMsgsReceived
    //     }
    // } // End processListInformation() method

    public void displayStatistics() {
        String header = "\tNumber Of Messages Sent\t\tNumber Of Messages Received\t\tSummation Of Sent Messages\t\tSummation Of Received Messages\t\tNumber Of Messages Relayed";

        System.out.println(header);

        int numberOfNodes = 1;
        for (TaskSummaryResponse rsp : responseList) {
            String out = "Node: " + numberOfNodes + "\t\t\t" + rsp.getMsgSent() + "\t\t\t" + rsp.getMsgReceived() + "\t\t\t" + rsp.getMsgSentSum() + "\t\t\t" + rsp.getMsgReceivedSum() + "\t\t\t" + rsp.getMsgsRelayed();
            System.out.println(out);
            numberOfNodes++;
        } // End for each loop
    } // End displayStatistics() method



    
} // End StatisticsCollectoryAndDisplay class
