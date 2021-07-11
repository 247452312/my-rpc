package indi.uhyils.rpc.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 个性化配置
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年05月30日 15时43分
 */
public class CustomConfig implements Serializable {

    private static final long serialVersionUID = 6547911512988920154L;

    /**
     * 自定义扩展
     */
    private Map<String, Object> custom = new HashMap<>();


    public Map<String, Object> getCustom() {
        return custom;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }
}
