package com.learning.javajson;

import java.util.ArrayList;
import java.util.List;

public class Employees {
    private String name;
    private String email;
    private boolean status;
    private ArrayList<Projects> projects;

    public Employees(){
        super();
    }


    public Employees(String name, String email, boolean status, ArrayList<Projects> projects) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Projects> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Projects> projects) {
        this.projects = projects;
    }
}
