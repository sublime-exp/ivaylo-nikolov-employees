package com.sub.employeeservice.api;

import java.util.Objects;

public class ProjectResponse {
    private int employeeId1;
    private int employeeId2;
    private int projectId;
    private int daysWorked;

    public ProjectResponse(int employeeId1, int employeeId2, int projectId, int daysWorked) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
        this.projectId = projectId;
        this.daysWorked = daysWorked;
    }

    public int getEmployeeId1() {
        return employeeId1;
    }

    public void setEmployeeId1(int employeeId1) {
        this.employeeId1 = employeeId1;
    }

    public int getEmployeeId2() {
        return employeeId2;
    }

    public void setEmployeeId2(int employeeId2) {
        this.employeeId2 = employeeId2;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    public ProjectResponse() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectResponse that = (ProjectResponse) o;
        return employeeId1 == that.employeeId1 &&
                employeeId2 == that.employeeId2 &&
                projectId == that.projectId &&
                daysWorked == that.daysWorked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId1, employeeId2, projectId, daysWorked);
    }

    @Override
    public String toString() {
        return "ProjectResponse{" +
                "employeeId1=" + employeeId1 +
                ", employeeId2=" + employeeId2 +
                ", projectId=" + projectId +
                ", daysWorked=" + daysWorked +
                '}';
    }
}
