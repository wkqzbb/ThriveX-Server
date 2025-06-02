# 第一阶段：构建阶段
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/maven:latest AS build
WORKDIR /build

# 复制Maven配置
COPY settings.xml /root/.m2/settings.xml

# 先复制pom文件并下载依赖，利用Docker缓存机制
COPY pom.xml /build/
COPY model/pom.xml /build/model/
COPY blog/pom.xml /build/blog/

# 预下载依赖
RUN mvn dependency:go-offline -B

# 复制源代码
COPY model/src /build/model/src
COPY blog/src /build/blog/src

# 使用Maven打包，显示详细日志
RUN mvn clean package -DskipTests -e

# 查找构建产物并重命名为app.jar
RUN find /build/blog/target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" -not -name "*tests.jar" -exec cp {} /build/app.jar \;

# 第二阶段：运行阶段
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:17-slim

# 设置应用程序的网络端口配置
ENV PORT 9003

# 配置数据库连接参数（数据库地址/端口、数据库名称）
ENV DB_PORT 3306
ENV DB_NAME myblog
ENV DB_HOST 127.0.0.1
ENV DB_USERNAME root
ENV DB_PASSWORD Zbb010719!
ENV DB_INFO=${DB_HOST}:${DB_PORT}/${DB_NAME}

# 配置邮件服务器连接参数（SMTP服务器地址、端口及认证信息）
ENV EMAIL_HOST smtp.qq.com
ENV EMAIL_PORT 465
ENV EMAIL_USERNAME wang.kaiqi@foxmail.com
ENV EMAIL_PASSWORD jcibunfsgpfuecje

# 设置工作目录
WORKDIR /server

# 从构建阶段复制打包好的JAR文件
COPY --from=build /build/app.jar /server/app.jar

# 添加启动脚本
COPY RUN.sh /server/RUN.sh

# 设置启动命令
ENTRYPOINT ["/server/RUN.sh"]
