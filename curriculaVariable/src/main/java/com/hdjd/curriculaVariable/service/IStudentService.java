package com.hdjd.curriculaVariable.service;

import com.hdjd.curriculaVariable.model.CurriculaVariableListModel;
import com.hdjd.curriculaVariable.model.Response;

public interface IStudentService {
    Response<CurriculaVariableListModel> getCourseList(String token);

}
