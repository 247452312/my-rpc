package indi.uhyils.rpc.util.pojo;

import java.io.Serializable;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月15日 22时14分
 */
public class ThreadLayerInfo implements Serializable {

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 调用行数
     */
    private int lineNumber;


    public static ThreadLayerInfo build(String className, String methodName, String fileName, int lineNumber) {
        ThreadLayerInfo build = new ThreadLayerInfo();
        build.className = className;
        build.methodName = methodName;
        build.fileName = fileName;
        build.lineNumber = lineNumber;
        return build;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThreadLayerInfo{");
        sb.append("className='").append(className).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", lineNumber=").append(lineNumber);
        sb.append('}');
        return sb.toString();
    }
}
