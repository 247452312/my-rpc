package indi.uhyils.rpc.netty.function;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 19时31分
 */
//@RpcService
public class FunctionOne implements FunctionOneInterface {
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
}
