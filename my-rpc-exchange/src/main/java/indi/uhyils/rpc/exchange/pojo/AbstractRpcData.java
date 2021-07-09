package indi.uhyils.rpc.exchange.pojo;

import indi.uhyils.rpc.exception.MyRpcException;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exception.RpcTypeNotSupportedException;
import indi.uhyils.rpc.exception.RpcVersionNotSupportedException;
import indi.uhyils.rpc.exchange.content.MyRpcContent;
import indi.uhyils.rpc.exchange.pojo.factory.RpcHeaderFactory;
import indi.uhyils.rpc.util.BytesUtils;
import indi.uhyils.rpc.util.LogUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * rpc体模板,用来规定rpc应该有的东西
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 11时16分
 */
public abstract class AbstractRpcData implements RpcData {
    /**
     * 换行符
     */
    protected static final byte ENTER = '\n';
    /**
     * 此类支持的最大版本
     */
    private static final Integer MAX_VERSION = 1;
    /**
     * 版本
     */
    protected Integer version;

    /**
     * 数据体类型 0->请求 1->响应
     */
    protected Integer type;

    /**
     * 内容大小
     */
    protected Integer size;

    /**
     * 状态
     */
    protected byte status;

    /**
     * 唯一标识
     */
    protected Long unique;

    /**
     * head
     */
    protected RpcHeader[] headers;

    /**
     * 拆出来的content字符串
     */
    protected String[] contentArray;

    /**
     * 内容
     */
    protected RpcContent content;

    protected AbstractRpcData() {
    }

    @Override
    public void init(final Object... params) throws Exception {
        byte[] data = (byte[]) params[0];
        doInit(data);
    }

    /**
     * 初始化内容
     *
     * @throws ClassNotFoundException 初始化时如果对应方法未找到
     * @throws RpcException           rpc错误
     */
    protected abstract void initContent() throws RpcException, ClassNotFoundException;

