package dboMars.Database;

import java.sql.*;

public class Database {
    public static Connection connection;
    public static Statement statement;
    private String host, user, password;


    public void connect(){
       /* this.host = "jdbc:mysql://sql12.freemysqlhosting.net/sql12216057";
        this.user = "sql12216057";
        this.password = "Qu5yvfUmw8";*/
        this.host = "jdbc:mysql://localhost/bugc";
        this.user = "root";
        this.password = "2n3904";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.host, this.user, this.password);
            String selectUserQuery = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectUserQuery);
            System.out.println("+   Connection Check √   +");
            if (resultSet.next()){
                System.out.println("User exist");
            }
                try {
                    String checkStudentDatabase = "SELECT * FROM students";
                    ResultSet resultSet1 = statement.executeQuery(checkStudentDatabase);
                    resultSet1.next();
                }catch (SQLException sql){
                    System.out.println(sql.getErrorCode());
                    if (sql.getErrorCode() == 1146){
                        System.out.println("Student table does not exist");
                        System.out.println("Creating Student table...");
                        createStudentDatabase();
                        System.out.println("Student table created!");
                    }
                }
                connection.close();
        }catch (ClassNotFoundException cnf){
            System.out.println("Driver not Found!");
        }catch (SQLException sql){
            int errorNumber = sql.getErrorCode();
            System.out.println("error number "+sql.getErrorCode());
            if (errorNumber == 1049){
                System.out.println("Database does not exist.");
                System.out.println("Creating database...");
                createNewDatabase();
            }else if (errorNumber == 1146){
                System.out.println("User Table does not exist.");
                System.out.println("Creating user table...");
                createAdminTable();
            }else if (errorNumber == 1045){
                System.out.println("Database Password error");
                System.out.println("This App is intended for Linux Machine");
            }
        }
    }

    public void subConnect(){
        /*this.host = "jdbc:mysql://sql12.freemysqlhosting.net/sql12216057";
        this.user = "sql12216057";
        this.password = "Qu5yvfUmw8";*/
        this.host = "jdbc:mysql://localhost/bugc";
        this.user = "root";
        this.password = "2n3904";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.host, this.user, this.password);
            System.out.println("+   Connection Check √   +");
        }catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void createNewDatabase(){
        /*this.host = "jdbc:mysql://sql12.freemysqlhosting.net";
        this.user = "sql12216057";
        this.password = "Qu5yvfUmw8";*/
        this.host = "jdbc:mysql://localhost/";
        this.user = "root";
        this.password = "2n3904";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(this.host, this.user, this.password);
            statement = connection.createStatement();
            String createDbQuery = "CREATE DATABASE bugc";
            int countAffected = statement.executeUpdate(createDbQuery);
            if (countAffected == 0){
                System.out.println("Database Created");
            }
            connect();
            connection.close();
        }catch (ClassNotFoundException cnf){

            System.out.println("Driver not Found!");
        }catch (SQLException sql){
            int error = sql.getErrorCode();
        }
    }

    public void createAdminTable(){
        try {
            subConnect();
            Statement statement = connection.createStatement();
            String createUserTableQuery = "CREATE TABLE user (firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, "+
                    "username VARCHAR(20) NOT NULL, phoneNumber VARCHAR(13) NOT NULL, securityKey VARCHAR(8) NOT NULL," +
                    "password VARCHAR(225) NOT NULL)";
            int countCreatedTable = statement.executeUpdate(createUserTableQuery);
            if (countCreatedTable == 0){
                System.out.println("Default user table created");
            }
            System.out.println("Creating default username and password...");
            createDefaultUser();
            connect();
            connection.close();
        }catch (SQLException sql){
            System.out.println(sql.getErrorCode());
        }
    }

    public void createDefaultUser(){
        try {
            subConnect();
            Statement statement = connection.createStatement();
            String createUserTableQuery = "INSERT INTO user VALUES('', '', 'admin', '', '', '21232f297a57a5a743894a0e4a801fc3')";
            int countCreatedTable = statement.executeUpdate(createUserTableQuery);
            if (countCreatedTable == 1){
                System.out.println("Default user now exist");
            }
            createStudentDatabase();
            connection.close();
        }catch (SQLException sql){
            System.out.println(sql.getErrorCode());
        }
    }

    public void createStudentDatabase(){
        try {
            subConnect();
            String createTable = "CREATE TABLE `students` (id INT(11) NOT NULL AUTO_INCREMENT, firstName VARCHAR(30) NOT NULL," +
                    "lastName VARCHAR(30) NOT NULL, course INT(1) NOT NULL, year INT(1) NOT NULL, block VARCHAR(1) NOT NULL," +
                    "hash VARCHAR(12) NOT NULL, studentId VARCHAR(12) NOT NULL, appUserName VARCHAR(50), " +
                    "phoneNumber VARCHAR(13), appPassword VARCHAR(225), position INT(20) NOT NULL, rightThumb TEXT, PRIMARY KEY(id))";
            Statement statement = connection.createStatement();
            int created = statement.executeUpdate(createTable);
            if (created == 0){
                System.out.println("Student table created");
            }
            createOfficerPosition();
            connection.close();
        }catch (SQLException sql){
            System.out.println(sql);
            System.out.println(sql.getErrorCode());
        }
    }

    public void createOfficerPosition(){
        String createOfficerTable = "CREATE TABLE `officers` (positionId INT(1) NOT NULL AUTO_INCREMENT, studentPosition VARCHAR(20) NOT NULL," +
                "PRIMARY KEY(positionId))";
        try{
            subConnect();
            Statement statement = connection.createStatement();
            int affected = statement.executeUpdate(createOfficerTable);
            if (affected == 0){
                System.out.println("Officer table created");
            }
            insertPositions();
            createCourseTable();
            connection.close();
        }catch (SQLException sql){
            System.out.println(sql.getErrorCode());
        }
    }

    public void insertPositions(){
        String [] position;
        position = new String[]{"Student", "President", "Vice-President", "Secretary", "PIO", "Treasurer", "Representative"};
        for (int x = 0; x < 7; x++){
            try {
                subConnect();
                String insertThisPosition = "INSERT INTO `officers` (`studentPosition`) VALUES ('"+position[x]+"')";
                Statement statement = connection.createStatement();
                int inserted = statement.executeUpdate(insertThisPosition);
                if (inserted == 1){
                    System.out.println("Student "+position[x]+" Exist");
                }
                connection.close();
            }catch (SQLException sql){
                System.out.println(sql.getErrorCode());
            }
        }
    }

    public void createCourseTable(){
        String courseTableCreationQuery = "CREATE TABLE `courses` (`courseId` INT(11) NOT NULL AUTO_INCREMENT, `courseName` VARCHAR(30) NOT NULL, " +
                "`organization` VARCHAR(30) NOT NULL, PRIMARY KEY(`courseId`))";
        try{
            subConnect();
            Statement statement = connection.createStatement();
            int created = statement.executeUpdate(courseTableCreationQuery);
            if (created == 0){
                System.out.println("Course table created");
            }
            insertCourses();
            connection.close();
        }catch (SQLException sql){
            System.out.println("Error: createCourseTable() = error number("+sql+")");
            System.out.println("query: "+courseTableCreationQuery);
        }catch (Exception ex){
            System.out.println("createCourseTable() Error: "+ex);
        }
    }

    public void insertCourses(){
        String [] course,organization ;
        course = new String[]{"BSCS", "BAT", "BEED", "BSED", ""};
        organization = new String[]{"ORACLE", "OASIA", "BEEDA", "BEEDA"};
        for (int x = 0; x < 4; x++){
            String insertThisCourse = "INSERT INTO `courses` (`courseName`, `organization`) VALUES ('"+course[x]+"', '"+organization[x]+"')";
            try {
                subConnect();
                Statement statement = connection.createStatement();
                int inserted = statement.executeUpdate(insertThisCourse);
                if (inserted == 1){
                    System.out.println("Course: "+course[x]+" Organization: "+organization[x]+" exist");
                }
                connection.close();
            }catch (SQLException sql){
                System.out.println(sql);
                System.out.println(insertThisCourse);
            }
        }
    }

    public void registerStudentFinger(String studentId, byte [] template){
        String recordBiometrics = "INSERT INT0";
    }
}
