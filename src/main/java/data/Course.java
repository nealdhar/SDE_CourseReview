package data;

public class Course {
    private int id_number;
    private String department;
    private int catalog_number;

    public Course(int idNumber, String department, int catalog_number) {
        this.id_number = idNumber;
        this.department = department;
        this.catalog_number = catalog_number;
    }
    public int getId_number() { return id_number;}
    public void setId_number(int idNumber) { this.id_number = idNumber; }
    public String getDepartment() { return department;}
    public void setDepartment(String department) { this.department = department; }
    public int getCatalogNumber() { return catalog_number;}
    public void setCatalogNumber(int catalog_number) { this.catalog_number = catalog_number; }
}
