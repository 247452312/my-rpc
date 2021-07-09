package indi.uhyils;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactory;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;
import indi.uhyils.rpc.factory.RpcBeanFactory;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.netty.callback.impl.RpcDefaultRequestCallBack;
import indi.uhyils.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import indi.uhyils.rpc.netty.enums.RpcNettyTypeEnum;
import indi.uhyils.rpc.netty.factory.NettyInitDtoFactory;
import indi.uhyils.rpc.netty.factory.RpcNettyFactory;
import indi.uhyils.rpc.netty.function.FunctionOne;
import indi.uhyils.rpc.netty.function.FunctionOneInterface;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 16时38分
 */
class RpcNettyNormalProviderTest {

    @org.junit.jupiter.api.Test
    void providerInit() throws Exception {
        HashMap<String, Object> beans = new HashMap<>();
        FunctionOneInterface functionOneInterface = new FunctionOne();
        beans.put(functionOneInterface.getClass().getName(), functionOneInterface);
        RpcBeanFactory instance = RpcBeanFactory.getInstance(beans);
        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.PROVIDER, NettyInitDtoFactory.createNettyInitDto("127.0.0.1", 8081, 1, new RpcDefaultRequestCallBack(instance.getRpcBeans())));
        System.out.println("providerStart");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void consumerInit() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.CONSUMER, NettyInitDtoFactory.createNettyInitDto("127.0.0.1", 1, 8081, new RpcDefaultResponseCallBack()));
        System.out.println("consumerStart");
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData getHeader = build.createByInfo(9L, null, new RpcHeader[]{rpcHeader}, "indi.uhyils.rpc.netty.function.FunctionOne", "1", "add", "java.lang.Integer;java.lang.Integer", "[1,2]", "[]");

        assert netty != null;
        RpcData rpcData = netty.sendMsg(getHeader);
        System.out.println("sendOver");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        netty.sendMsg(getHeader);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
