package dboMars.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsMessaging {

    public void send(String phoneNumber, String tempMessage){
        String message = tempMessage.replace(" ", "_");
        String sendThisToThis = "http://localhost/Thesis/sms/send.php?send=send&message="+message+"&phoneNumber="+phoneNumber+" ";
        try{
            URL url = new URL(sendThisToThis);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            bufferedReader.close();
            //System.out.println(stringBuffer.toString());
        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }

}
