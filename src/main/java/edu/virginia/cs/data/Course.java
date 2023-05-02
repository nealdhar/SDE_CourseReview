package edu.virginia.cs.data;

public class Course {
    private String department;
    private int catalog_number;

    public Course(String department, int catalog_number) {
        this.department = department;
        this.catalog_number = catalog_number;
    }
    public String getDepartment() { return department;}
    public void setDepartment(String department) { this.department = department; }
    public int getCatalogNumber() { return catalog_number;}
    public void setCatalogNumber(int catalog_number) { this.catalog_number = catalog_number; }
}
