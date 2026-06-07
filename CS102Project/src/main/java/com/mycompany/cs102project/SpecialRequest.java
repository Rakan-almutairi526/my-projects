package com.mycompany.cs102project;

public class SpecialRequest {

    private String studentId;
    private String courseCode;
    private String message;
    private String status;
    private String advisorcomment;

    public SpecialRequest(String studentId, String courseCode, String message) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.message = message;
        this.status = "Pending";
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getAdvisorcomment() {
        return advisorcomment;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdvisorcomment(String advisorcomment) {
        this.advisorcomment = advisorcomment;
    }

    public void approveRequest(String comment) {
        setStatus("Approved");
        this.advisorcomment = comment;
    }

    public void denyRequest(String comment) {
        setStatus("declined");
        this.advisorcomment = comment;
    }

    @Override
    public String toString() {
        return "Special request\n" + "Student id: " + getStudentId() + "\ncourseCode: " + getCourseCode() + "\nStudent's message: " + getMessage() + "\nsStatus: " + getStatus() + "Advisor comment: ";
    }

}
