package csx55.overlay.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

import csx55.overlay.wireformats.TaskSummaryResponse;

public class StatisticsCollectorAndDisplay {

    private ArrayList<TaskSummaryResponse> responseList;

    public StatisticsCollectorAndDisplay(ArrayList<TaskSummaryResponse> list) {
        this.responseList = list;
    } // End StatisticsCollectorAndDisplay

    public void displayStatistics() {
        int sumOfAllMessagesSent = 0;
        int sumOfNumberMessagesSent = 0;

        int sumOfNumberMessagesReceived = 0;
        int sumOfAllMessagesReceived = 0;
        String header = "\tNumber Of Messages Sent\tNumber Of Messages Received\tSummation Of Sent Messages\tSummation Of Received Messages\tNumber Of Messages Relayed";

        System.out.println(header);

        int numberOfNodes = 1;
        for (TaskSummaryResponse rsp : responseList) {
            String out = "Node " + numberOfNodes + "\t\t" + rsp.getMsgSent() + "\t\t\t" + rsp.getMsgReceived() + "\t\t\t" + rsp.getMsgSentSum() + "\t\t\t" + rsp.getMsgReceivedSum() + "\t\t\t" + rsp.getMsgsRelayed();
            System.out.println(out);
            numberOfNodes++;

            sumOfNumberMessagesSent += rsp.getMsgSent();
            sumOfAllMessagesSent += rsp.getMsgSentSum();
            sumOfNumberMessagesReceived += rsp.getMsgReceived();
            sumOfAllMessagesReceived += rsp.getMsgReceivedSum();
        } // End for each loop

        String tail = "Sum\t\t " + sumOfNumberMessagesSent + "\t" + sumOfNumberMessagesReceived + "\t" + sumOfAllMessagesSent + "\t" + sumOfAllMessagesReceived;
        System.out.println(tail);
    } // End displayStatistics() method



    
} // End StatisticsCollectoryAndDisplay class
