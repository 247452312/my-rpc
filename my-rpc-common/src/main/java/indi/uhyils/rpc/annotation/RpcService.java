package indi.uhyils.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用这个类可以将此类做成一个rpc bean 并向注册中心注册
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 07时19分
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {


}
