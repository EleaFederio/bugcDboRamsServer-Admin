package dboMars.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SmsGateWayDotMe {

    public String send(String sendTo, String theMessage) {
        try {
            // Construct data
            String message = theMessage;
            String number = sendTo;


            // Send data
            String data = "http://localhost/Thesis/SmS/send.php?" + "send " +"cpNumber="+ number +" message="+ message;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult="";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult=sResult+line+" ";
            }
            rd.close();

            return sResult;
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}