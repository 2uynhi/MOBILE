package com.lehuuquynhnhi.models;

import java.io.Serializable;

public class Employee implements Serializable {
    private String employeeId;
    private String employeeName;
    private String phoneNumber;
    private String birthplace;

    public Employee(String employeeId, String employeeName, String phoneNumber, String birthplace) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
        this.birthplace = birthplace;
    }

    public Employee(String employeeId, String employeeName, String phoneNumber) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.phoneNumber = phoneNumber;
    }

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthplace='" + birthplace + '\'' +
                '}';
    }
}
