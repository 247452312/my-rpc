package com.uhyils.service.impl;

import com.uhyils.model.Student;
import com.uhyils.service.StudentRpcService;
import com.uhyils.service.StudentService;
import com.uhyils.service.base.StudentBaseRpcService;
import indi.uhyils.rpc.annotation.RpcReference;
import org.springframework.stereotype.Service;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月06日 09时18分
 */
@Service
public class StudentServiceImpl implements StudentService {


    @RpcReference
    private StudentRpcService service;
    @RpcReference
    private StudentBaseRpcService studentBaseRpcService;

    @Override
    public Student changeStudent(Student student) {
        return service.changeStudent(student);
    }

    @Override
    public Student changeBaseStudent(Student student) {
        return studentBaseRpcService.changeStudent(student);
    }
}
