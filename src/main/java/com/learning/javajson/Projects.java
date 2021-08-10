package com.learning.javajson;

public class Projects {
    private String projectId;
    private String projectDescription;
    private boolean projectStatus;

    public Projects(String projectId, String projectDescription, boolean projectStatus){
        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public boolean isProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(boolean projectStatus) {
        this.projectStatus = projectStatus;
    }
}
