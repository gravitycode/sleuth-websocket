# sleuth websocket
Project for testing Sleuth with SockJS clients with Stomp messages. 
The library should intercept messages and put trace id and span id as native headers. 

The application is initialized with the conext / and port 8081

To launch the test we have to invoke the url: 
http://localhost:8081/send