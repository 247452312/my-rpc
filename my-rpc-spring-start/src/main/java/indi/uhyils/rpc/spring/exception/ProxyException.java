package indi.uhyils.rpc.spring.exception;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月12日 14时00分
 */
public class ProxyException extends Exception {


    public ProxyException(Object obj) {
        super(obj.toString() + "不是被代理的类");
    }
}
