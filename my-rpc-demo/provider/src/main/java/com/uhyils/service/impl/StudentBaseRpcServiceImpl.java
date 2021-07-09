package com.uhyils.service.impl;

import com.uhyils.model.Student;
import com.uhyils.service.base.StudentBaseRpcService;
import indi.uhyils.rpc.annotation.RpcService;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月06日 09时26分
 */
@RpcService
public class StudentBaseRpcServiceImpl implements StudentBaseRpcService {

    @Override
    public Student changeStudent(Student student) {
        if (student == null) {
            return null;
        }

        Integer id = student.getId();
        if (id == null) {
            student.setId(0);
        } else {
            student.setId(id + 1);
        }
        return student;
    }
}
