package com.hdjd.curriculaVariable.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Entity
@Table(name = "c_teacher_course", schema = "course_selection", catalog = "")
public class CTeacherCourseEntity implements Serializable {
    private static final long serialVersionUID = 6507949034673857323L;
    private String tcId;
    private CCourseEntity course;
    private CTeacherEntity teacher;
    private int currentUser;
    private String status;
    private Integer total;
    private int grade;
    private int startTime;
    private int endTime;
    private String college;

    @Id
    @Column(name = "tc_id")
    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    public CCourseEntity getCourse() {
        return course;
    }

    public void setCourse(CCourseEntity course) {
        this.course = course;
    }

//    @ManyToOne(targetEntity = CTeacherEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @ManyToOne
    @JoinColumn( name = "teacher_id")
    public CTeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(CTeacherEntity teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "current__user")
    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "total")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CTeacherCourseEntity that = (CTeacherCourseEntity) o;

        if (currentUser != that.currentUser) return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;
        if (course != null ? !course.equals(that.course) : that.course != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacher != null ? teacher.hashCode() : 0;
        result = 31 * result + (course != null ? course.hashCode() : 0);
        result = 31 * result + currentUser;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "grade")
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "start_time")
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "college")
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}