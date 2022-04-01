//import java packages
package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import pi_auth.*;

// Client class
public class client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);
            String ip = "192.168.1.130"; //change this to address of server

            // establish the connection with server port 3333
            Socket s = new Socket(ip, 3333);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                String dataIn = dis.readUTF();
                System.out.println(dataIn);
                String tosend = "";
                if (dataIn.equals("SCANRFID")){
                    Auth newAuth = new Auth();
                    String cardID = newAuth.getCard();
                    dos.writeUTF(cardID);
                } else {
                    tosend = scn.nextLine();
                    dos.writeUTF(tosend);
                }

                // If client sends exit,close this connection
                // and then break from the while loop
                if (tosend.equals("exit")) {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}