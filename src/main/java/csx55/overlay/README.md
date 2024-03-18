How to run HW1 for CSX55

1. module load courses/cs455

2. gradle build - To build the jar file we will be executing

3. gradle clean - If you need to clear the build directory

How to run the Registry code: java -cp build/classes/java/main/ csx55.overlay.node.Registry <port-number>

How to run the MessagingNode code: java -cp build/classes/java/main/ csx55.overlay.node.MessagingNode <hostname> <port-number>

Tar command for submission: tar -cvf Joseph_Maitan_HW1.tar src build.gradle README.md

java -cp build/classes/java/main/ csx55.overlay.node.MessagingNode ferrari 5505

Registry Commands (in this order)
1. setup-overlay <number-of-connections>
2. send-overlay-link-weights
3. start <num-rounds>

Extra:
    list-messaging-nodes
    list-weights

MessagingNode Commands
    print-shortest-path
    exit-overlay


