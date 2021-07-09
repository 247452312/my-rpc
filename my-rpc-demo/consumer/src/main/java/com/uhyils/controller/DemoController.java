package com.uhyils.controller;


import com.uhyils.model.Student;
import com.uhyils.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月06日 09时12分
 */
@RestController
public class DemoController {

    @Autowired
    private StudentService service;

    @PostMapping("action")
    public Student action(@RequestBody Student student) {
        return service.changeStudent(student);
    }
    @PostMapping("action2")
    public Student action2(@RequestBody Student student) {
        return service.changeBaseStudent(student);
    }
}
