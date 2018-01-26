package dboMars.Systems;

import dboMars.Utilities.Security;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dboMars.Database.Database;
import dboMars.Main;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    Database database = new Database();
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
    @FXML private Label forgotPasswordLink;
    public static Stage mainWindow = new Stage();
    Security security = new Security();
    public static Stage forgotPasswordWindow = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(){
        String adminUsername = username.getText();
        String adminPassword = security.encrypt(password.getText());
        try {
            database.subConnect();
            Statement statement = database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()){
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                username.setText("");
                password.setText("");
                if(adminUsername.equals(dbUsername) && adminPassword.equals(dbPassword)){
                    System.out.println("Login Success!");
                    Main.window.close();
                    loadInterface();
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password");
                }
            }
            database.connection.close();
        }catch (SQLException sql){

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadInterface(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainProgram.fxml"));
            mainWindow.setScene(new Scene(root));
            mainWindow.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void forgotPassword(){
        try{
            Main.window.close();
            Parent root = FXMLLoader.load(getClass().getResource("ForgotPasswordUi.fxml"));
            forgotPasswordWindow.setScene(new Scene(root));
            forgotPasswordWindow.show();
        }catch (Exception e){
            System.out.println("Hello");
            System.out.println(e.getMessage());
        }
    }

    public void doSomething(){
        username.setText("Hello");
    }

    public void cancelLogin(){
        Main.window.close();
    }

    public void changeColorEntered(){
        loginButton.setStyle("-fx-background-color: linear-gradient(#ffffff, #9999ff)");
    }

    public void changeColorExit(){
        loginButton.setStyle("-fx-background-color: linear-gradient(#9999ff, #ffffff)");
    }

    public void forgetPasswordChangeColor(){
        forgotPasswordLink.setStyle("-fx-text-fill: red");
    }
}
