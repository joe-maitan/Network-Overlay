# How to run the Network Overlay

gradle build - To build the jar file we will be executing

gradle clean - If you need to clear the build directory

How to run the Registry (server) code: java -cp build/classes/java/main/ csx55.overlay.node.Registry <port-number>

How to run the MessagingNode (client) code: java -cp build/classes/java/main/ csx55.overlay.node.MessagingNode <hostname> <port-number>

# Commands for the Registry
1. setup-overlay <number-of-connections>
2. send-overlay-link-weights
3. start <num-rounds>

Extra:
    list-messaging-nodes
    list-weights
    
# Commands for the Messaging Node
1. exit-overlay
