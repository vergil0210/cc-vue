package com.hdjd.curriculaVariable.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 */
public class ConstantUtil {

    public static String checkSemester(int grade) throws ParseException {
        int temp = 150;
        int aYear = 365;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(grade + "-09-01");
        Date date2 = new Date();
        int day = (int)((System.currentTimeMillis() - date1.getTime())/(3600 * 1000 * 24));
        if (day <= temp) {
            return "大一-下";
        }else if (day <= (aYear)) {
            return "大二-上";
        }else if (day <= (aYear+temp)) {
            return "大二-下";
        }else if (day <= 2*aYear) {
            return "大三-上";
        }else if (day <= 2*aYear+temp) {
            return "大三-下";
        }else if (day <= 3*aYear) {
            return "大四-上";
        }else if (day <= 3*aYear+temp){
            return "大四-下";
        }
        return null;
    }
}