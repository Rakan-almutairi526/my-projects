package com.mycompany.cs102project;

public class SpecialRequest {

    private static int nextRequestNumber = 1;
    private String Id;
    private String studentId;
    private String courseCode;
    private String message;
    private String status;
    private String advisorComment;

    public SpecialRequest(String studentId, String courseCode, String message)
    {
        this.Id = String.format("REQ%02d", nextRequestNumber++);
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.message = message;
        this.status = "Pending";
        this.advisorComment = "None";
    }

    public SpecialRequest(String id, String studentId, String courseCode, String message, String status, String advisorComment) {
        this.Id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.message = message;
        this.status = status;
        this.advisorComment = advisorComment;

        updateNextRequestNumber(id);
    }
    private static void updateNextRequestNumber(String requestId)
    {
        try {
            int loadedNumber = Integer.parseInt(requestId.substring(3));
            if (loadedNumber >= nextRequestNumber) {
                nextRequestNumber = loadedNumber + 1;
            }
        } catch (Exception e) {
        }
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

    @Override
    public String toString() {
        return "*Special request* Id: " + getId() +" Student id: " + getStudentId() + " courseCode: " + getCourseCode() + " Student's message: " + getMessage() + " Status: " + getStatus() + " Advisor comment: " + getAdvisorComment();
    }

}
