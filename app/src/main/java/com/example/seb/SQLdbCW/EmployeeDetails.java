package com.example.seb.SQLdbCW;

public class EmployeeDetails {
    private String employeeName;
    private String niNumber;

    public EmployeeDetails(String niNumber, String employeeName){
        this.niNumber = niNumber;
        this.employeeName = employeeName;
    }

    public String getNiNumber() {
        return niNumber;
    }

    public String getEmployeeName() { return employeeName; }

    @Override
    public String toString() {
        return niNumber +  " - "+ employeeName;
    }
}
