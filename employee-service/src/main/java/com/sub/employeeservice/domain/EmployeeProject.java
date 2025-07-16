package com.sub.employeeservice.api;


import java.util.Date;


public class EmployeeProject {
    private int empId;
    private int projectId;
    private Date dateFrom;
    private Date dateTo;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public EmployeeProject(int empId, int projectId,
                           Date dateFrom, Date dateTo) {
        this.empId = empId;
        this.projectId = projectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public EmployeeProject() {
    }

    public static long calculateOverlapDays(Date emp1Start, Date emp1End, Date emp2Start, Date emp2End) {
        Date overlapStart = emp1Start.after(emp2Start) ? emp1Start : emp2Start;
        Date overlapEnd = emp1End.before(emp2End) ? emp1End : emp2End;

        if (overlapStart.after(overlapEnd)) {
            return 0;
        }

        long diffInMillis = overlapEnd.getTime() - overlapStart.getTime();
        return (diffInMillis / (1000 * 60 * 60 * 24)) + 1;
    }
}