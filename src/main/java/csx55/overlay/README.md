How to run HW1 for CSX55

1. module load courses/cs455

2. gradle build - To build the jar file we will be executing

gradle clean - To

Go into this directory for the jar: ~/CS455/csx55/build/libs$

3. How to run the Registry code: java -cp build/libs/csx55.jar csx55.overlay.node.Registry <port-number>

4. How to run the MessagingNode code: java -cp build/libs/csx55.jar csx55.overlay.node.MessagingNode <hostname> <port-number>