package dboMars;

public class Officers {
    private String studentId, firstName, lastName, course, block, position;
    private int mainId, year;

    public Officers(int mainId, String studentId, String firstName, String lastName, String course, int year, String block, String position) {
        this.mainId = mainId;
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.block = block;
        this.year = year;
        this.position = position;
    }

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int mainId) {
        this.mainId = mainId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
