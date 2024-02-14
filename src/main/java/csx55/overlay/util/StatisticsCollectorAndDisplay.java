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
        String header = "\tNumber Of Messages Sent\tNumber Of Messages Received\tSummation Of Sent Messages\tSummation Of Received Messages\tNumber Of Messages Relayed";

        System.out.println(header);

        int numberOfNodes = 1;
        for (TaskSummaryResponse rsp : responseList) {
            String out = "Node: " + numberOfNodes + "\t" + rsp.getMsgSent() + "\t" + rsp.getMsgReceived() + "\t" + rsp.getMsgSentSum() + "\t" + rsp.getMsgReceivedSum() + "\t" + rsp.getMsgsRelayed();
            System.out.println(out);
            numberOfNodes++;
        } // End for each loop
    } // End displayStatistics() method



    
} // End StatisticsCollectoryAndDisplay class
