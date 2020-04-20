package com.hdjd.curriculaVariable.entity;

import com.hdjd.curriculaVariable.utils.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "c_teacher", schema = "course_selection", catalog = "")
public class CTeacherEntity implements Serializable {
    private static final long serialVersionUID = -3950586683660927497L;
    private String teacherId;
    private String name;
    private String gender;
    private int age;
    private String password;
    private  Set<CTeacherCourseEntity> teacherCourses = new HashSet<>();
    private String title;
    private String email;
    private String phone;


    public CTeacherEntity() {
    }

    public CTeacherEntity(String teacherId) {
        this.teacherId = teacherId;
    }


    @Id
    @Column(name = "teacher_id")
//    @NotFound(action = NotFoundAction.IGNORE)
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (!Gender.MALE.equals(gender) & !Gender.FEMALE.equals(gender)) {
            throw new RuntimeException("该角色性别不合法");
        }
        this.gender = gender;
    }

    @Basic
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @OneToMany(cascade = CascadeType.ALL, targetEntity = CTeacherCourseEntity.class)
//    @JoinColumn(name = "teacher_id",referencedColumnName = "teacher_id")
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
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

        CTeacherEntity that = (CTeacherEntity) o;

        if (age != that.age) return false;
        if (teacherId != null ? !teacherId.equals(that.teacherId) : that.teacherId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teacherId != null ? teacherId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}