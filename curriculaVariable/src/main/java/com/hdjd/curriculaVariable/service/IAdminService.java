package com.hdjd.curriculaVariable.service;

import com.hdjd.curriculaVariable.model.*;

public interface IAdminService {
    Response addCurricula(CurriculaModel model);

    Response<CurriculaModel> findCurriculaByCondition(CurriculaQueryModel model);

    Response deleteCurriculaById(long id);

    Response changeCurriculaOpt(long id);

    Response addTeacher(TeacherModel model);

    Response<TeacherModel> findTeacherByCondition(TeacherQueryModel model);

    Response deleteTeacherById(long id);

    Response addStudent(StudentModel model);

    Response<StudentModel> findStudentByCondition(StudentQueryModel model);

    Response<TeacherModel> findTeacherAll();

    Response<CurriculaModel> findCurriculaAll();

    Response deleteStudentById(long id);

    Response addCc(CurriculaCreateQueryModel model);

    Response<CurriculaListModel> findCcByCondition(CurriculaListQueryModel model);

    Response deleteCcById(String id);

    Response changeCcOpt(String id);
}
