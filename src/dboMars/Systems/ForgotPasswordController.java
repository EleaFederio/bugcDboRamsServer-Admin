package dboMars.Systems;

import dboMars.Database.Database;
import dboMars.Main;
import dboMars.Systems.LoginController;
import dboMars.Utilities.SMS;
import dboMars.Utilities.SecretKey;
import dboMars.Utilities.SmsGateWayDotMe;
import dboMars.Utilities.SmsMessaging;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForgotPasswordController {
    SmsMessaging sms = new SmsMessaging();
    Database database = new Database();
    @FXML private Button cancelButton = new Button();
    @FXML public  TextField checkThisNumber = new TextField();
    public static Stage confirmCodeWindow = new Stage();

    public void cancel(){
        LoginController.forgotPasswordWindow.hide();
        Main.window.show();
    }

    public  void confirmNumber(){
        System.out.println("Confirm");
        try {
            database.subConnect();
            String phoneNumberCheckQuery = "SELECT phoneNumber FROM user WHERE phoneNumber = '+63"+checkThisNumber.getText()+"'";
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(phoneNumberCheckQuery);

            if (resultSet.next()){
                System.out.println("Number matched!");
                String securityKey = SecretKey.generate(8);
                String phoneNumber = resultSet.getString("phoneNumber").replace("+","");
                String message = "Your security key is "+securityKey+" ";
                sms.send(phoneNumber, message);

                String updateSecurityKey = "UPDATE `user` SET `securityKey` = '"+securityKey+"' WHERE `phoneNumber` = '+63"+checkThisNumber.getText()+"'";
                int securityKeyUpdate = statement.executeUpdate(updateSecurityKey);
                if (securityKeyUpdate == 0){
                    System.out.println("Ok");
                }
                LoginController.forgotPasswordWindow.close();
                Parent parent = FXMLLoader.load(getClass().getResource("EnterConfirmationCode.fxml"));
                confirmCodeWindow.setScene(new Scene(parent));
                confirmCodeWindow.show();

            }else {
                System.out.println("What's the problem?");
                System.out.println(phoneNumberCheckQuery);
            }
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql);
            System.out.println("Yeah");
        }catch (Exception ex){
            System.out.println( ex);
        }
    }
}
