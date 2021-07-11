package indi.uhyils.rpc.registry.exception;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 09时01分
 */
public class RegistryTypeException extends RegistryException {

    public RegistryTypeException() {
        super("消费者不需要注册");
    }
}
