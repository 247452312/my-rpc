package indi.uhyils.rpc.annotation;


import java.lang.annotation.*;

/**
 * 扩展注释,此注释和{META-INF/rpc/}下的文件配合使用
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 07时58分
 * @see indi.uhyils.rpc.spi.RpcSpiExtension
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcSpi {

    /**
     * 排序,数值越小越靠前
     *
     * @return
     */
    int order() default 50;

    /**
     * 此扩展点的名称
     *
     * @return
     */
    String name() default "";

    /**
     * 是否是单例,如果不是就使用原型模式,自行实现cloneable接口
     *
     * @return
     */
    boolean single() default true;
}
