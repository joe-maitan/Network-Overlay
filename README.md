# CS455

This is my GitHub repository holding all of the work I did in my first capstone course CS455. 

The CSU catalog description of the course states, "Distributed systems including model of distributed computations; concurrency; thread pools and scalable servers; distributed mutual exclusion; cloud computing; distributed graph algorithms; data representation formats; atomic transactions; large-scale storage systems; distributed shared memory; and overlays".



gradle build

Go into this directory for the jar: ~/CS455/csx55/build/libs$

How to run the Registry code: java -cp csx55.jar csx55.overlay.node.Registry 5505

How to run the MessagingNode code: java -cp csx55.jar csx55.overlay.node.MessagingNode <hostname> <port-number>