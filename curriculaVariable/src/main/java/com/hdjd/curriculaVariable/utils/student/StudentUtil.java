package com.hdjd.curriculaVariable.utils.student;

import com.hdjd.curriculaVariable.entity.CStudentEntity;
import com.hdjd.curriculaVariable.model.StudentModel;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 */
public class StudentUtil {

    public static CStudentEntity transform(StudentModel model) {
        CStudentEntity entity = new CStudentEntity();
        entity.setStudentId(model.getId()+"");
        if (!StringUtils.isEmpty(model.getGender())) entity.setGender(model.getGender().trim());
        entity.setAge(model.getAge());
        if (!StringUtils.isEmpty(model.getName()))  entity.setName(model.getName().trim());
        if (!StringUtils.isEmpty(model.getPassword()))  entity.setPassword(model.getPassword().trim());
        if (!StringUtils.isEmpty(model.getCollege()))  entity.setCollege(model.getCollege().trim());
        if (!StringUtils.isEmpty(model.getGrade()))  entity.setGrade(Integer.parseInt(model.getGrade().trim()));
        if (!StringUtils.isEmpty(model.getClazz()))  entity.setClazz(model.getClazz().trim());
        if (!StringUtils.isEmpty(model.getEmail()))  entity.setEmail(model.getEmail().trim());
        if (!StringUtils.isEmpty(model.getPhone()))  entity.setPhone(model.getPhone().trim());
        return entity;
    }

    public static StudentModel transform(CStudentEntity entity) {
        StudentModel model = new StudentModel();
        model.setId(Long.parseLong(entity.getStudentId()));
        model.setAge(entity.getAge());
        model.setName(entity.getName());
        model.setGender(entity.getGender());
        model.setPassword(entity.getPassword());
        model.setCollege(entity.getCollege());
        model.setGrade(entity.getGrade()+"");
        model.setClazz(entity.getClazz());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        return model;
    }

    public static String initPassword(String id) throws StringIndexOutOfBoundsException{
        return id.substring(id.length()-6);
    }
}