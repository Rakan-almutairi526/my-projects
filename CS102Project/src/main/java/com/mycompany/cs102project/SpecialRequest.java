package com.mycompany.cs102project;

public class SpecialRequest {

    private String Id;
    private String studentId;
    private String courseCode;
    private String message;
    private String status;
    private String advisorComment;

    public SpecialRequest(String studentId, String courseCode, String message) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.message = message;
        this.status = "Pending";
        this.Id = "REQ0" + (DataManager.specialRequestsList.size() + 1);
        this.advisorComment = "None";
    }

    public SpecialRequest(String id, String studentId, String courseCode, String message, String status, String advisorComment) {
        Id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.message = message;
        this.status = status;
        this.advisorComment = advisorComment;
    }

    public String getId() {return Id;}

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

    public String getAdvisorComment() {
        return advisorComment;
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

    public void setAdvisorComment(String advisorComment) {
        this.advisorComment = advisorComment;
    }

    public void approveRequest(String comment) {
        setStatus("Approved");
        this.advisorComment = comment;
    }

    public void denyRequest(String comment) {
        setStatus("Declined");
        this.advisorComment = comment;
    }

    @Override
    public String toString() {
        return "*Special request* Id: " + getId() +" Student id: " + getStudentId() + " courseCode: " + getCourseCode() + " Student's message: " + getMessage() + " Status: " + getStatus() + " Advisor comment: " + getAdvisorComment();
    }

}
