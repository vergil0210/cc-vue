package com.hdjd.curriculaVariable.service.imp;

import com.hdjd.curriculaVariable.CurriculaVariableApplication;
import com.hdjd.curriculaVariable.dao.StudentDao;
import com.hdjd.curriculaVariable.entity.CStudentEntity;
import com.hdjd.curriculaVariable.model.UserModel;
import com.hdjd.curriculaVariable.service.IConstantServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConstantServiceImp implements IConstantServcie {
    private static Logger logger = LoggerFactory.getLogger(CurriculaVariableApplication.class);
    private static String ROLE_STUDENT = "student";
    private static String ROLE_TEACHER = "teacher";
    private static String ROLE_ADMIN = "admin";

    @Autowired
    StudentDao studentDao;

    @Override
    public String login(UserModel user) {
        logger.info("用户正在登陆，请稍后。。。");
        String msg = null;
        if (user == null) {
            throw new RuntimeException("登陆信息为空，请检查代码漏洞");
        }
        String role = user.getRole();

        if (ROLE_STUDENT.equals(role)) {
            msg = studentLogin(user);
        } else if (ROLE_TEACHER.equals(role)){
            msg = teacherLogin(user);
        } else if (ROLE_ADMIN.equals(role)){
            msg = adminLogin(user);
        }

        return msg;
    }

    private String studentLogin(UserModel user) {
        String msg = null;
        Optional<CStudentEntity> opt = studentDao.findById(user.getUserId()+"");
        if (!opt.isPresent()) {
            msg = "该用户不存在,请重新确认";
            logger.warn(msg);
            return msg;
        }
        CStudentEntity student = opt.get();
        if (student.getPassword().equals(user.getPassword())){
            return msg;
        }
        return msg;
    }
    private String teacherLogin(UserModel user) {
        String msg = null;
        /* 逻辑待添加 */
        return msg;
    }
    private String adminLogin(UserModel user) {
        String msg = null;
        /* 逻辑待添加 */
        return msg;
    }

}