package indi.uhyils.rpc.netty.spi.filter;

import indi.uhyils.rpc.exchange.pojo.data.RpcData;
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


    private final RpcData requestData;

    private Map<String, Object> info = new HashMap<>();

    public FilterContext(RpcData requestData) {
        this.requestData = requestData;
    }

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

    public RpcData getRequestData() {
        return requestData;
    }

}
