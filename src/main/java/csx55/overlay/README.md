# How to run the Network Overlay
First, we need to build the project.
```
gradle build
```

If needed clean the project.
```
gradle clean
```

How to run the Registry (server) code: 
```
java -cp build/classes/java/main/ csx55.overlay.node.Registry <port-number>
```

How to run the MessagingNode (client) code: 
```
java -cp build/classes/java/main/ csx55.overlay.node.MessagingNode <hostname> <port-number>
```

## Commands for the Registry
You can specify a number of connections for the nodes to connect to. The default is 4.
```
setup-overlay <number-of-connections>
```
```
send-overlay-link-weights
```
```
start <num-rounds>
```

### Extra:
```
list-messaging-nodes
```
```
list-weights
```
    
## Commands for the Messaging Node
```
register
```
```
exit-overlay
```
