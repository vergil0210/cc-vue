package com.hdjd.curriculaVariable.controller;

import com.hdjd.curriculaVariable.model.*;
import com.hdjd.curriculaVariable.service.IAdminService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    Logger logger;

    @Autowired
    IAdminService adminService;

    /**
     * 课程接口
     */
    @GetMapping("course/list")
    public Response<CurriculaModel> courseSearch(CurriculaQueryModel model){
        return adminService.findCurriculaByCondition(model);
    }

    @GetMapping("course/findALl")
    public Response<CurriculaModel> findAll(){
        return adminService.findCurriculaAll();
    }

    @PostMapping("course/create")
    public Response courseCreate(@RequestBody CurriculaModel model){
        return adminService.addCurricula(model);
    }

    @DeleteMapping("course/deleteById")
    public Response deleteById(@RequestBody CurriculaModel model){
        return adminService.deleteCurriculaById(model.getId());
    }

    @PutMapping("course/changeOpt")
    public Response changeOpt(@RequestBody CurriculaModel model){
        return adminService.changeCurriculaOpt(model.getId());
    }

    /**
     * 教师接口
     */
    @PostMapping("teacher/create")
    public Response teacherCreate(@RequestBody TeacherModel model) {
        return adminService.addTeacher(model);
    }

    @GetMapping("teacher/list")
    public Response<TeacherModel> teacherSearch(TeacherQueryModel model) {
        return adminService.findTeacherByCondition(model);
    }

    @GetMapping("teacher/findAll")
    public Response<TeacherModel> teacherSearchAll() {
        return adminService.findTeacherAll();
    }

    @DeleteMapping("teacher/deleteById")
    public Response teacherDelete(@RequestBody TeacherModel model) {
        return adminService.deleteTeacherById(model.getId());
    }

    /**
     * 学生接口
     */
    @PostMapping("student/create")
    public Response studentCreate(@RequestBody StudentModel model){
        return adminService.addStudent(model);
    }

    @GetMapping("student/list")
    public Response<StudentModel> studentSearch(StudentQueryModel model) {
        return adminService.findStudentByCondition(model);
    }

    @DeleteMapping("student/deleteById")
    public Response studentDelete(@RequestBody StudentModel model) {
        return adminService.deleteStudentById(model.getId());
    }

    /**
     * 教师_课程接口
     */
    @PostMapping("cc/create")
    public Response ccCreate(@RequestBody CurriculaCreateQueryModel model) {
        return  adminService.addCc(model);
    }

    @GetMapping("cc/list")
    public Response<CurriculaListModel> ccSearch(CurriculaListQueryModel model) {
        return adminService.findCcByCondition(model);
    }

    @DeleteMapping("cc/deleteOne")
    public Response ccDelete(@RequestBody CurriculaListModel model){
        return  adminService.deleteCcById(model.getId());
    }

    @PutMapping("cc/changeOpt")
    public Response ccChangeOpt(@RequestBody CurriculaListModel model){
        return adminService.changeCcOpt(model.getId());
    }

}