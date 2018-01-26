package dboMars.Systems;

import dboMars.Database.Database;
import dboMars.Database.Students;
import dboMars.Utilities.SecretKey;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RecordWindow implements Initializable{
    Database database = new Database();
    @FXML private TableView<Students> studentTable = new TableView();
    @FXML private TableColumn<Students, String> firstNameColumn = new TableColumn();
    @FXML private TableColumn<Students, String> lastNameColumn = new TableColumn();
    @FXML private TableColumn<Students, String> courseColumn = new TableColumn();
    @FXML private TableColumn<Students, Integer> yearColumn = new TableColumn();
    @FXML private TableColumn<Students, String> blockColumn = new TableColumn();
    @FXML private TableColumn<Students, String> hashColumn = new TableColumn();
    @FXML private TableColumn<Students, String> studentIdColumn = new TableColumn();
    @FXML private TextField firstNameField = new TextField();
    @FXML private TextField lastNameField = new TextField();
    @FXML private ComboBox<String> courseField = new ComboBox<>();
    @FXML private ComboBox<Integer> yearField = new ComboBox<>();
    @FXML private ComboBox<String> blockField = new ComboBox<>();
    @FXML private TextField studentIdField = new TextField();
    String firstName, lastName, course, block, hash, studentId, tempCourseString, tempCourseStringII;
    int year, mainId, tempCourseII;
    static int tempCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseField.getItems().addAll("BSCS", "BAT", "BEED", "BSED");
        yearField.getItems().addAll(1, 2, 3, 4, 5);
        blockField.getItems().addAll("A", "B", "C", "D");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        blockColumn.setCellValueFactory(new PropertyValueFactory<>("block"));
        hashColumn.setCellValueFactory(new PropertyValueFactory<>("hash"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentTable.getColumns().clear();
        studentTable.setItems(getStudentInfo());
        studentTable.getColumns().addAll(firstNameColumn, lastNameColumn, courseColumn, yearColumn, blockColumn, hashColumn, studentIdColumn);
        clearUi();
    }

    public void clearUi(){
        firstNameField.clear();
        lastNameField.clear();
        courseField.getSelectionModel().clearSelection();
        yearField.getSelectionModel().clearSelection();
        blockField.getSelectionModel().clearSelection();
        studentIdField.clear();
        mainId = 0;
        firstName = "";
        lastName = "";
        course = "";
        year = 0;
        block = "";
    }


    public void add(){
        String addStudent = null;
        if(firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || courseField.getSelectionModel().isEmpty() || yearField.getSelectionModel().isEmpty() || blockField.getSelectionModel().isEmpty() || studentIdField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "All fields needs to be fill!");
            clearUi();
        }else{
            try{
                database.subConnect();
                String duplicateStudentCancelation = "SELECT * FROM `students` WHERE `id` = "+mainId+" ";
                Statement statement = Database.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(duplicateStudentCancelation);
                if (resultSet.next()){
                    JOptionPane.showMessageDialog(null, "Student already Exist!");
                }else {
                    join();
                    try {
                        addStudent = "INSERT INTO `students` (`firstName`, `lastName`, `course`, `year`, `block`, `hash`, `studentId`, `position`) VALUES " +
                                "('"+firstNameField.getText()+"', '"+lastNameField.getText()+"', "+tempCourse+", " +
                                ""+yearField.getSelectionModel().getSelectedItem().intValue()+", '"+blockField.getSelectionModel().getSelectedItem().toString()+"', '"+ SecretKey.generate(8)+"', '"+studentIdField.getText()+"', 1)";
                        Statement statement2 = Database.connection.createStatement();
                        int affected = statement2.executeUpdate(addStudent);
                        if (affected == 0){
                            System.out.println(affected+" added into student table");
                        }
                        studentTable.setItems(getStudentInfo());
                        clearUi();
                    }catch (SQLException sql){
                        System.out.println(sql);
                        System.out.println(addStudent);
                        System.out.println(courseField.getSelectionModel().getSelectedItem().toString());
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
                database.connection.close();
            }catch (SQLException ex){
                System.out.println(ex+ "line 73");
            }
        }
    }

    public void update(){
        if (firstNameField.getText().equals(null) && lastNameField.getText().equals(null) && courseField.getValue().equals(null) &&
                yearField.getValue().toString().equals(null) && blockField.getValue().equals(null) && studentIdField.getText().equals(null)){
            JOptionPane.showMessageDialog(null, "Nothing to update!");
        }else {
            join();
            try {
                database.subConnect();
                String updateQuery = "UPDATE `students` JOIN `courses` ON `students`.`course` = `courses`.`courseId`  SET `firstName` = '"+firstNameField.getText()+"', `lastName` = '"+lastNameField.getText()+"', `course` = "+tempCourse+", " +
                        "`year` = "+yearField.getSelectionModel().getSelectedItem().intValue()+", `block` = '"+blockField.getSelectionModel().getSelectedItem().toString()+"', `studentId` = '"+studentIdField.getText()+"' " +
                        "WHERE `id` = '"+mainId+"' ";
                Statement statement = Database.connection.createStatement();
                int updatedStudent = statement.executeUpdate(updateQuery);
                if (updatedStudent == 1){
                    JOptionPane.showMessageDialog(null,updatedStudent + " student updated!");
                }
                studentTable.setItems(getStudentInfo());
                clearUi();
                database.connection.close();
            }catch (SQLException sql){
                System.out.println(sql.getErrorCode());
            }catch (RuntimeException runEx){
                JOptionPane.showMessageDialog(null, "Nothing to update!");
            }
        }
    }

    public void join(){
        tempCourseString = courseField.getSelectionModel().getSelectedItem().toString();
        if (tempCourseString.equals("BSCS")){
            tempCourse = 1;
        }else if(tempCourseString.equals("BAT")){
            tempCourse = 2;
        }else if (tempCourseString.equals("BEED")){
            tempCourse = 3;
        }else if (tempCourseString.equals("BSED")){
            tempCourse = 4;
        }else {
            System.out.println("Error!");
        }
    }

    public void select(){

        Students students = studentTable.getSelectionModel().getSelectedItem();
        mainId = students.getMainId();
        firstName = students.getFirstName();
        lastName = students.getLastName();
        course = students.getCourse();
        year = students.getYear();
        block = students.getBlock();
        hash = students.getHash();
        studentId = students.getStudentId();
        tempCourseII = students.getTempCourse();

        //********************//
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        courseField.setValue(course);
        yearField.setValue(year);
        blockField.setValue(block);
        studentIdField.setText(studentId);

        //********************//
        System.out.println(mainId+" "+firstName+" "+lastName+" "+course+" "+year+" "+block+" "+hash+" "+studentId);
    }

    public void delete(){
        try{
            database.subConnect();
            String deleteThis = "DELETE FROM `students` WHERE `firstName` = '"+firstNameField.getText()+"' AND `lastName` = '"+lastNameField.getText()+"' ";
            Statement statement = Database.connection.createStatement();
            int deleted = statement.executeUpdate(deleteThis);
            if (deleted == 1){
                JOptionPane.showMessageDialog(null, deleted+" account deleted");
            }
            clearUi();
            studentTable.setItems(getStudentInfo());
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql.getErrorCode());
        }catch (Exception ex){
            System.out.println(ex);
        }
    }


    public ObservableList<Students> getStudentInfo(){
        ObservableList<Students> students = FXCollections.observableArrayList();
        try {
            database.subConnect();
            String selectStudents = "SELECT * FROM students JOIN courses ON students.course = courses.courseId";
            Statement statement = Database.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectStudents);
            while (resultSet.next()){
                mainId = resultSet.getInt("id");
                String dbFirstName = resultSet.getString("firstName");
                String dbLastName = resultSet.getString("lastName");
                int dbTempCorse = resultSet.getInt("course");
                String dbCourse = resultSet.getString("courseName");
                int dbYear = resultSet.getInt("year");
                String dbBlock = resultSet.getString("block");
                String dbHash = resultSet.getString("hash");
                String dbStudentId = resultSet.getString("studentId");
                students.add(new Students(mainId, dbFirstName, dbLastName, dbCourse, dbYear, dbBlock, dbHash, dbStudentId, dbTempCorse));
            }
            database.connection.close();
        }catch (SQLException sql){
            System.out.println(sql.getErrorCode());
        }
        return students;
    }
}
