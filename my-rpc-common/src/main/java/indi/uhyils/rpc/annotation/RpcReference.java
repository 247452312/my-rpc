package indi.uhyils.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解可以在方法或属性上
 * 如果在属性上,则会中rpc缓存中获取指定的类注入{@link indi.uhyils.rpc.spring.RpcConsumerBeanFieldInjectConfiguration#fieldInject(java.lang.Object)}
 * 如果在方法上,则会判断方法入参是否在缓存中存在{@link indi.uhyils.rpc.spring.RpcConsumerBeanFieldInjectConfiguration#methodInject(java.lang.Object)}
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 07时19分
 */
@Documented
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {

    /**
     * 如果此值为true 则从spring中查询本项目中的bean,如果查询到,则使用本项目中的bean,没有查询到才会查询rpc
     *
     * @return
     */
    boolean inConnection() default false;
}
