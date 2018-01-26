package dboMars.Systems;

import dboMars.Database.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import dboMars.Main;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainProgram implements Initializable{
    Database database = new Database();
    @FXML private Pane setting = new Pane();
    @FXML private Pane logout = new Pane();
    @FXML private Pane record = new Pane();
    @FXML private BorderPane mainWindow = new BorderPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void settingClick(){
        try {
            Parent settingScene = FXMLLoader.load(getClass().getResource("setting.fxml"));
            mainWindow.setCenter(new SubScene(settingScene, 545, 440));
            settingExit();
            System.out.println("--- Account Setting ---");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void recordClick(){
        try {
            Parent recordScene = FXMLLoader.load(getClass().getResource("RecordWindow.fxml"));
            mainWindow.setCenter(new SubScene(recordScene, 545, 440));
            System.out.println("--- Record Student ---");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void assignOfficer(){
        try {
            Parent assignOfficerScene = FXMLLoader.load(getClass().getResource("DboOfficer.fxml"));
            mainWindow.setCenter(new SubScene(assignOfficerScene, 545, 440));
            System.out.println("--- Assign Officer ---");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void registerClick(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("biometricRecord.fxml"));
            mainWindow.setCenter(new SubScene(root, 545, 440));
            System.out.println("--- Biometric Record ---");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void logout(){
        LoginController.mainWindow.close();
        Main.window.show();
        System.out.println("--- Logout ---");
    }

    public void settingHover(){
        setting.setStyle("-fx-background-color:  rgb(130, 130, 150)");
    }

    public void settingExit(){
        setting.setStyle("-fx-background-color:  rgb(150, 150, 150)");
    }

    public void recordHover(){
        record.setStyle("-fx-background-color:  rgb(130, 130, 150)");
    }

    public void recordExit(){
        record.setStyle("-fx-background-color:  rgb(150, 150, 150)");
    }

    public void logoutHover(){
        logout.setStyle("-fx-background-color:  rgb(130, 130, 150)");
    }

    public void logoutExit(){
        logout.setStyle("-fx-background-color:  rgb(150, 150, 150)");
    }
}
