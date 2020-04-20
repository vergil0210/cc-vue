package com.hdjd.curriculaVariable;

import com.hdjd.curriculaVariable.dao.StudentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurriculaVariableApplication {
	private static Logger logger = LoggerFactory.getLogger(CurriculaVariableApplication.class);



	public static void main(String[] args) {
		logger.info("CurriculaVariable is starting");
		SpringApplication.run(CurriculaVariableApplication.class, args);
		logger.info("CurriculaVariable is running");

	}

}
