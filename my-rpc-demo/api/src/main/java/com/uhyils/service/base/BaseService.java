package com.uhyils.service.base;

import java.io.Serializable;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月08日 08时18分
 */
public interface BaseService<T extends Serializable> {

    /**
     * 修改id+1 如果没有 则置为1
     *
     * @param student
     * @return
     */
    T changeStudent(T student);
}
