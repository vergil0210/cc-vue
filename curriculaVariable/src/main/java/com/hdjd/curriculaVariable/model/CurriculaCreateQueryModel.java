package com.hdjd.curriculaVariable.model;

import java.util.List;

/**
 * @author Administrator
 */
public class CurriculaCreateQueryModel {
    private List<Long> coIds;
    private List<Long> teaIds;
    private String college;
    private int grade;
    private int startTime;
    private int endTime;
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<Long> getCoIds() {
        return coIds;
    }

    public void setCoIds(List<Long> coIds) {
        this.coIds = coIds;
    }

    public List<Long> getTeaIds() {
        return teaIds;
    }

    public void setTeaIds(List<Long> teaIds) {
        this.teaIds = teaIds;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}