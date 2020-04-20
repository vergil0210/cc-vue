package com.hdjd.curriculaVariable.dao;

import com.hdjd.curriculaVariable.entity.CCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao extends JpaSpecificationExecutor<CCourseEntity>, JpaRepository<CCourseEntity,String> {

    List<CCourseEntity> findByStatusAndSemester(String status,String semester);
}
