package indi.uhyils;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.netty.function.FunctionOneInterface;
import indi.uhyils.rpc.proxy.RpcProxyFactory;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月28日 07时05分
 */
class ProxyFactoryTest {

    public static void main(String[] args) throws Throwable {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOne = RpcProxyFactory.newProxy(FunctionOneInterface.class);
        Integer add = functionOne.add(1, 2);
        System.out.println(add);
        for (int i = 0; i < 10; i++) {
            Integer add1 = functionOne.add(1, i);
            System.out.println(add1);
        }
        System.in.read();
    }
}
