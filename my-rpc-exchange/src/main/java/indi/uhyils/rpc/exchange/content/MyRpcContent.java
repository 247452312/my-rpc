package indi.uhyils.rpc.exchange.content;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 09时23分
 */
public class MyRpcContent {

    /**
     * rpc版本
     */
    public static final int VERSION = 1;
    /**
     * rpc头数据结构标记大小
     */
    public static final List<Integer> RPC_DATA_ITEM_SIZE = Collections.unmodifiableList(Arrays.asList(2, 1, 4, 1, 8));
    /**
     * mark在头中所占的位
     */
    public static final int RPC_DATA_MARK_INDEX = 0;
    /**
     * 版本和RPC体类型(请求/回应)在头中所占的位
     */
    public static final int RPC_DATA_VERSION_REQ_RES_INDEX = 1;
    /**
     * 请求体大小在头中所占的位
     */
    public static final int RPC_DATA_SIZE_INDEX = 2;
    /**
     * 状态在头中所占的位
     */
    public static final int RPC_DATA_STATUS_INDEX = 3;
    /**
     * 唯一标示在头中所占的位
     */
    public static final int RPC_DATA_UNIQUE_INDEX = 4;
    /**
     * rpc心跳请求
     */
    public static final RpcData RPC_HEALTH_DATA = RpcFactoryProducer.build(RpcTypeEnum.REQUEST).getHealth();
    /**
     * rpc标志
     */
    private static final int AGREEMENT_START_INT = 0x929d;
    /**
     * rpc标志,两字节占用
     */
    public static final byte[] AGREEMENT_START = new byte[]{
            (byte) (AGREEMENT_START_INT >> 8 & 0xff),
            (byte) (AGREEMENT_START_INT & 0xff)
    };

    private MyRpcContent() {
    }


}
