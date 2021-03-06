package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.entity.CGradeEntity;
import com.hdjd.curriculaVariable.entity.CStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface GradeDao extends JpaSpecificationExecutor<CGradeEntity>, JpaRepository<CGradeEntity,String> {
    @Query(value = "SELECT * FROM c_grade WHERE student_id = ?1", nativeQuery = true)
    List<CGradeEntity> findByStudent(String studentId);

    @Transactional
    @Modifying
    @Query(value = "delete FROM c_grade WHERE student_id = ?1", nativeQuery = true)
    void deleteByStudent(String studentId);
}