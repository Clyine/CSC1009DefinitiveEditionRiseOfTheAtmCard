package pi_auth;

import java.io.*;

public class Auth {

    public static BufferedReader inp;
    public static BufferedWriter out;

    public static void print(String s) {
    System.out.println(s);
    }

    public static String pipe(String msg) {
    String ret;

    try {
        out.write( msg + "\n" );
        out.flush();
        ret = inp.readLine();
        return ret;
    }
    catch (Exception err) {

    }
    return "";
    }

    public String getCard() {

    String cmd = "python3 scan.py";

    try {

        print(cmd);
        print(System.getProperty("user.dir"));
        Process p = Runtime.getRuntime().exec(cmd);

        inp = new BufferedReader( new InputStreamReader(p.getInputStream()) );
        out = new BufferedWriter( new OutputStreamWriter(p.getOutputStream()) );
        print("scanning: ");
        // print( pipe("AAAaaa") );
        String cardID = pipe("presentcard");
        pipe("quit");
        inp.close();
        out.close();
        print(cardID);
        return cardID;
    }

    catch (Exception err) {
        err.printStackTrace();
    }
    return "Null";
    }
    
}
