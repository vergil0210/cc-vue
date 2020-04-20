package com.hdjd.curriculaVariable.utils.cc;

import com.hdjd.curriculaVariable.entity.CCourseEntity;
import com.hdjd.curriculaVariable.entity.CTeacherCourseEntity;
import com.hdjd.curriculaVariable.entity.CTeacherEntity;
import com.hdjd.curriculaVariable.model.CurriculaCreateQueryModel;
import com.hdjd.curriculaVariable.model.CurriculaListModel;
import com.hdjd.curriculaVariable.model.CurriculaListQueryModel;
import com.hdjd.curriculaVariable.utils.curricula.State;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Administrator
 */
public class CcUtil {

    public static List<CTeacherCourseEntity> transform(CurriculaCreateQueryModel model) {
        List<CTeacherCourseEntity> entities = new ArrayList<>();
        for (Long coId : model.getCoIds()) {
            for (Long teaId : model.getTeaIds()) {
                CTeacherCourseEntity entity = new CTeacherCourseEntity();
                entity.setTcId(UUID.randomUUID().toString());
                if (!StringUtils.isEmpty(model.getGrade())) entity.setGrade(model.getGrade());
                if (!StringUtils.isEmpty(model.getCollege())) entity.setCollege(model.getCollege());
                entity.setStartTime(model.getStartTime());
                entity.setEndTime(model.getEndTime());
                entity.setTeacher(new CTeacherEntity(teaId+""));
                entity.setCourse(new CCourseEntity(coId+""));
                if (!StringUtils.isEmpty(model.getStatus())) {
                    entity.setStatus(model.getStatus());
                }else {
                    entity.setStatus(State.NOT_OPT);
                }
                entities.add(entity);
            }
        }
        return entities;
    }

    public static CurriculaListModel transform(CTeacherCourseEntity entity) {
        CurriculaListModel model = new CurriculaListModel();
        if (entity.getCourse() == null) {
            throw new RuntimeException("课程信息为空,请查看后台日志");
        }
        if (entity.getTeacher() == null) {
            throw new RuntimeException("教师信息为空,请查看后台日志");
        }
        CTeacherEntity teacher = entity.getTeacher();
        CCourseEntity course = entity.getCourse();
        model.setCoId(Long.parseLong(course.getCourseId()));
        model.setCoName(course.getName());
        model.setTeaId(Long.parseLong(teacher.getTeacherId()));
        model.setTeaName(teacher.getName());
        model.setStartTime(entity.getStartTime());
        model.setEndTime(entity.getEndTime());
        model.setSemester(course.getSemester());
        model.setMaxUser(course.getMaxUser());
        model.setCollege(entity.getCollege());
        model.setCurrentUser(entity.getCurrentUser());
        model.setCredit(course.getCredit());
        model.setId(entity.getTcId());
        Integer total = entity.getTotal();
        int currentUser = model.getCurrentUser();
        if (total==null || currentUser==0){
            model.setRate(5);
        }else {
            model.setRate(total/currentUser);
        }
        model.setStatus(entity.getStatus());
        return model;
    }
}