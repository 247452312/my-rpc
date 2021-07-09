package indi.uhyils.rpc.netty.factory;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.enums.RpcNettyTypeEnum;
import indi.uhyils.rpc.netty.pojo.NettyInitDto;
import indi.uhyils.rpc.spi.RpcSpiManager;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 13时39分
 */
public class RpcNettyFactory {

    private static final String PROVIDER_NETTY_DEFAULT_NAME = "PROVIDER_NETTY_DEFAULT_NAME";

    private static final String CONSUMER_NETTY_DEFAULT_NAME = "CONSUMER_NETTY_DEFAULT_NAME";


    private static final String PROVIDER_NETTY_SPI_NAME = "provider_netty_spi_name";

    private static final String CONSUMER_NETTY_SPI_NAME = "consumer_netty_spi_name";

    /**
     * 创建一个netty
     *
     * @param type
     * @return
     */
    public static RpcNetty createNetty(RpcNettyTypeEnum type, NettyInitDto nettyInit, Long outTime) throws Exception {
        switch (type) {
            case PROVIDER:
                return createProvider(nettyInit.getHost(), nettyInit.getPort(), nettyInit.getCallback(), outTime);
            case CONSUMER:
                return createConsumer(nettyInit.getHost(), nettyInit.getPort(), nettyInit.getCallback(), outTime);
            default:
                return null;
        }
    }

    /**
     * 创建一个netty
     *
     * @param type
     * @return
     */
    public static RpcNetty createNetty(RpcNettyTypeEnum type, NettyInitDto nettyInit) throws Exception {
        return createNetty(type, nettyInit, 6000L);
    }

    /**
     * 创建一个服务消费者
     *
     * @param host
     * @param port
     * @return
     */
    private static RpcNetty createConsumer(String host, Integer port, RpcCallBack callBack, Long outTime) throws Exception {

        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(CONSUMER_NETTY_SPI_NAME, CONSUMER_NETTY_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcNetty) RpcSpiManager.getExtensionByClass(RpcNetty.class, registryName, outTime, callBack, host, port);
    }

    /**
     * 创建一个服务提供者
     *
     * @param host
     * @param port
     * @return
     */
    private static RpcNetty createProvider(String host, Integer port, RpcCallBack callBack, Long outTime) throws Exception {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(PROVIDER_NETTY_SPI_NAME, PROVIDER_NETTY_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcNetty) RpcSpiManager.getExtensionByClass(RpcNetty.class, registryName, outTime, callBack, host, port);
    }
}
