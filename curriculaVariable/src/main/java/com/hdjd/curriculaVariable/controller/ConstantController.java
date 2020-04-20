package com.hdjd.curriculaVariable.controller;

import com.hdjd.curriculaVariable.model.UserModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/constant")
public class ConstantController {

    @GetMapping("test")
    public String test() throws InterruptedException {
        String lock = "a";
        synchronized (lock){

            lock.wait(4000);
        }
        System.out.println("test finish");
        return "success";
    }

    @GetMapping("test1")
    public String test1() throws InterruptedException {
        System.out.println("test1 finish");
        return "success";
    }

    @PostMapping("login")
    public String login(@RequestBody UserModel userModel){
        System.out.println(userModel);
        return null;
    }
}