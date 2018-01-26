package dboMars.Utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMS {

    public String send(String sendTo, String theMessage) {
        try {
            // Construct data
            String user = "username=" + URLEncoder.encode("eleafederiojava24@gmail.com", "UTF-8");
            String hash = "&hash=" + URLEncoder.encode("oEAMG6iLNkM-uht9slzJrmlsShUBVdBSeUYnGjHasT", "UTF-8");
            String message = "&message=" + URLEncoder.encode(theMessage, "UTF-8");
            String sender = "&sender=" + URLEncoder.encode("BUGC DBO", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode(sendTo, "UTF-8");

            // Send data
            String data = "http://api.txtlocal.com/send/?" + user + hash + numbers + message + sender;
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult="";
            System.out.println(data);
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