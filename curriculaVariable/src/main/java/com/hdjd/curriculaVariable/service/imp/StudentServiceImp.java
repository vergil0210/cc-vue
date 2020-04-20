package com.hdjd.curriculaVariable.service.imp;

import com.hdjd.curriculaVariable.dao.CourseDao;
import com.hdjd.curriculaVariable.dao.GradeDao;
import com.hdjd.curriculaVariable.dao.StudentDao;
import com.hdjd.curriculaVariable.dao.TcDao;
import com.hdjd.curriculaVariable.entity.*;
import com.hdjd.curriculaVariable.exception.StudentCourseException;
import com.hdjd.curriculaVariable.model.CurriculaVariableListModel;
import com.hdjd.curriculaVariable.model.CvlChildModel;
import com.hdjd.curriculaVariable.model.Response;
import com.hdjd.curriculaVariable.service.IStudentService;
import com.hdjd.curriculaVariable.utils.ConstantUtil;
import com.hdjd.curriculaVariable.utils.JwtUtil;
import com.hdjd.curriculaVariable.utils.curricula.State;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.*;

@Service
public class StudentServiceImp implements IStudentService {

    @Autowired
    Logger logger;

    @Autowired
    StudentDao studentDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    TcDao tcDao;

    @Autowired
    CourseDao courseDao;

    @Override
    @Transactional
    public Response<CurriculaVariableListModel> getCourseList(String token) {
        Response<CurriculaVariableListModel> resp = new Response<>();
        String msg = null;
//        long id = JwtUtil.getUserId(token);
        long id = 2016211001000814L;
        Optional<CStudentEntity> opt = studentDao.findById(id + "");
        if (!opt.isPresent()){
            msg = "用户id不存在";
            resp.setMsg(msg);
            logger.warn(msg);
            return resp;
        }
        CStudentEntity studentEntity = opt.get();
        Map<String, String> courseCheck = getStudentCourseStatus(studentEntity);
        int grade = studentEntity.getGrade();
        String college = studentEntity.getCollege();
        List<CCourseEntity> courseEntities = null;
        try {
            courseEntities = courseDao.findByStatusAndSemester(State.OPT, ConstantUtil.checkSemester(studentEntity.getGrade()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CurriculaVariableListModel> list = new ArrayList<>();
        for (CCourseEntity courseEntity : courseEntities) {
            CurriculaVariableListModel model = new CurriculaVariableListModel();
            if (courseCheck.get(courseEntity.getCourseId()) == null) {
                model.setStatus("已选");
            }else {
                model.setStatus("待选");
            }
            model.setCoName(courseEntity.getName());
            model.setAccessType(courseEntity.getAssessType());
            model.setCourseType(courseEntity.getCourseType());
            model.setSemester(courseEntity.getSemester());
            model.setCredit(courseEntity.getCredit());
            model.setAmountTime(courseEntity.getAmountTime());
            model.setCoId(courseEntity.getCourseId());
            Set<CTeacherCourseEntity> tcs = courseEntity.getTeacherCourses();
            List<CvlChildModel> childModels = new ArrayList<>();
            for (CTeacherCourseEntity tc : tcs) {
                // 如果课程为不可选 直接跳过本次循环
                if (State.NOT_OPT.equals(tc.getStatus())) continue;
                CTeacherEntity teacherEntity = tc.getTeacher();
                CvlChildModel childModel = new CvlChildModel();
                childModel.setId(Long.parseLong(teacherEntity.getTeacherId()));
                childModel.setName(teacherEntity.getName());
                childModel.setGender(teacherEntity.getGender());
                childModel.setTitle(teacherEntity.getTitle());
                if (tc.getTotal()!=null && tc.getCurrentUser()!= 0){
                    childModel.setRate(tc.getTotal()/tc.getCurrentUser());
                }else {
                    childModel.setRate(5);
                }
                childModel.setMaxUser(courseEntity.getMaxUser());
                childModel.setCurrentUser(tc.getCurrentUser());
                childModels.add(childModel);
            }
            model.setChildModels(childModels);
            list.add(model);
        }
        resp.setList(list);

//        for (CTeacherCourseEntity tc : tcs) {
//            model = new CurriculaVariableListModel();
//            if (tc.getCourse() == null) {
//                msg = "课程信息为空，请查看日志";
//                resp.setMsg(msg);
//                logger.warn(msg + ":" + tc);
//                return resp;
//            }
//            model.setCoName(tc.getCourse().getName());
//            model.setAccessType(tc.getCourse().getAssessType());
//            model.setCourseType(tc.getCourse().getCourseType());
//            model.setSemester(tc.getCourse().getSemester());
//            model.setCredit(tc.getCourse().getCredit());
//            model.setAmountTime(tc.getCourse().getAmountTime());
//            model.setCoId(tc.getCourse().getCourseId());
//            CvlChildModel childModel = new CvlChildModel();
//
////            model.setChildModel();
//        }
//        Specification<CGradeEntity> spec = new Specification<>() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> list = new ArrayList<>();
//                if (!StringUtils.isEmpty(coName)){
//                    list.add(criteriaBuilder.like(root.get("course").get("name"),"%" + coName + "%"));
//                }
//                return null;
//            }
//        };


        return resp;
    }

    private Map<String,String> getStudentCourseStatus(CStudentEntity entity) throws StudentCourseException {
        Map<String,String> map = new HashMap<>();
        Set<CGradeEntity> grades = entity.getGrades();
        for (CGradeEntity grade : grades) {
            map.put(grade.getTc().getCourse().getCourseId(),"");
        }
        return map;

    }
}