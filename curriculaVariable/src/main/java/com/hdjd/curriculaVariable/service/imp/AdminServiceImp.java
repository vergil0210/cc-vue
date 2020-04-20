package com.hdjd.curriculaVariable.service.imp;

import com.hdjd.curriculaVariable.dao.*;
import com.hdjd.curriculaVariable.entity.*;
import com.hdjd.curriculaVariable.model.*;
import com.hdjd.curriculaVariable.service.IAdminService;
import com.hdjd.curriculaVariable.utils.cc.CcUtil;
import com.hdjd.curriculaVariable.utils.curricula.CurriculaUtil;
import com.hdjd.curriculaVariable.utils.curricula.State;
import com.hdjd.curriculaVariable.utils.student.StudentUtil;
import com.hdjd.curriculaVariable.utils.teacher.TeacherUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImp implements IAdminService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    TcDao tcDao;

    @Autowired
    Logger logger;

    @Override
    public Response addCurricula(CurriculaModel model) {
        Response resp = new Response();
        logger.info("开始转换 model ==> entity");
        CCourseEntity entity = null;
        try {
            entity = CurriculaUtil.transform(model);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "转换失败，请查看日志";
            resp.setMsg(msg);
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        logger.info("转换完成,开始插入...");
        try {
            courseDao.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resp.setMsg("插入失败，请查看后台日志");
            throw new RuntimeException(e.getMessage());
        }
        return resp;
    }

    @Override
    public Response<CurriculaModel> findCurriculaAll() {
        Specification<CCourseEntity> spec = (Specification<CCourseEntity>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), State.OPT);
        Response<CurriculaModel> resp = new Response<>();
        List<CCourseEntity> entities = courseDao.findAll(spec);
        for (CCourseEntity entity : entities) {
            resp.getList().add(CurriculaUtil.transform(entity));
        }
        return resp;
    }

    @Override
    public Response<CurriculaModel> findCurriculaByCondition(CurriculaQueryModel model) {
        String name = model.getName();
        String id = model.getId();
        String status = model.getStatus();
        String credit = model.getCredit();
        Specification<CCourseEntity> spec = (Specification<CCourseEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(id)){
                list.add(criteriaBuilder.like(root.get("courseId"),id));
            }
            if (!StringUtils.isEmpty(name)){
                list.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
            }
            // 判断是否可选
            if (!StringUtils.isEmpty(credit)){
                list.add(criteriaBuilder.equal(root.get("credit"),Double.parseDouble(credit)));
            }
            if (!StringUtils.isEmpty(status)){
                list.add(criteriaBuilder.like(root.get("status"),status));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Pageable pageable = PageRequest.of(model.getPage()-1, model.getLimit());
        Page<CCourseEntity> page = courseDao.findAll(spec, pageable);
        List<CCourseEntity> entities = page.getContent();
        List<CurriculaModel> models = new ArrayList<>(entities.size());
        for (CCourseEntity entity : entities) {
            models.add(CurriculaUtil.transform(entity));
        }
        Response<CurriculaModel> resp = new Response<>();
        resp.setList(models);
        // 参数转入
        return resp;
    }

    @Override
    public Response deleteCurriculaById(long id) {
        Response resp = new Response();
        try {
            courseDao.deleteById(id+"");
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            resp.setMsg("该课程id不存在");
        }
        return resp;
    }

    @Override
    public Response changeCurriculaOpt(long id) {
        Response resp = new Response();
        Optional<CCourseEntity> opt = courseDao.findById(id + "");
        if (!opt.isPresent()){
            resp.setMsg("该课程不存在");
            return resp;
        }
        CCourseEntity entity = opt.get();
        String status = entity.getStatus();
        if (State.OPT.equals(status)) {
            entity.setStatus(State.NOT_OPT);
        }else if (State.NOT_OPT.equals(status)) {
            entity.setStatus(State.OPT);
        }else {
            resp.setMsg("该课程可选类型不合法");
            return resp;
        }
        courseDao.save(entity);
        return resp;
    }

    @Override
    public Response addTeacher(TeacherModel model) {
        Response resp = new Response();
        String msg = null;
        CTeacherEntity entity = null;
        try {
            entity = TeacherUtil.transform(model);
        } catch (Exception e) {
            msg = "数据转换异常";
            resp.setMsg(msg);
            e.printStackTrace();
        }
        assert entity != null;
        String teacherId = entity.getTeacherId();
        Optional<CTeacherEntity> opt = teacherDao.findById(teacherId);
        try {
            if ((opt.isPresent() && opt.get().getPassword()==null)){
                entity.setPassword(TeacherUtil.initPassword(entity.getTeacherId()));
            }else if (!opt.isPresent()){
                entity.setPassword(TeacherUtil.initPassword(entity.getTeacherId()));
            }else {
                entity.setPassword(opt.get().getPassword());
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            msg = "账号："+ model.getId() +"长度不合法,应该在6位以上";
            resp.setMsg(msg);
            return resp;
        }
        teacherDao.save(entity);
        return resp;
    }

    @Override
    public Response<TeacherModel> findTeacherAll() {
        Response<TeacherModel> resp = new Response<>();
        List<CTeacherEntity> entities = teacherDao.findAll();
        for (CTeacherEntity entity : entities) {
            resp.getList().add(TeacherUtil.transform(entity));
        }
        return resp;
    }



    @Override
    public Response<TeacherModel> findTeacherByCondition(TeacherQueryModel model) {
        Response<TeacherModel> resp = new Response<>();
        String gender = model.getGender();
        String name = model.getName();
        String title = model.getTitle();
        Specification<CTeacherEntity> spec = (Specification<CTeacherEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(name)){
                list.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
            }
            if (!StringUtils.isEmpty(gender)){
                list.add(criteriaBuilder.equal(root.get("gender"),name));
            }
            // 判断是否可选
            if (!StringUtils.isEmpty(title)){
                list.add(criteriaBuilder.equal(root.get("title"),title));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        List<CTeacherEntity> list = null;
        try {
            PageRequest pageable = PageRequest.of(model.getPage() - 1, model.getLimit());
            Page<CTeacherEntity> page = teacherDao.findAll(spec, pageable);
            list = page.getContent();
        } catch (Exception e) {
            resp.setMsg("查询出错，请查看后台日志");
            e.printStackTrace();
            return resp;
        }
        List<TeacherModel> models = new ArrayList<>(list.size());
        for (CTeacherEntity entity : list) {
            models.add(TeacherUtil.transform(entity));
        }
        resp.setList(models);
        return resp;
    }

    @Override
    public Response deleteTeacherById(long id) {
        Response resp = new Response();
        try {
            Optional<CTeacherEntity> opt = teacherDao.findById(id + "");
            if (opt.isPresent()) {
                teacherDao.delete(opt.get());
            }else {
                resp.setMsg("该课程不存在");
            }
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            resp.setMsg("该课程id不存在");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setMsg("删除失败,请查看后台日志");
        }
        return resp;
    }

    @Override
    public Response addStudent(StudentModel model) {
        Response resp = new Response();
        String msg = null;
        if (model==null){
            msg = "传入数据为空";
            resp.setMsg(msg);
            logger.warn(msg);
            return resp;
        }
        CStudentEntity entity = StudentUtil.transform(model);
        if (StringUtils.isEmpty(entity.getStudentId()) || entity.getStudentId().length()<=6 ){
            msg = "id不合法,请重新确认";
            resp.setMsg(msg);
            logger.warn(msg);
            return resp;
        }
        Optional<CStudentEntity> opt = studentDao.findById(entity.getStudentId());
        if ((opt.isPresent() && opt.get().getPassword()==null)){
            entity.setPassword(StudentUtil.initPassword(entity.getStudentId()));
        }else if (!opt.isPresent()){
            entity.setPassword(StudentUtil.initPassword(entity.getStudentId()));
        }else {
            entity.setPassword(opt.get().getPassword());
        }
        try {
            studentDao.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "插入失败，请查看后台日志";
            resp.setMsg(msg);
            logger.warn(msg);
            return resp;
        }
        return resp;
    }

    @Override
    public Response<StudentModel> findStudentByCondition(StudentQueryModel model) {
        Response<StudentModel> resp = new Response<>();
        String college = model.getCollege();
        String grade = model.getGrade();
        String name = model.getName();
        Specification<CStudentEntity> spec = (Specification<CStudentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(name)){
                list.add(criteriaBuilder.like(root.get("name"),"%" + name + "%"));
            }
            if (!StringUtils.isEmpty(grade)){
                list.add(criteriaBuilder.equal(root.get("grade"),grade));
            }
            // 判断是否可选
            if (!StringUtils.isEmpty(college)){
                list.add(criteriaBuilder.equal(root.get("college"),college));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        PageRequest pageable = PageRequest.of(model.getPage() - 1, model.getLimit());
        Page<CStudentEntity> page = studentDao.findAll(spec, pageable);
        List<CStudentEntity> entities = page.getContent();
        List<StudentModel> models = new ArrayList<>();
        for (CStudentEntity entity : entities) {
            models.add(StudentUtil.transform(entity));
        }
        resp.setList(models);
        return resp;
    }

    @Override
    public Response deleteStudentById(long id) {
        Response resp = new Response();
        try {
            studentDao.deleteById(id+"");
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            resp.setMsg("该学生id不存在");
        }
        return resp;
    }

    @Override
    public Response addCc(CurriculaCreateQueryModel model) {
        Response resp = new Response();
        List<CTeacherCourseEntity> list = CcUtil.transform(model);
        for (CTeacherCourseEntity tc : list) {
            tcDao.save(tc);
            if (StringUtils.isEmpty(tc.getCollege()) || tc.getGrade() == 0) {
                String msg = "专业或年级不能为空";
                resp.setMsg(msg);
                logger.warn(msg);
                return resp;
            }
//            List<CStudentEntity> studentList = studentDao.findByGradeAndCollege(tc.getCollege(), tc.getGrade());
//            if (studentList==null) {
//                continue;
//            }
//            for (CStudentEntity student : studentList) {
//                CGradeEntity gradeEntity = new CGradeEntity();
//                gradeEntity.setStsId(UUID.randomUUID().toString());
//                gradeEntity.setStudent(student);
//                gradeEntity.setTc(tc);
//                gradeDao.save(gradeEntity);
//            }
        }
        return resp;
    }

    @Override
    @Transactional
    public Response<CurriculaListModel> findCcByCondition(CurriculaListQueryModel model) {
        Response<CurriculaListModel> resp = new Response<CurriculaListModel>();
        String coName = model.getCoName();
        String teaName = model.getTeaName();
        String college = model.getCollege();
        String status = model.getStatus();
        String semester = model.getSemester();
        Specification<CTeacherCourseEntity> spec = (Specification<CTeacherCourseEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(coName)){
                list.add(criteriaBuilder.like(root.get("course").get("name"),"%" + coName + "%"));
            }
            if (!StringUtils.isEmpty(teaName)){
                list.add(criteriaBuilder.like(root.get("teacher").get("name"),"%" + teaName + "%"));
            }
            if (!StringUtils.isEmpty(semester)){
                list.add(criteriaBuilder.equal(root.get("course").get("semester"), semester));
            }
            if (!StringUtils.isEmpty(college)){
                list.add(criteriaBuilder.equal(root.get("college"), college));
            }
            // 判断是否可选
            if (!StringUtils.isEmpty(status)){
                list.add(criteriaBuilder.equal(root.get("status"),status));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        PageRequest pageable = PageRequest.of(model.getPage() - 1, model.getLimit());
        Page<CTeacherCourseEntity> page = tcDao.findAll(spec, pageable);
        List<CTeacherCourseEntity> entities = page.getContent();
        List<CurriculaListModel> models = new ArrayList<>(entities.size());
        for (CTeacherCourseEntity entity : entities) {
            try {
                models.add(CcUtil.transform(entity));
            } catch (Exception e) {
                e.printStackTrace();
                resp.setMsg(e.getMessage());
                return resp;
            }
        }
        resp.setList(models);
        return resp;
    }

    @Override
    public Response deleteCcById(String id) {
        Response resp = new Response();
        try {
            tcDao.deleteById(id);
        } catch (Exception e) {
            resp.setMsg("删除失败，请查看后台日志");
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public Response changeCcOpt(String id) {
        Response resp = new Response();
        Optional<CTeacherCourseEntity> opt = tcDao.findById(id);
        if (!opt.isPresent()){
            resp.setMsg("该课程不存在");
            return resp;
        }
        CTeacherCourseEntity entity = opt.get();
        String status = entity.getStatus();
        if (State.OPT.equals(status)) {
            entity.setStatus(State.NOT_OPT);
        }else if (State.NOT_OPT.equals(status)) {
            entity.setStatus(State.OPT);
        }else {
            resp.setMsg("该课程可选类型不合法");
            return resp;
        }
        tcDao.save(entity);
        return resp;
    }


}