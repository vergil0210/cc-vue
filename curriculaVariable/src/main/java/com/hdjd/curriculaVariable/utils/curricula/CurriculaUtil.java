package com.hdjd.curriculaVariable.utils.curricula;

import com.hdjd.curriculaVariable.entity.CCourseEntity;
import com.hdjd.curriculaVariable.model.CurriculaModel;

/**
 * @author Administrator
 */
public class CurriculaUtil {
    public static CCourseEntity transform(CurriculaModel model) {
        CCourseEntity entity = new CCourseEntity();
        entity.setCourseId(model.getId()+"");
        entity.setName(model.getName().trim());
        entity.setSemester(model.getSemester().trim());
        entity.setCredit(model.getCredit());
        entity.setStatus(model.getStatus().trim());
        entity.setAssessType(model.getAccessType().trim());
        entity.setCourseType(model.getCourseType().trim());
        entity.setMaxUser(model.getMaxUser());
        entity.setCurrentUser(model.getCurrentUser());
        entity.setAmountTime(model.getAmountTime());
        return entity;
    }

    public static CurriculaModel transform(CCourseEntity entity) {
        CurriculaModel model = new CurriculaModel();
        model.setId(Integer.parseInt(entity.getCourseId()));
        model.setName(entity.getName());
        model.setSemester(entity.getSemester());
        model.setCredit(entity.getCredit());
        if (entity.getAmountTime()!=null) model.setAmountTime(entity.getAmountTime());
        model.setMaxUser(entity.getMaxUser());
        model.setCurrentUser(entity.getCurrentUser());
        model.setStatus(entity.getStatus());
        model.setAccessType(entity.getAssessType());
        model.setCourseType(entity.getCourseType());
        return model;
    }

}