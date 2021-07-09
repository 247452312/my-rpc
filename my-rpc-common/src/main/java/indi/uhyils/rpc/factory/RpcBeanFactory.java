package indi.uhyils.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * rpc下的bean工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 06时54分
 */
public class RpcBeanFactory {


    /**
     * 单例
     */
    private volatile static RpcBeanFactory instance;
    /**
     * 是否初始化
     */
    private final Boolean init;
    /**
     * rpc所在的bean
     */
    private Map<String, Object> rpcBeans = new HashMap<>();

    private RpcBeanFactory(Map<String, Object> beans) {
        if (beans == null) {
            init = false;
            return;
        }
        init = Boolean.TRUE;
        rpcBeans.putAll(beans);
    }

    /**
     * 双重检测锁
     *
     * @return
     */
    public static RpcBeanFactory getInstance(Map<String, Object> beans) {
        if (null == instance) {
            if (beans == null) {
                throw new RuntimeException("rpc bean工厂第一次初始化需要参数");
            }
            synchronized (RpcBeanFactory.class) {
                if (null == instance) {
                    instance = new RpcBeanFactory(beans);
                }
            }
        }
        return instance;
    }

    /**
     * 双重检测锁 此处在已经调用过之后不会再报错
     *
     * @return
     */
    public static RpcBeanFactory getInstance() throws Exception {
        return getInstance(null);
    }


    public Map<String, Object> getRpcBeans() {
        return rpcBeans;
    }
}
