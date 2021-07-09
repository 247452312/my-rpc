package indi.uhyils.rpc.spi;


/**
 * rpc的拦截器,可扩展如果需要扩展proxy,分五步:
 * 1.需要实现此方法(必须)
 * 2.使用{@link indi.uhyils.rpc.annotation.RpcSpi}注解(必须)
 * 3.在META-INF/rpc中新建文件 名称: indi.uhyils.rpc.proxy.handler.RpcProxyHandlerInterface(必须)
 * 4.在新建的文件中写明要扩展的类的名称以及全称 例如: xxx_rpc_spi_proxy=indi.uhyils.rpc.proxy.handler.RpcProxyDefaultHandler(可选,如果在扫描范围内可不填,那么名称默认是类名首字母小写)
 * 5.在配置中写明rpc.custom.proxy=您要实现扩展的类的名称(如果是自己实现的,则必须,使用系统默认的,则不需要)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时04分
 */
public interface RpcSpiExtension {

    /**
     * 默认实现的原型模式的clone方法,子类如果想要实现带有参数的克隆,则重写此方法
     *
     * @return new出来的一个神奇的object
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    default Object rpcClone() throws IllegalAccessException, InstantiationException {
        return this.getClass().newInstance();
    }

    /**
     * 初始化rpcSpi的方法
     *
     * @param params
     */
    default void init(final Object... params) throws Exception {

    }

}
