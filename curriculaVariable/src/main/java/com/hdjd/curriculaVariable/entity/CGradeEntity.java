package com.hdjd.curriculaVariable.entity;

import javax.persistence.*;

@Entity
@Table(name = "c_grade")
public class CGradeEntity {

    private String stsId;
    private Integer grade;
    private CStudentEntity student;
    private CTeacherCourseEntity tc;

    @Id
    @Column(name = "sts_id")
    public String getStsId() {
        return stsId;
    }

    public void setStsId(String stsId) {
        this.stsId = stsId;
    }


    @Column(name = "grade")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    public CStudentEntity getStudent() {
        return student;
    }

    public void setStudent(CStudentEntity student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "tc_id")
    public CTeacherCourseEntity getTc() {
        return tc;
    }

    public void setTc(CTeacherCourseEntity tc) {
        this.tc = tc;
    }
}