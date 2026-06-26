package com.lehuuquynhnhi.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Department {
    private String departmentId;
    private String departmentName;

    private ArrayList<Employee> listOfEmployee = new ArrayList<>();

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.departmentName;
    }

    public void addEmployee(Employee emp){
        this.listOfEmployee.add(emp);
    }

    public void addListEmployee(ArrayList<Employee> list) {
        this.listOfEmployee.addAll(list);
    }

    public ArrayList<Employee> getListOfEmployee(){
        return this.listOfEmployee;
    }
}
