package com.hdjd.curriculaVariable.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "c_course", schema = "course_selection", catalog = "")
public class CCourseEntity implements Serializable {
    private static final long serialVersionUID = -5479897530857581209L;
    private String courseId;
    private String name;
    private String assessType;
    private int credit;
    private String courseType;
    private Integer amountTime;
    private Integer syHour;
    private Integer sjHour;
    private Integer qtHour;
    private int maxUser;
    private int currentUser;
    private String status;
    private Integer total;
    private String semester;
//    private Set<CTeacherEntity> teacher = new HashSet<>();
    private  Set<CTeacherCourseEntity> teacherCourses = new HashSet<>();

    public CCourseEntity() {
    }

    public CCourseEntity(String courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "assess_type")
    public String getAssessType() {
        return assessType;
    }

    public void setAssessType(String assessType) {
        this.assessType = assessType;
    }

    @Basic
    @Column(name = "credit")
    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Basic
    @Column(name = "course_type")
    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    @Basic
    @Column(name = "amount_time")
    public Integer getAmountTime() {
        return amountTime;
    }

    public void setAmountTime(Integer amountTime) {
        this.amountTime = amountTime;
    }

    @Basic
    @Column(name = "sy_hour")
    public Integer getSyHour() {
        return syHour;
    }

    public void setSyHour(Integer syHour) {
        this.syHour = syHour;
    }

    @Basic
    @Column(name = "sj_hour")
    public Integer getSjHour() {
        return sjHour;
    }

    public void setSjHour(Integer sjHour) {
        this.sjHour = sjHour;
    }

    @Basic
    @Column(name = "qt_hour")
    public Integer getQtHour() {
        return qtHour;
    }

    public void setQtHour(Integer qtHour) {
        this.qtHour = qtHour;
    }

    @Basic
    @Column(name = "max_user")
    public int getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
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

    @Basic
    @Column(name = "semester")
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    /*@ManyToMany( targetEntity = CTeacherEntity.class*//*, cascade = CascadeType.ALL*//*)
    @JoinTable( name = "C_teacher_course",
            joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")})
    public Set<CTeacherEntity> getTeacher() {
        return teacher;
    }

    public void setTeacher(Set<CTeacherEntity> teacher) {
        this.teacher = teacher;
    }*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
//    @OneToMany(cascade = CascadeType.ALL, targetEntity = CTeacherCourseEntity.class)
//    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
//    @OneToMany(mappedBy = "teacher")
    public Set<CTeacherCourseEntity> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<CTeacherCourseEntity> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CCourseEntity entity = (CCourseEntity) o;

        if (credit != entity.credit) return false;
        if (maxUser != entity.maxUser) return false;
        if (currentUser != entity.currentUser) return false;
        if (courseId != null ? !courseId.equals(entity.courseId) : entity.courseId != null) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (assessType != null ? !assessType.equals(entity.assessType) : entity.assessType != null) return false;
        if (courseType != null ? !courseType.equals(entity.courseType) : entity.courseType != null) return false;
        if (amountTime != null ? !amountTime.equals(entity.amountTime) : entity.amountTime != null) return false;
        if (syHour != null ? !syHour.equals(entity.syHour) : entity.syHour != null) return false;
        if (sjHour != null ? !sjHour.equals(entity.sjHour) : entity.sjHour != null) return false;
        if (qtHour != null ? !qtHour.equals(entity.qtHour) : entity.qtHour != null) return false;
        if (status != null ? !status.equals(entity.status) : entity.status != null) return false;
        if (total != null ? !total.equals(entity.total) : entity.total != null) return false;
        if (semester != null ? !semester.equals(entity.semester) : entity.semester != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = courseId != null ? courseId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (assessType != null ? assessType.hashCode() : 0);
        result = 31 * result + credit;
        result = 31 * result + (courseType != null ? courseType.hashCode() : 0);
        result = 31 * result + (amountTime != null ? amountTime.hashCode() : 0);
        result = 31 * result + (syHour != null ? syHour.hashCode() : 0);
        result = 31 * result + (sjHour != null ? sjHour.hashCode() : 0);
        result = 31 * result + (qtHour != null ? qtHour.hashCode() : 0);
        result = 31 * result + maxUser;
        result = 31 * result + currentUser;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (semester != null ? semester.hashCode() : 0);
        return result;
    }
}