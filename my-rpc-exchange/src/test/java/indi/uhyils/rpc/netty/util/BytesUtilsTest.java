package indi.uhyils.rpc.netty.util;

import indi.uhyils.rpc.util.BytesUtils;
import org.springframework.util.Assert;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月22日 11时05分
 */
class BytesUtilsTest {
    @org.junit.jupiter.api.Test
    void changeIntegerToByte() {
        int data = 65535;
        byte[] bytes = BytesUtils.changeIntegerToByte(data);
        Integer integer = BytesUtils.changeByteToInteger(bytes);
        Assert.isTrue(true, "");
    }
}
