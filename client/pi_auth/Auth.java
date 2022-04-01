// RFID card authentication package
package client.pi_auth;
// import java io
import java.io.*;
//public Auth class
public class Auth {
    //declard bufferedreader and writer variables
    public static BufferedReader din;
    public static BufferedWriter dout;
    //default constructor
    public Auth(){
    }
    //method pipe to write msg to buffer
    public String pipe(String msg) {
        String ret;
        try {
            dout.write(msg + "\n");
            dout.flush();
            ret = din.readLine();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //method to get card id
    public String getCard() {
        String fileCmd = "python3 scan.py";
        try {
            Process p = Runtime.getRuntime().exec(fileCmd);
            din = new BufferedReader(new InputStreamReader(p.getInputStream()));
            dout = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            System.out.println("Scanning RFID Card...");
            String id = pipe("presentcard");
            pipe("quit");
            din.close();
            dout.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Null";
    }

}
