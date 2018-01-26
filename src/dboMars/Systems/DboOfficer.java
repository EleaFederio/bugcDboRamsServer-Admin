package dboMars.Systems;

import dboMars.Database.Database;
import dboMars.Database.Students;
import dboMars.Officers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DboOfficer implements Initializable{
    Database database = new Database();
    @FXML private VBox toogleOracle = new VBox();
    @FXML private VBox toogleOasia = new VBox();
    @FXML private VBox toogleBeda = new VBox();

    @FXML private TableView<Officers> officersTable = new TableView<>();
    @FXML private TableColumn<Officers, String> idField = new TableColumn<>();
    @FXML private TableColumn<Officers, String> firstNameField = new TableColumn<>();
    @FXML private TableColumn<Officers, String> lastNameField = new TableColumn<>();
    @FXML private TableColumn<Officers, String> courseField = new TableColumn<>();
    @FXML private TableColumn<Officers, Integer> yearField = new TableColumn<>();
    @FXML private TableColumn<Officers, Integer> blockField = new TableColumn<>();
    @FXML private TableColumn<Officers, String> positionField = new TableColumn<>();

    @FXML private ComboBox<String> positionSelector = new ComboBox();

    String id, firstName, lastName, course, block, position, organization;
    int year, mainId;

    static String courseSwitch = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toogleToOracle();
        positionSelector.getItems().addAll("President", "Vice-President", "Secretary", "PIO", "Treasurer", "Representative", "Student");
        idField.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        firstNameField.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameField.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        courseField.setCellValueFactory(new PropertyValueFactory<>("course"));
        yearField.setCellValueFactory(new PropertyValueFactory<>("year"));
        blockField.setCellValueFactory(new PropertyValueFactory<>("block"));
        positionField.setCellValueFactory(new PropertyValueFactory<>("position"));
        officersTable.getColumns().clear();
        officersTable.setItems(getStudents());
        officersTable.getColumns().addAll(idField, firstNameField, lastNameField, courseField, yearField, blockField, positionField);
    }

    public void selected(){
        Officers officers = officersTable.getSelectionModel().getSelectedItem();
        mainId = officers.getMainId();
        firstName = officers.getFirstName();
        lastName = officers.getLastName();
        course = officers.getCourse();
        year = officers.getYear();
        block = officers.getBlock();
        position = officers.getPosition();
    }

    public void assign(){
        selected();
        System.out.println("id number = "+mainId);
        int tempPosition = 0;
        String  tempAssignPossition = positionSelector.getSelectionModel().getSelectedItem().toString();
        System.out.println(tempAssignPossition+ " : "+tempPosition);
        if (tempAssignPossition.equals("President")){
            tempPosition = 2;
        }else if (tempAssignPossition.equals("Vice-President")){
            tempPosition = 3;
        }else if (tempAssignPossition.equals("Secretary")){
            tempPosition = 4;
        }else if (tempAssignPossition.equals("PIO")){
            tempPosition = 5;
        }else if(tempAssignPossition.equals("Treasurer")){
            tempPosition = 6;
        }else if (tempAssignPossition.equals("Representative")){
            tempPosition = 7;
        }else if(tempAssignPossition.equals("Student")){
            tempPosition = 1;
        }else {
            System.out.println("Position Error");
        }

        try {
            if(courseSwitch.equals("Oracle") && tempPosition > 1 && tempPosition < 7){
                String checkOfficerExist = "SELECT * FROM `students` WHERE `course` = 1 AND `position` = "+tempPosition+" ";
                database.subConnect();
                Statement checkOfficerStatement = database.connection.createStatement();
                ResultSet checkOfficerResult = checkOfficerStatement.executeQuery(checkOfficerExist);
                if (checkOfficerResult.next()){
                    JOptionPane.showMessageDialog(null, tempAssignPossition+" already exist");
                }else {
                    try{
                        database.subConnect();
                        String assignAsOfficer = "UPDATE students JOIN officers ON students.position = officers.positionId  SET position = "+tempPosition+" WHERE id = "+mainId+" ";
                        Statement statement = Database.connection.createStatement();
                        int affected = statement.executeUpdate(assignAsOfficer);
                        System.out.println(affected+" student Position updated!");
                        database.connection.close();
                    }catch (SQLException sql){

                    }
                }
            }else {
                try{
                    database.subConnect();
                    String assignAsOfficer = "UPDATE students JOIN officers ON students.position = officers.positionId  SET position = "+tempPosition+" WHERE id = "+mainId+" ";
                    Statement statement = Database.connection.createStatement();
                    int affected = statement.executeUpdate(assignAsOfficer);
                    System.out.println(affected+" student Position updated!");
                    database.connection.close();
                }catch (SQLException sql){

                }
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }


        officersTable.setItems(getStudents());
        positionSelector.setValue("");
    }

    public ObservableList<Officers> getStudents(){
        ObservableList<Officers> officers = FXCollections.observableArrayList();
        try{
            database.subConnect();
            if(courseSwitch.equals("Oracle")){
                System.out.println("--- ORACLE ---");
                String selectOracleStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 1";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectOracleStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }else if (courseSwitch.equals("Oasia")){
                System.out.println("--- OASIA ---");
                String selectOasiaStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 2";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectOasiaStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }else if (courseSwitch.equals("Beda")){
                System.out.println("--- BEEDA ---");
                String selectBedaStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 3 OR course = 4";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectBedaStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }else {
                System.out.println("--- No Course ---");
            }
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql);
        }
        return officers;
    }

    public void showOfficers(){
        officersTable.setItems(getOfficers());
    }

    public void showAllStudents(){
        officersTable.setItems(getStudents());
    }

    public ObservableList<Officers> getOfficers(){
        ObservableList<Officers> officers = FXCollections.observableArrayList();
        try {
            database.subConnect();
            if (courseSwitch.equals("Oracle")){
                String selectOracleStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 1 AND position > 1 ";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectOracleStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }else if (courseSwitch.equals("Oasia")){
                String selectOracleStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 2 AND position > 1 ";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectOracleStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }else if (courseSwitch.equals("Beda")){
                String selectOracleStudent = "SELECT id, studentId, firstName, lastName, course, year, block, studentPosition FROM students JOIN officers " +
                        "ON students.position = officers.positionId WHERE course = 3 OR course = 4 AND position > 1 ";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectOracleStudent);
                while (resultSet.next()){
                    mainId = resultSet.getInt("id");
                    id = resultSet.getString("studentId");
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    course = courseCoverter(resultSet.getInt("course"));
                    year = resultSet.getInt("year");
                    block = resultSet.getString("block");
                    position = resultSet.getString("studentPosition");
                    officers.add(new Officers(mainId, id, firstName, lastName, course, year, block, position));
                }
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return officers;
    }

    public String courseCoverter(int courseNumber){
        String courseProduct = "";
        if (courseNumber == 1){
            courseProduct = "BSCS";
        }else if (courseNumber == 2){
            courseProduct = "BAT";
        }else if (courseNumber == 3){
            courseProduct = "BEED";
        }else if (courseNumber == 4){
            courseProduct = "BSED";
        }
        return courseProduct;
    }

    public void toogleToOracle(){
        courseSwitch = "Oracle";
        toogleOracle.setStyle("-fx-background-color: rgb(100,100,100)");
        toogleOasia.setStyle("-fx-background-color: #000000");
        toogleBeda.setStyle("-fx-background-color: #000000");
        getStudents();
        officersTable.setItems(getStudents());
    }

    public void toogleToOasia(){
        courseSwitch = "Oasia";
        toogleOracle.setStyle("-fx-background-color: #000000");
        toogleOasia.setStyle("-fx-background-color: rgb(100,100,100)");
        toogleBeda.setStyle("-fx-background-color: #000000");
        getStudents();
        officersTable.setItems(getStudents());
    }

    public  void toogleToBeda(){
        courseSwitch = "Beda";
        toogleOracle.setStyle("-fx-background-color: #000000");
        toogleOasia.setStyle("-fx-background-color: #000000");
        toogleBeda.setStyle("-fx-background-color: rgb(100,100,100)");
        getStudents();
        officersTable.setItems(getStudents());
    }
}
