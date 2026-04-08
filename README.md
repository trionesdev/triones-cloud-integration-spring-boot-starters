# 云服务集成的Spring Boot Starter 包

> 对多个云服务平台的 对象存储，短信，等通用业务进行统一抽象定义，满足在切换不同平台服务的时候，只需要更新配置文件，而不需要改动代码。

## 模块

- 阿里云
    - [OSS 对象存储](triones-csi-aliyun-spring-boot-starter/triones-csi-aliyun-oss-spring-boot-starter)
    - [SMS 短信服务](triones-csi-aliyun-spring-boot-starter/triones-csi-aliyun-sms-spring-boot-starter)
- 腾讯云
    - [COS 对象存储](triones-csi-tencentcloud-spring-boot-starter/triones-csi-tencentcloud-cos-spring-boot-starter)
    - [SMS 短信服务](triones-csi-tencentcloud-spring-boot-starter/triones-csi-tencentcloud-sms-spring-boot-starter)
    - [SES 邮件服务](triones-csi-tencentcloud-spring-boot-starter/triones-csi-tencentcloud-ses-spring-boot-starter)
    - [OCR 文本识别](triones-csi-tencentcloud-spring-boot-starter/triones-csi-tencentcloud-ocr-spring-boot-starter)
- 华为云
    - [OBS 对象存储](triones-csi-huaweicloud-spring-boot-starter/triones-csi-huaweicloud-obs-spring-boot-starter)
    - [SMS 短信服务](triones-csi-huaweicloud-spring-boot-starter/triones-csi-huaweicloud-sms-spring-boot-starter)
- 七牛云
    - [KODO 对象存储](triones-csi-qiniu-spring-boot-starter/triones-csi-qiniu-kudo-spring-boot-starter)
- 微软云
    - [BLOB 块存储](triones-csi-azure-spring-boot-starter/triones-csi-azure-blob-spring-boot-starter)

- 自部署服务
    - [minio](triones-csi-selfhosted-spring-boot-starter/triones-csi-minio-spring-boot-starter)

## 使用

### 引入依赖

#### Spring boot2.x

```
<dependency>
    <groupId>com.trionesdev.csi</groupId>
    <artifactId>triones-cloud-integration-spring-boot-starters</artifactId>
    <version>1.0-SNAPSHOT</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

#### Spring Boot3.x

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.trionesdev.csi</groupId>
            <artifactId>csi-spring-boot-dependencies</artifactId>
            <version>版本号</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

### 关注我们，一起交流

> 留言回复不及时，可以通过关注公众号联系我们
<div style="text-align: center">
<img src="images/shuque_wx.jpg" width="200px" alt="">
</div>