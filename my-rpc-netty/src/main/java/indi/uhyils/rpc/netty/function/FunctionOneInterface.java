package indi.uhyils.rpc.netty.function;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月28日 07时12分
 */
public interface FunctionOneInterface extends Serializable {
    Integer add(Integer a, Integer b);
}
