package com.hdjd.curriculaVariable.config;

import com.hdjd.curriculaVariable.CurriculaVariableApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
public class BeanConfig {

    @Bean(name = "log")
    public Logger getLog(){
        return LoggerFactory.getLogger(CurriculaVariableApplication.class);
    }

    @Bean(name = "userInfoMap")
    public Map<String,String> getMap() {
        return new HashMap<>(5);
    }
}