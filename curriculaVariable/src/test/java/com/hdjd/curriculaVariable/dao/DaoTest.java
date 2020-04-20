package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.CurriculaVariableApplication;
import com.hdjd.curriculaVariable.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurriculaVariableApplication.class)
public class DaoTest {
    @Autowired
    StudentDao studentDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TcDao tcDao;
    @Autowired
    GradeDao gradeDao;

    @Autowired
    TeacherDao teacherDao;
    private static Logger logger = LoggerFactory.getLogger(CurriculaVariableApplication.class);

    @Test
    @Transactional
    public void studentDaoTest01(){
//        List<CStudentEntity> all = studentDao.findAll();
        CStudentEntity cStudentEntity = studentDao.findById("2016211001000814").get();
        System.out.println(cStudentEntity);
//
//        List<CGradeEntity> all = gradeDao.findAll();
//        System.out.println(all);
//        CCourseEntity course = new CCourseEntity();
//        course.setName("123123");
//        course.setCourseId("111111");
//        course.setMaxUser(120);
//        course.setCurrentUser(50);
//        course.setStatus(State.OPT);
//        course.setAssessType(AccessType.ACCESS_TYPE_EXAM);
//        course.setCourseType(CourseTpe.ELECTIVE);
//
//
//        CTeacherEntity teacher = new CTeacherEntity();
//        teacher.setTeacherId("200010002");
//        teacher.setName("王小龙");
//        teacher.setAge(51);
//        teacher.setGender(TeacherUtil.getGENDER_MALE());
//        teacher.setPassword("123123");
//        teacher.setTitle(Rank.P);
//        teacher.setGender(Gender.MALE);
//        teacher.setEmail("123123@qq.com");
//
//        CTeacherCourseEntity tc = new CTeacherCourseEntity();
//        tc.setTcId("11222");
//        tc.setTeacher(teacher);
//        tc.setStatus(State.OPT);
//        tc.setCourse(course);
//        tc.setTeacher(teacher);
//        tcDao.save(tc);



//        HashSet<CTeacherEntity> set = new HashSet<>();
//        set.add(teacher);
//        cCourseEntity.setTeacher(set);
//        cCourseEntity.getTeacher().add(teacher);
//        courseDao.save(cCourseEntity);
//        PageRequest of = PageRequest.of(0, 4);
//        Page<CCourseEntity> all = courseDao.findAll(of);
//        List<CCourseEntity> content = all.getContent();
//        System.out.println(all);
//        List<Integer> list = Arrays.asList(1, 2, 3);
//        courseDao.deleteById("2");
//        Optional<CTeacherEntity> all = teacherDao.findById(200010001 + "");
//        CTeacherEntity cTeacherEntity = all.get();
//        for (CTeacherCourseEntity teacherCours : cTeacherEntity.getTeacherCourses()) {
//            System.out.println();
//        }
//        System.out.println(all);
//        List<CTeacherCourseEntity> c = tcDao.findByGradeAndCollege(2016, "软件工程");
//        System.out.println();
//        String college = "软件工程";
//        int grade = 2016;
//        Specification<CCourseEntity> spec = new Specification<>() {
//            @Override
//            public Predicate toPredicate(Root<CCourseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> list = new ArrayList<>();
//                if (!StringUtils.isEmpty(college)){
//                    list.add(criteriaBuilder.equal(root.get("teacherCourses").get("college"), college));
//                }
//                // 判断是否可选
//                if (!StringUtils.isEmpty(status)){
//                    list.add(criteriaBuilder.equal(root.get("status"),status));
//                }
//                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
//            }
//        };
    }

}