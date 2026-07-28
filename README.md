# 云服务集成的Spring Boot Starter 包

> 对多个云服务平台的 对象存储，短信，等通用业务进行统一抽象定义，满足在切换不同平台服务的时候，只需要更新配置文件，而不需要改动代码。

## 模块

- 阿里云
    - [OSS 对象存储](csi-spring-boot-starters/csi-aliyun-oss-spring-boot-starter)
    - [SMS 短信服务](csi-spring-boot-starters/csi-aliyun-sms-spring-boot-starter)
- 腾讯云
    - [COS 对象存储](csi-spring-boot-starters/csi-tencentcloud-cos-spring-boot-starter)
    - [SMS 短信服务](csi-spring-boot-starters/csi-tencentcloud-sms-spring-boot-starter)
    - [SES 邮件服务](csi-spring-boot-starters/csi-tencentcloud-ses-spring-boot-starter)
    - [OCR 文本识别](csi-spring-boot-starters/csi-tencentcloud-ocr-spring-boot-starter)
- 华为云
    - [OBS 对象存储](csi-spring-boot-starters/csi-huaweicloud-obs-spring-boot-starter)
    - [SMS 短信服务](csi-spring-boot-starters/csi-huaweicloud-sms-spring-boot-starter)
- 七牛云
    - [KODO 对象存储](csi-spring-boot-starters/csi-qiniu-kudo-spring-boot-starter)
- 微软云
    - [BLOB 块存储](csi-spring-boot-starters/csi-azure-blob-spring-boot-starter)

- 自部署服务
    - [Minio](csi-spring-boot-starters/csi-minio-spring-boot-starter)
    - [Rustfs](csi-spring-boot-starters/csi-rustfs-spring-boot-starter)

## 使用

### 引入依赖

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
