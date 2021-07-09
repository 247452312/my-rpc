package indi.uhyils.rpc.netty.spi.filter;

import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResult;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResultImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * invoker时传递的上下文
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 07时21分
 */
public class FilterContext {

    /**
     * netty的key
     */
    public static final String NETTY_KEY = "netty";

    /**
     * 生产者需要存储结果的key
     */
    public static final String RESULT_KEY = "result";

    private RpcResult rpcResult = new RpcResultImpl();

    private Map<String, Object> info = new HashMap<>();

    public Object put(String name, Object info) {
        return this.info.put(name, info);
    }

    public Object remove(String name) {
        return this.info.remove(name);
    }

    public Object get(String name) {
        return this.info.get(name);
    }

    public Set<Map.Entry<String, Object>> entitySet() {
        return info.entrySet();
    }

    public RpcResult getRpcResult() {
        return rpcResult;
    }
}
