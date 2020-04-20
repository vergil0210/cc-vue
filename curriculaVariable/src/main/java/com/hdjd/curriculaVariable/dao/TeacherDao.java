package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.entity.CStudentEntity;
import com.hdjd.curriculaVariable.entity.CTeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherDao extends JpaRepository<CTeacherEntity,String>, JpaSpecificationExecutor<CTeacherEntity> {
}
