# 其他云服务或者本地化方案

## minio

### 添加依赖
```
<dependency>
    <groupId>com.moensun.cloud.integration</groupId>
    <artifactId>other-spring-boot-starter</artifactId>
</dependency>
```

### 使用方法

#### 在启动类上添加注解 @EnableMinioClients
#### 添加Client

```java
import com.moensun.cloud.integration.api.oss.OssTemplate;
import com.moensun.cloud.integration.starter.other.minio.annotations.MinioClient;

@MinioClient(
        secretKey = "", 
        accessKey = "",
        endpoint = "",
        bucket = "",
        urlPrefix = "" //拼接URL的前缀
)
public interface MinioOssClient extends OssTemplate {
    
}
```