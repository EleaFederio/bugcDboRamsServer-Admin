package dboMars.Database;

public class Students {
    private String firstName, lastName, course, block, hash, studentId;
    private int mainId, year, tempCourse;

    public Students(int mainId, String firstName, String lastName, String course, int year, String block, String hash, String studentId, int tempCourse) {
        this.mainId = mainId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.block = block;
        this.hash = hash;
        this.studentId = studentId;
        this.year = year;
        this.tempCourse = tempCourse;
    }

    public int getMainId(){return mainId; }

    public void setMainId(int mainId){this.mainId = mainId; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTempCourse() {
        return tempCourse;
    }

    public void setTempCourse(int tempCourse) {
        this.tempCourse = tempCourse;
    }
}
