package data;

public class Student {
    private int id_number;
    private String username;
    private String password;

    public Student(int idNumber, String username, String password) {
        id_number = idNumber;
        this.username = username;
        this.password = password;
    }
    public int getId_number() { return id_number;}
    public void setId_number(int idNumber) { this.id_number = idNumber; }
    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password; }
}
