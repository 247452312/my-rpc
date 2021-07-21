package indi.uhyils.rpc.netty.callback;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.content.RpcContent;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.pojo.InvokeResult;
import indi.uhyils.rpc.spi.RpcSpiExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 18时55分
 */
public interface RpcCallBack extends RpcSpiExtension {


    /**
     * 获取rpc数据
     *
     * @param data
     *
     * @return
     *
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcData createRpcData(byte[] data) throws Exception;

    /**
     * 获取rpc体
     *
     * @param data
     *
     * @return
     */
    RpcContent getContent(byte[] data) throws Exception;

    /**
     * 执行方法,下一级去实现此方法
     *
     * @param content
     *
     * @return
     *
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    InvokeResult invoke(RpcContent content);


    /**
     * 组装返回值
     *
     * @param unique
     * @param result
     *
     * @return
     */
    RpcData assembly(Long unique, InvokeResult result) throws RpcException, ClassNotFoundException;

}
