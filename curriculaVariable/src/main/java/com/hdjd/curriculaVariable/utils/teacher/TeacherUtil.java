package com.hdjd.curriculaVariable.utils.teacher;

import com.hdjd.curriculaVariable.dao.TeacherDao;
import com.hdjd.curriculaVariable.entity.CTeacherEntity;
import com.hdjd.curriculaVariable.model.TeacherModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 */
public class TeacherUtil {

    @Autowired
    static TeacherDao teacherDao;
    @Autowired
    static Logger logger;

    public static CTeacherEntity transform(TeacherModel model) {
        CTeacherEntity entity = null;
        try {
            entity = new CTeacherEntity();
            entity.setTeacherId(model.getId()+"");
            entity.setName(model.getName());
            entity.setEmail(model.getEmail());
            entity.setGender(model.getGender());
            entity.setTitle(model.getTitle());
            entity.setPhone(model.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return entity;
    }

    public static TeacherModel transform(CTeacherEntity entity) {
        TeacherModel model = null;

        try {
            model = new TeacherModel();
            model.setName(entity.getName());
            model.setId(Long.parseLong(entity.getTeacherId()));
            model.setEmail(entity.getEmail());
            model.setGender(entity.getGender());
            model.setTitle(entity.getTitle());
            model.setPhone(entity.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;


    }

    // 初始化密码，取值为id的后六位
    public static String initPassword(String id) throws StringIndexOutOfBoundsException{
        return id.substring(id.length()-6);
    }

}