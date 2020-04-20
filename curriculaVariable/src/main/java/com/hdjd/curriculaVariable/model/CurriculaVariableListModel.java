package com.hdjd.curriculaVariable.model;

import java.util.List;

public class CurriculaVariableListModel {
    private String coName;
    private String accessType;
    private String courseType;
    private String semester;
    private int credit;
    private int amountTime;
    private String coId;
    private String status;
    private List<CvlChildModel> childModels;

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getAmountTime() {
        return amountTime;
    }

    public void setAmountTime(int amountTime) {
        this.amountTime = amountTime;
    }

    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    public List<CvlChildModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<CvlChildModel> childModels) {
        this.childModels = childModels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}