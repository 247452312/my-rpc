package indi.uhyils.rpc.util;




import java.util.Map;
import java.util.function.Supplier;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月12日 17时55分
 */
public class MapUtil {

    private MapUtil() {
    }

    /**
     * 同{@link Map#putIfAbsent(Object, Object)} 只是防止提前使用
     *
     * @param map           map本p
     * @param key           key
     * @param valueSupplier 执行后返回目标值的方法
     * @param <K>           key的类型
     * @param <V>           目标值的类型
     * @return 如果map中存在, 则使用map中的, 如果不存在, 则使用supplier返回的
     */
    public static <V, K> V putIfAbsent(Map<K, V> map, K key, Supplier<V> valueSupplier) {
        V v = map.get(key);
        if (v == null) {
            v = map.put(key, valueSupplier.get());
        }

        return v;
    }

}
