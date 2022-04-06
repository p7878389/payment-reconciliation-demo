### 通用支付对账demo

#### parse模块（文件下载、解析、入库流程）

- **FileParseStrategy** 文件解析策略，根据不同的文件类型使用不同策略读取文件内容，自定义文件解析策略需要实现该接口的相关方法
- **DataParseStrategy** 数据解析策略，将文件解析策略的数据解析成相信数据，自定义策略都需要实现该接口的相关方法并入库

#### RedisServices包含交集、差集相关方法

- **intersectAndStore** 获取交集，**需要注意key一定要用{}包裹**
    ~~~java
    redisServices.intersectAndStore("{account}:union",SetUtils.hashSet("{account}:localSet","{account}:outerSet"));
    ~~~
- **difference** 获取差集
    ~~~java
    // 与本地数据差异
     Set<Object> localSetDifference = redisServices.difference("{account}:localSet", "{account}:union");
    // 与对账文件差异     
    Set<Object> outerSetDifference = redisServices.difference("{account}:outerSet", "{account}:union");
  ~~~

#### 注意
- 当订单数据量很大的时候就不能在使用redis进行差异比较，也不能在使用mysql来对第三方账单进行存储
- 数据量大的情况可以使用大数据相关工具进行对账处理，例如spark、flink等