package dboMars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import dboMars.Database.Database;
import dboMars.Utilities.SecretKey;

public class Main extends Application {
    public static Stage window = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Systems/loginGUI.fxml"));
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(new Scene(root));
        window.show();
    }



    public static void main(String[] args) {
        Database database = new Database();
        database.connect();
        SecretKey.generate(8);
        launch(args);
    }
}