    /**
     * 填充size
     *
     * @param data
     * @param readIndex
     */
    protected void initSize(byte[] data, AtomicInteger readIndex) {
        final int sizeSize = MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_SIZE_INDEX);
        final int startIndex = readIndex.get();
        byte[] sizeBytes = Arrays.copyOfRange(data, startIndex, startIndex + sizeSize);
        this.size = BytesUtils.changeByteToInteger(sizeBytes);
        readIndex.addAndGet(sizeSize);
    }

    /**
     * 获取rpc全部
     *
     * @return
     */
    @Override
    public byte[] toBytes() {
        //头部
        byte[] previousBytes = new byte[MyRpcContent.RPC_DATA_ITEM_SIZE.stream().mapToInt(t -> t).sum()];
        // 写索引
        AtomicInteger writeIndex = new AtomicInteger(0);
        // 写入mark头
        System.arraycopy(
                MyRpcContent.AGREEMENT_START,
                0,
                previousBytes,
                writeIndex.getAndAdd(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_MARK_INDEX)),
                MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_MARK_INDEX));

        // 写入version and type
        byte[] src = {(byte) ((rpcVersion() << 2) + (type() << 1))};
        int andAdd = writeIndex.getAndAdd(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_VERSION_REQ_RES_INDEX));
        System.arraycopy(src, 0, previousBytes, andAdd, MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_VERSION_REQ_RES_INDEX));

        //写入size,并获取head 和 content 的数组
        byte[] headAndContent = headerAndContent().getBytes(StandardCharsets.UTF_8);
        System.arraycopy(BytesUtils.changeIntegerToByte(headAndContent.length), 0, previousBytes, writeIndex.getAndAdd(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_SIZE_INDEX)), MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_SIZE_INDEX));

        // 写入状态
        previousBytes[writeIndex.getAndAdd(1)] = getStatus();

        //写入唯一标示
        byte[] uniqueBytes = BytesUtils.changeLongToByte(getUnique());
        System.arraycopy(uniqueBytes,
                0,
                previousBytes,
                writeIndex.getAndAdd(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_UNIQUE_INDEX)),
                MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_UNIQUE_INDEX));

        byte[] result = new byte[previousBytes.length + headAndContent.length];
        System.arraycopy(previousBytes, 0, result, 0, previousBytes.length);
        System.arraycopy(headAndContent, 0, result, previousBytes.length, headAndContent.length);

        return result;
    }

    private void doInit(final byte[] data) throws RpcException, ClassNotFoundException {
        try {
            AtomicInteger readIndex = new AtomicInteger(0);
            // 判断是不是myRpc的协议
            isMyRpc(data, readIndex);

            // 确定版本以及类型是否兼容(正确)
            initVersionAndType(data, readIndex);

            // 填充size
            initSize(data, readIndex);

            //填充状态
            initStatus(data, readIndex);

            //填充唯一标识
            initUnique(data, readIndex);

            // 获取header
            initHeader(data, readIndex);

            // 获取内容字符串
            initContentArray(data, readIndex);

            // 处理内容
            initContent();
        } catch (RpcException | ClassNotFoundException e) {
            LogUtil.error(this, e);
            throw e;
        }
    }

    protected void initUnique(byte[] data, AtomicInteger readIndex) {
        final int uniqueSize = MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_UNIQUE_INDEX);
        final int startIndex = readIndex.get();
        byte[] uniqueBytes = Arrays.copyOfRange(data, startIndex, startIndex + uniqueSize);
        this.unique = BytesUtils.changeByteToLong(uniqueBytes);
        readIndex.addAndGet(uniqueSize);
    }

    protected void initStatus(byte[] data, AtomicInteger readIndex) {
        final int statusSize = MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_STATUS_INDEX);
        assert statusSize == 1;
        final int startIndex = readIndex.get();
        final byte[] dataStatus = Arrays.copyOfRange(data, startIndex, startIndex + statusSize);
        // 先这么搞..
        this.setStatus(dataStatus[0]);
        readIndex.addAndGet(statusSize);
    }

    protected void initContentArray(byte[] data, AtomicInteger readIndex) {
        byte[] bytes = Arrays.copyOfRange(data, readIndex.get(), data.length);
        String contentStr = new String(bytes, StandardCharsets.UTF_8);
        this.contentArray = contentStr.split("\n");
    }

    protected void initHeader(byte[] data, AtomicInteger readIndex) {
        boolean lastByteIsEnter = Boolean.FALSE;
        List<RpcHeader> rpcHeaders = new ArrayList<>();
        StringBuilder headerStr = new StringBuilder();
        int headerEnd = 0;
        for (int i = readIndex.get(); i < data.length; i++) {
            headerEnd++;
            if (Objects.equals(data[i], ENTER)) {
                if (lastByteIsEnter) {
                    break;
                }
                lastByteIsEnter = Boolean.TRUE;
                RpcHeader rpcHeader = RpcHeaderFactory.newHeader(headerStr.toString());
                headerStr.delete(0, headerStr.length());
                if (rpcHeader != null) {
                    rpcHeaders.add(rpcHeader);
                }
            } else {
                headerStr.append((char) data[i]);
                lastByteIsEnter = Boolean.FALSE;
            }
        }
        this.headers = rpcHeaders.toArray(new RpcHeader[]{new RpcHeader()});
        readIndex.addAndGet(headerEnd);
    }

    /**
     * 确定版本以及类型是否兼容(正确)
     *
     * @param data
     * @param readIndex
     * @throws RpcVersionNotSupportedException
     */
    protected void initVersionAndType(byte[] data, AtomicInteger readIndex) throws RpcException {
        int dataVersion = (data[readIndex.get()] >> 2) & 0b111111;
        if (dataVersion > MAX_VERSION) {
            throw new RpcVersionNotSupportedException(dataVersion, MAX_VERSION);
        }
        int dataType = (data[readIndex.get()] & 0b10) >> 1;
        if (!Objects.equals(dataType, type())) {
            throw new RpcTypeNotSupportedException(dataType, type());
        }
        this.version = dataVersion;
        readIndex.addAndGet(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_VERSION_REQ_RES_INDEX));

    }

    /**
     * 判断是不是myRpc的协议
     *
     * @param data
     * @param readIndex
     * @throws MyRpcException
     */
    private void isMyRpc(byte[] data, AtomicInteger readIndex) throws MyRpcException {
        int from = readIndex.get();
        byte[] bytes = Arrays.copyOfRange(data, from, from + MyRpcContent.AGREEMENT_START.length);
        boolean startByteEquals = Arrays.equals(bytes, MyRpcContent.AGREEMENT_START);
        if (!startByteEquals) {
            throw new MyRpcException();
        }
        readIndex.addAndGet(MyRpcContent.RPC_DATA_ITEM_SIZE.get(MyRpcContent.RPC_DATA_MARK_INDEX));
    }


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public RpcHeader[] getHeaders() {
        return headers;
    }

    public void setHeaders(RpcHeader[] headers) {
        this.headers = headers;
    }


    public RpcContent getContent() {
        return content;
    }

    public void setContent(RpcContent content) {
        this.content = content;
    }

    @Override
    public Long unique() {
        return unique;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Long getUnique() {
        return unique;
    }

    public void setUnique(Long unique) {
        this.unique = unique;
    }

    public String[] getContentArray() {
        return contentArray;
    }

    public void setContentArray(String[] contentArray) {
        this.contentArray = contentArray;
    }

    @Override
    public String headerAndContent() {
        StringBuilder sb = new StringBuilder();
        for (RpcHeader rpcHeader : rpcHeaders()) {
            sb.append("\n");
            sb.append(String.format("%s:%s", rpcHeader.getName(), rpcHeader.getValue()));
        }
        sb.append("\n");
        sb.append("\n");
        sb.append(this.content().contentString());
        return sb.toString();
    }
}
