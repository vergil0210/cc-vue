package com.hdjd.curriculaVariable.interceptor;

import com.hdjd.curriculaVariable.CurriculaVariableApplication;
import com.hdjd.curriculaVariable.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(CurriculaVariableApplication.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
            由于存在跨域访问，前端会先给后端发送一个option请求，让服务端返回允许的方法
            通过判断，过滤掉option请求
        */
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) return true;

        logger.info("开始token校验。。。");
        String token = request.getHeader("token");
        logger.info(request.getMethod());

        if (StringUtils.isEmpty(token)){
            logger.info("token值为空，已拦截该请求...");
            return false;
        }
        if (JwtUtil.verify(token)){
            logger.info("token校验通过,成功接收该请求");
            logger.info("token: " + token);
            return true;
        }
        return false;
    }
}