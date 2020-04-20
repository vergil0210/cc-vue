package com.hdjd.curriculaVariable.utils;


import com.hdjd.curriculaVariable.model.UserModel;
import com.hdjd.curriculaVariable.utils.teacher.TeacherUtil;
import org.junit.platform.engine.TestDescriptor;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.UUID;

public class UtilsTest {
    public static void main(String[] args) throws ParseException {
//        UserModel userModel = new UserModel();
//        userModel.setUserId("11111");
//        userModel.setPassword("19980816");
//        userModel.setRole("student");
//        String sign1 = JwtUtil.sign(userModel);
//        String sign2 = JwtUtil.sign(userModel);
//        String sign3 = JwtUtil.sign(userModel);
//        System.out.println("sign = " + sign1);
//        System.out.println("sign = " + sign2);
//        System.out.println("sign = " + sign3);
//        JwtUtil.verify("123");
//
//        String a = "213";
//        a.substring(-5);
//        System.out.println(Long.parseLong("2016211001000814"));
        System.out.println(ConstantUtil.checkSemester(2017));
    }
}