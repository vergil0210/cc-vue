package com.hdjd.curriculaVariable.service.imp;

import com.hdjd.curriculaVariable.dao.CourseDao;
import com.hdjd.curriculaVariable.dao.GradeDao;
import com.hdjd.curriculaVariable.dao.StudentDao;
import com.hdjd.curriculaVariable.dao.TcDao;
import com.hdjd.curriculaVariable.entity.*;
import com.hdjd.curriculaVariable.exception.StudentCourseException;
import com.hdjd.curriculaVariable.model.*;
import com.hdjd.curriculaVariable.service.IStudentService;
import com.hdjd.curriculaVariable.utils.ConstantUtil;
import com.hdjd.curriculaVariable.utils.JwtUtil;
import com.hdjd.curriculaVariable.utils.cc.CcUtil;
import com.hdjd.curriculaVariable.utils.curricula.State;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        //获取学生已选的课程集合(map）
        Map<String, String> courseCheck = getStudentCourseStatus(studentEntity);
        List<CCourseEntity> courseEntities = null;
        try {
            courseEntities = courseDao.findByStatusAndSemester(State.OPT, ConstantUtil.checkSemester(studentEntity.getGrade()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<CurriculaVariableListModel> list = new ArrayList<>();
        for (CCourseEntity courseEntity : courseEntities) {
            CurriculaVariableListModel model = new CurriculaVariableListModel();
            String tcId = courseCheck.get(courseEntity.getCourseId());
            if (tcId == null) {
                model.setStatus("待选");
            }else {
                model.setStatus("已选");
                model.setTcId(tcId);
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
                childModel.setTcId(tc.getTcId());
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

    @Override
    @Transactional
    public Response<Integer> createGradeList(Request<String> request) {
        Response<Integer> resp = new Response<>();
        String msg = null;
        //        long id = JwtUtil.getUserId(token);
        long id = 2016211001000814L;
        //清空学生选课
        gradeDao.deleteByStudent(id+"");
        List<String> list = request.getList();
        int index = 0;
        for (String tcId : list) {
            CGradeEntity entity = new CGradeEntity();
            CTeacherCourseEntity tc = tcDao.findById(tcId).get();
            // 判断该课程人数是否已满
            if (tc.getCurrentUser()>=tc.getCourse().getMaxUser()){
                resp.getList().add(index);
                msg = "该课程人数已满";
                logger.warn(msg + "(tcId: " + tcId +")");
                if (StringUtils.isEmpty(resp.getMsg())) resp.setMsg("出现未知错误，请重新尝试");
                else resp.setMsg(msg);
                continue;
            }
            // 如果通过 则认定为成功选中
            tc.setCurrentUser(tc.getCurrentUser()+1);
            entity.setStudent(new CStudentEntity(id+""));
            entity.setTc(tc);
            entity.setStsId(UUID.randomUUID().toString());
            try {
                gradeDao.save(entity);
                tcDao.save(tc);
            } catch (Exception e) {
                e.printStackTrace();
                if (StringUtils.isEmpty(resp.getMsg())) resp.setMsg("出现未知错误，请重新尝试");
                else {
                    resp.setMsg("选课失败，请重新尝试");
                }
            }
            index++;
        }

        return resp;
    }

    @Override
    public Response<CurriculaListModel> getChosenList(Request<PageModel> req) {
        Response<CurriculaListModel> resp = new Response<>();
        PageModel model = req.getData();
        String msg = null;
        //        long id = JwtUtil.getUserId(token);
        long id = 2016211001000814L;
        Specification<CGradeEntity> spec = (Specification<CGradeEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("student").get("studentId"),id+"");
        };
        PageRequest pageable = PageRequest.of(model.getPage() - 1, model.getLimit());
        Page<CGradeEntity> page = gradeDao.findAll(spec, pageable);
        List<CGradeEntity> entities = page.getContent();
        List<CurriculaListModel> models = new ArrayList<>(entities.size());
        for (CGradeEntity entity : entities) {
            try {
                models.add(CcUtil.transform(entity.getTc()));
            } catch (Exception e) {
                e.printStackTrace();
                resp.setMsg(e.getMessage());
                return resp;
            }
        }
        resp.setList(models);
        return resp;
    }

    /**
     * @param entity 学生实体
     * @return  返回已选课程集合 key: course_id      value: tc_id
     * @throws StudentCourseException
     */
    private Map<String,String> getStudentCourseStatus(CStudentEntity entity) throws StudentCourseException {
        Map<String,String> map = new HashMap<>();
        Set<CGradeEntity> grades = entity.getGrades();
        for (CGradeEntity grade : grades) {
            map.put(grade.getTc().getCourse().getCourseId(),grade.getTc().getTcId());
        }
        return map;

    }
}