package indi.uhyils.rpc.netty.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 定长队列 环状
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 14时02分
 */
public class FixedLengthQueue<T extends Serializable> implements Serializable {

    /**
     * 定长
     */
    private Integer length;

    /**
     * 数组
     */
    private T[] data;

    /**
     * 读写指针
     */
    private volatile AtomicInteger readWritePosition;

    public FixedLengthQueue(Class<T> clazz) {
        this(8, clazz);
    }

    public FixedLengthQueue(Integer length, Class<T> clazz) {
        this.length = length;
        readWritePosition = new AtomicInteger(0);
        data = (T[]) Array.newInstance(clazz, length);
    }

    public FixedLengthQueue(T[] data) {
        this.data = data;
        length = data.length;
        // 指向最后一个
        readWritePosition = new AtomicInteger(data.length - 1);
    }

    public T add(T t) {
        T datum = data[readWritePosition.get()];
        data[readWritePosition.get()] = t;
        readWritePosition.getAndAdd(1);
        if (readWritePosition.get() > length) {
            readWritePosition.set(0);
        }
        return datum;
    }

    public Boolean contain(T t) {
        return ArrayUtils.contains(data, t);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getReadWritePosition() {
        return readWritePosition.get();
    }

    public void setReadWritePosition(Integer readWritePosition) {
        this.readWritePosition.set(readWritePosition);
    }
}
