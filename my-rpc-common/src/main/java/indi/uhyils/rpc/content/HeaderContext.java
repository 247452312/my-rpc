package indi.uhyils.rpc.content;

import com.sun.istack.internal.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 传递时的header暂存地
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月11日 09时08分
 */
public class HeaderContext {


    /**
     * 标题头
     */
    private static volatile ThreadLocal<Map<String, String>> headers = new ThreadLocal<>();

    /**
     * 获取header
     *
     * @return
     */
    @Nullable
    public static Map<String, String> get() {
        return headers.get();
    }

    /**
     * 批量添加header
     *
     * @param headerMap
     */
    public static void addHeaders(Map<String, String> headerMap) {
        if (headerMap == null) {
            return;
        }
        initHeaders();
        headers.get().putAll(headerMap);
    }


    /**
     * 添加header
     *
     * @param key
     * @param value
     */
    public static void addHeader(String key, String value) {
        initHeaders();
        headers.get().put(key, value);
    }

    /**
     * 初始化header
     */
    private static void initHeaders() {
        if (headers.get() == null) {
            headers.set(new HashMap<>());
        }
    }

    /**
     * 是否包含
     *
     * @param key
     *
     * @return
     */
    public static Boolean contains(String key) {
        if (headers.get() == null) {
            return false;
        }
        return headers.get().containsKey(key);
    }

    /**
     * 清空
     */
    public static void remove() {
        headers.remove();
    }


}
