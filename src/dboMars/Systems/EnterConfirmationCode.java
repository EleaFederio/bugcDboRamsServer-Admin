package dboMars.Systems;

import dboMars.Database.Database;
import dboMars.Systems.ForgotPasswordController;
import dboMars.Utilities.SecretKey;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EnterConfirmationCode implements Initializable {
    Database database = new Database();
    @FXML private Label confirmationResult = new Label();
    @FXML private TextField confirmationCodeField = new TextField();
    ForgotPasswordController forgotPasswordController = new ForgotPasswordController();
    static Stage resetWindow = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmationResult.setText("");
    }

    public void resend(){
        try{
            database.subConnect();
            Statement statement = Database.connection.createStatement();

            String securityKey = SecretKey.generate(8);
            String updateSecurityKey = "UPDATE `user` SET `securityKey` = '"+securityKey+"' ";
            int securityKeyUpdate = statement.executeUpdate(updateSecurityKey);
            System.out.println(updateSecurityKey);
            System.out.println(securityKeyUpdate+" Field Updated.");
            System.out.println("Your confirmation key is: "+securityKey+" ");
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void confirm(){
        try {
            database.subConnect();
            String selectSameSecurityKey = "SELECT * FROM `user` WHERE securityKey = '"+confirmationCodeField.getText()+"'";
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSameSecurityKey);
            if (resultSet.next()){
                System.out.println("Confirmation Code Match");
                ForgotPasswordController.confirmCodeWindow.close();
                Parent parent = FXMLLoader.load(getClass().getResource("ResetPassword.fxml"));
                resetWindow.setScene(new Scene(parent));
                resetWindow.show();

            }else {
                confirmationResult.setText("Confirmation Code didn't Match!");
            }
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql);
        }catch (Exception ex){

        }
    }
}
