package socket;
import model.*;
import datastore.*;

import java.net.*;
import java.io.*;

public class Server {

    private static final int portNum = 3333;
    ServerSocket server;
    DataStore d;

    public Server() throws Exception{
        this.d = new DataStore();  //load csv into memory
        this.serverRun(d);
    }

    public void serverRun(DataStore d) throws Exception {
        server = new ServerSocket(portNum); //Start Server Socket at given port
        System.out.println("Starting socket server at port : "+portNum);
        //Loop infinitely and listen to any connection requests
        while (true) {
            Socket s = null;
            try {
                s = server.accept(); //Accept any client socket request received
                System.out.println("A new client is connected : " + s);
                DataInputStream dis = new DataInputStream(s.getInputStream()); //Assign Input and output stream to client socket
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Assigning new thread for this client.....");
                Thread t = new ClientHandler(s, dis, dos, d); //Starts anew thread for client socket
                t.start(); //Start thread
            } catch (Exception e) {
                s.close();
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
