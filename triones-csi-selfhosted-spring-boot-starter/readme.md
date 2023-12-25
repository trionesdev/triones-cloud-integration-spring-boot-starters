# 自托管

## minio

### 添加依赖
```
<dependency>
    <groupId>com.trionesdev.csi</groupId>
    <artifactId>triones-csi-minio-spring-boot-starter</artifactId>
</dependency>
```

### 使用方法

只对接单个minio
```text
triones.minio.enabled=true
triones.minio.endpoint= //地址
triones.minio.accessKey= //key
triones.minio.secretKey= //秘钥
triones.minio.bucket= //bucket
triones.minio.urlPrefix= //返回值前缀，和objectName一起拼接
```


对接多个minio
#### 在启动类上添加注解 @EnableMinioClients
#### 添加Client

```java
import com.trionesdev.csi.api.oss.OssTemplate;
import com.trionesdev.csi.minio.annotation.MinioClient;

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