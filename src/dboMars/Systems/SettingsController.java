package dboMars.Systems;

import dboMars.Main;
import dboMars.Utilities.Security;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dboMars.Database.Database;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{
    Database database = new Database();
    @FXML private Button updateButton = new Button();
    @FXML private TextField firstName = new TextField();
    @FXML private TextField lastName = new TextField();
    @FXML private TextField username = new TextField();
    @FXML private TextField phoneNumber = new TextField();
    @FXML private PasswordField oldPassword = new PasswordField();
    @FXML private PasswordField newPassword = new PasswordField();
    @FXML private PasswordField confirmNewPassword = new PasswordField();
    @FXML private Label profileErrorMessage = new Label();
    Stage mainWindow = new Stage();
    Security security = new Security();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profileErrorMessage.setText("");
        updateButton.setOnAction(event -> {
            try {
                database.subConnect();
                Statement statement = Database.connection.createStatement();
                String adminFirstName = firstName.getText();
                String adminLastName = lastName.getText();
                String adminUsername = username.getText();
                String adminPhoneNumber = phoneNumber.getText();
                String adminOldPassword = security.encrypt(oldPassword.getText());
                String adminNewPassword = security.encrypt(newPassword.getText());
                String adminConfirmNewPassword = security.encrypt(confirmNewPassword.getText());

                if (adminFirstName.equals("") || adminLastName.equals("") || adminUsername.equals("") || adminPhoneNumber.equals("") ||
                        !adminOldPassword.equals("") && adminNewPassword.equals("") || adminConfirmNewPassword.equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill-up all fields");
                    firstName.setText("");
                    lastName.setText("");
                    username.setText("");
                    phoneNumber.setText("");
                    oldPassword.setText("");
                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }else{
                    if(adminNewPassword.equals(adminConfirmNewPassword)){
                        String updateProfileQuery = "UPDATE user SET `firstName`='"+adminFirstName+"', `lastName`='"+adminLastName+"', `username`='"+adminUsername+"', " +
                                "`phoneNumber`='+63"+adminPhoneNumber+"', `securityKey`='123', `password`='"+adminNewPassword+"' WHERE `password`='"+adminOldPassword+"'";
                        int countUpdate = statement.executeUpdate(updateProfileQuery);
                        if (countUpdate == 1){
                            JOptionPane.showMessageDialog(null, "Admin Username and password has been updated");
                        }
                        firstName.setText("");
                        lastName.setText("");
                        username.setText("");
                        phoneNumber.setText("");
                        oldPassword.setText("");
                        newPassword.setText("");
                        confirmNewPassword.setText("");
                        LoginController.mainWindow.close();
                        Main.window.show();
                    }else {
                        JOptionPane.showMessageDialog(null, "New Password and Confirm New Password does not match!");
                        System.out.println(adminNewPassword+" : "+adminConfirmNewPassword);
                    }
                }
            database.connection.close();
            }catch (SQLException sql){
                System.out.println(sql.getErrorCode());
            }
        });
    }
}
