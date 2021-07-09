package indi.uhyils.rpc.netty;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 14时14分
 */
public abstract class AbstractRpcNetty implements RpcNetty {

    /**
     * 超时时间
     */
    protected Long timeOut;
    /**
     * bootstrap
     */
    protected AbstractBootstrap<?, ? extends Channel> bootstrap;

    protected AbstractRpcNetty() {
    }

    @Override
    public void init(Object... params) throws Exception {
        this.timeOut = (Long) params[0];
    }

    @Override
    public AbstractBootstrap<?, ? extends Channel> getBootstrap() {
        return bootstrap;
    }

    @Override
    public void setBootstrap(AbstractBootstrap<?, ? extends Channel> bootstrap) {
        this.bootstrap = bootstrap;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }
}
