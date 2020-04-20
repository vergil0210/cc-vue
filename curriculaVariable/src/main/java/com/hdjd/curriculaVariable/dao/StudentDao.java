package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.entity.CStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<CStudentEntity,String>, JpaSpecificationExecutor<CStudentEntity> {

    /**
     * @param college 专业
     * @param grade 年级
     * @return
     */
    List<CStudentEntity> findByGradeAndCollege(String college, int grade);

}