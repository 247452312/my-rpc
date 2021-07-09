package indi.uhyils.rpc.annotation;

import java.lang.annotation.*;

/**
 * rpc启动标注,将此类标注在启动类上,默认以此注解所在的包为扫描范围
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 07时10分
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRpc {
    /**
     * 手动添加扫描包
     *
     * @return
     */
    String[] baseScanPackage() default {};

    /**
     * 排除的包
     *
     * @return
     */
    String[] excludePackage() default {};
}
