package indi.uhyils.rpc.exception;

/**
 * id生成错误
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月15日 19时15分
 */
public class IdGenerationException extends RuntimeException {

    public IdGenerationException(String msg) {
        super("id生成错误:" + msg);
    }

}
