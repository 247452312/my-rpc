# rpc模块

## 模块分层

1. exchange rpc协议支撑以及协议组装拆解包
2. netty rpc协议发送接收包,tcp长连接
3. cluster 负载均衡层,负责权重管理以及负载均衡方法,包含了多个netty
4. registry 注册中心层,负责维持与注册中心的连接
5. proxy 代理层,代理成用户指定的接口,隐藏底层实现,方便调用
6. spring-start spring对接层,引入此层对接入spring

包名  | 介绍  | 链接
 ---- | ----- | ------  
exchange  | rpc协议支撑以及协议组装拆解包 | [链接](my-common-rpc-exchange)
netty  | rpc协议发送接收包,tcp长连接 | [链接](my-common-rpc-netty)
cluster  | 负载均衡层,负责权重管理以及负载均衡方法,包含了多个netty | [链接](my-common-rpc-cluster)
registry  | 注册中心层,负责维持与注册中心的连接 | [链接](my-common-rpc-cluster)
proxy  | 代理层,代理成用户指定的接口,隐藏底层实现,方便调用 | [链接](my-common-rpc-proxy)
spring-start  | spring对接层,引入此层对接入spring | [链接](my-common-rpc-spring-start)

## 用法

该项目使用 [maven](http://maven.apache.org/download.cgi) 。如果您没有在本地安装它们，请检查它们。

```xml
<!--暂时没有在maven中央仓库,请下载自行安装后引入-->
<dependency>
    <groupId>indi.uhyils</groupId>
    <artifactId>my-common-rpc-spring-start</artifactId>
    <version>0.3.0-my-SNAPSHOT</version>
</dependency>
```

## 用法

1. 在启动类上添加 ***@MyRpc***
    - 会自动扫描此注解下的包以及此注解包含的包
2. 如果想暴露rpc方法 则在类上添加 ***@RpcService***
3. 如果想使用远程rpc方法 则在属性或方法上添加 ***@RpcReference***
4. 如果想自定义某个方法,则在自定义扩展上添加 ***@RpcSpi*** 
    - **(注:如果在 @MyRpc扫描范围内可不加)** 并且在**META-INF/rpc**中添加要扩展的类命名的文件,在此文件中添加(名称=类名)
