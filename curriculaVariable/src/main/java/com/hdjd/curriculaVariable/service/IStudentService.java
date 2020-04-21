package com.hdjd.curriculaVariable.service;

import com.hdjd.curriculaVariable.model.*;

public interface IStudentService {
    Response<CurriculaVariableListModel> getCourseList(String token);

    Response<Integer> createGradeList(Request<String> request);


    Response<CurriculaListModel> getChosenList(Request<PageModel> request);
}
