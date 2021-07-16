package indi.uhyils;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import indi.uhyils.rpc.annotation.MyRpc;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.function.FunctionOne;
import indi.uhyils.rpc.netty.function.FunctionOneInterface;
import indi.uhyils.rpc.registry.Registry;
import indi.uhyils.rpc.registry.RegistryFactory;
import indi.uhyils.rpc.registry.content.RegistryContent;
import indi.uhyils.rpc.registry.mode.nacos.RegistryNacosMode;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 16时20分
 */
@MyRpc
class RegistryFactoryTest {

    @Test
    void createConsumer() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RegistryNacosMode mode = new RegistryNacosMode();
        Class<FunctionOneInterface> clazz = FunctionOneInterface.class;
        Registry<FunctionOneInterface> consumer = RegistryFactory.createConsumer(clazz);
        RpcData add = consumer.invoke(90L, "add", (Class<FunctionOneInterface>[]) clazz.getMethods()[0].getParameterTypes(), new Object[]{1, 2});

        System.out.println("--------------------------------------------------------------------" + add);

        for (int i = 0; i < 10; i++) {
            RpcData add1 = consumer.invoke(Integer.toUnsignedLong(i), "add", (Class<FunctionOneInterface>[]) clazz.getMethods()[0].getParameterTypes(), new Object[]{1, 2});
            System.out.println("-------------重复执行 这里是" + i + "遍结果是:   " + add1);

        }
        Assert.isTrue(true, "hello world");
    }

    @Test
    void createProvider1() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Registry<FunctionOneInterface> provider = RegistryFactory.createProvider(FunctionOneInterface.class, functionOneInterface);
        System.out.println("服务提供者服务加载完毕----------------------------!!!!!!! yeah");
        System.in.read();

        Assert.isTrue(true, "hello world");
    }

    @Test
    void createProvider2() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Registry<FunctionOneInterface> provider = RegistryFactory.createProvider(FunctionOneInterface.class, functionOneInterface);
        System.out.println("服务提供者服务加载完毕----------------------------!!!!!!! yeah");
        System.in.read();

        Assert.isTrue(true, "hello world");
    }

    @Test
    void testNacos() throws Exception {
        NamingService namingService = NamingFactory.createNamingService("192.168.1.101:8848");
        namingService.subscribe("indi.uhyils.rpc.netty.function.FunctionOneInterface", RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, event -> {
            if (event instanceof NamingEvent) {
                System.out.println(((NamingEvent) event).getServiceName());
                System.out.println(((NamingEvent) event).getGroupName());
                System.out.println(((NamingEvent) event).getInstances());
                System.out.println(((NamingEvent) event).getClusters());
            }
        });
        System.in.read();

        Assert.isTrue(true, "hello world");
    }
}
