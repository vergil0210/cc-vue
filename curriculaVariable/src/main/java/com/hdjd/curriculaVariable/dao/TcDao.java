package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.entity.CStudentEntity;
import com.hdjd.curriculaVariable.entity.CTeacherCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface TcDao extends JpaRepository<CTeacherCourseEntity,String>, JpaSpecificationExecutor<CTeacherCourseEntity> {

    /**
     * 查询
     * @param grade 年级
     * @param college 专业
     * @return 符合条件的tc集合
     */
    List<CTeacherCourseEntity> findByGradeAndCollege(int grade,String college);
}
