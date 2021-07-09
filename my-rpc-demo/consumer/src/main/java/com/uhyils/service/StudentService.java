package com.uhyils.service;


import com.uhyils.model.Student;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月06日 09时18分
 */
public interface StudentService {

    /**
     * 修改id+1 如果没有 则置为1
     *
     * @param student
     * @return
     */
    Student changeStudent(Student student);


    /**
     * 修改id+1 如果没有 则置为1
     *
     * @param student
     * @return
     */
    Student changeBaseStudent(Student student);



}
