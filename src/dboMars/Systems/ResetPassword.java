package dboMars.Systems;

import dboMars.Database.Database;
import dboMars.Main;
import dboMars.Utilities.Security;
import dboMars.Utilities.SmsMessaging;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ResetPassword implements Initializable{
    SmsMessaging sms = new SmsMessaging();
    Database database = new Database();
    @FXML private PasswordField newPassword = new PasswordField();
    @FXML private PasswordField confirmNewPassword = new PasswordField();
    @FXML private Label resetResult = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetResult.setText("");
    }

    public void cancel(){
        EnterConfirmationCode.resetWindow.close();
        Main.window.show();
    }

    public void reset(){
        Security security = new Security();
        if (newPassword.getText().equals("") || confirmNewPassword.getText().equals("")){
            resetResult.setText("New Password and Confirm Password Require");
        }else if (newPassword.getText().equals(confirmNewPassword.getText())){
            try{
                database.subConnect();
                String updatePasswordQuery = "UPDATE `user` SET `password` = '"+ security.encrypt(newPassword.getText())+"'";
                Statement statement = Database.connection.createStatement();
                int updatePassword = statement.executeUpdate(updatePasswordQuery);
                if (updatePassword == 1){
                    Statement statementTwo = Database.connection.createStatement();
                    ResultSet resultSet = statementTwo.executeQuery("SELECT * FROM user");
                    if (resultSet.next()){
                        String userName = resultSet.getString("username");
                        String phoneNumber = resultSet.getString("phoneNumber");
                        //String password = security.decrypt(resultSet.getString("password"));
                        String message = "new password: "+newPassword.getText()+"%0a new username: "+userName;
                        sms.send(phoneNumber,message);
                    }
                }
                System.out.println(updatePassword+" Field Updated.");
                EnterConfirmationCode.resetWindow.close();
                Main.window.show();
                database.connection.close();
            }catch (SQLException sql){
                System.out.println(sql);
            }catch (Exception ex){
                System.out.println(ex);
            }
        }else {
            resetResult.setText("Password and Confirm Password does not Match");
        }
    }
}
