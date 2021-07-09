package indi.uhyils.rpc.cluster;

import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactory;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;
import indi.uhyils.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import indi.uhyils.rpc.netty.function.FunctionOne;
import indi.uhyils.rpc.netty.function.FunctionOneInterface;
import indi.uhyils.rpc.netty.pojo.NettyInitDto;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 18时58分
 */
class ClusterFactoryTest {

    @org.junit.jupiter.api.Test
    void createDefaultProviderCluster1() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        HashMap<String, Object> beans = new HashMap<>();
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Class<? extends FunctionOneInterface> aClass = functionOneInterface.getClass();
        beans.put(aClass.getName(), functionOneInterface);
        Cluster defaultProviderCluster = ClusterFactory.createDefaultProviderCluster(8082, beans);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.isTrue(true, "hello World");
    }

    @org.junit.jupiter.api.Test
    void createDefaultProviderCluster2() throws Exception {
        HashMap<String, Object> beans = new HashMap<>();

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Class<? extends FunctionOneInterface> aClass = functionOneInterface.getClass();
        beans.put(aClass.getName(), functionOneInterface);
        Cluster defaultProviderCluster = ClusterFactory.createDefaultProviderCluster(8083, beans);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.isTrue(true, "hello World");
    }

    @org.junit.jupiter.api.Test
    void createDefaultConsumerCluster() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        NettyInitDto nettyInit1 = new NettyInitDto();
        nettyInit1.setCallback(new RpcDefaultResponseCallBack());
        nettyInit1.setHost("127.0.0.1");
        nettyInit1.setPort(8082);
        NettyInitDto nettyInit2 = new NettyInitDto();
        nettyInit2.setCallback(new RpcDefaultResponseCallBack());
        nettyInit2.setHost("127.0.0.1");
        nettyInit2.setPort(8083);
        Class<? extends FunctionOneInterface> aClass = FunctionOneInterface.class;
        Cluster defaultConsumerCluster = ClusterFactory.createDefaultConsumerCluster(aClass, nettyInit1, nettyInit2);
        System.out.println("--------------------------------------------------------------------consumerStart");
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData getHeader = build.createByInfo(9L, null, new RpcHeader[]{rpcHeader}, "indi.uhyils.rpc.netty.function.FunctionOne", "1", "add", "java.lang.Integer;java.lang.Integer", "[1,2]", "[]");

        SendInfo info = new SendInfo();
        info.setIp("192.168.1.101");
        defaultConsumerCluster.sendMsg(getHeader, info);
        System.out.println("sendOver");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            defaultConsumerCluster.sendMsg(getHeader, info);
            System.out.println("--------------------------这里发送了" + i + "次");
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.isTrue(true, "hello World");
    }
}
