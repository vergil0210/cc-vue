package com.hdjd.curriculaVariable.controller;

import com.hdjd.curriculaVariable.model.CurriculaVariableListModel;
import com.hdjd.curriculaVariable.model.Response;
import com.hdjd.curriculaVariable.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    IStudentService studentService;

    @Resource(name = "userInfoMap")
    Map<String,String> userInfo;

    // token认证完成后改为传入token
    @GetMapping("course/list")
    public Response<CurriculaVariableListModel> getCourseList(String token){
        String grade = userInfo.get("grade");
        String college = userInfo.get("college");
        return studentService.getCourseList(token);
    }
}