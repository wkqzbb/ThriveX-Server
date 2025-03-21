# 设置基础镜像
FROM openjdk:11.0-jre-buster
# 设置应用程序的网络端口配置
ENV PORT 9003

# 配置数据库连接参数（数据库地址/端口、数据库名称）
ENV DB_INFO localhost:3306/thrive
ENV DB_USERNAME root
ENV DB_PASSWORD root

# 配置邮件服务器连接参数（SMTP服务器地址、端口及认证信息）
ENV EMAIL_HOST mail.qq.com
ENV EMAIL_PORT 465
ENV EMAIL_USERNAME 123456789@qq.com
ENV EMAIL_PASSWORD 123456789
ARG VERSION=2.4.7

# 设置工作目录
WORKDIR /server
# 添加jar包
ADD https://github.com/LiuYuYang01/ThriveX-Server/releases/download/${VERSION}/blog.jar /server/app.jar
# 添加启动脚本
COPY RUN.sh /server/RUN.sh
# 设置权限
RUN chmod +x /server/RUN.sh

# 设置启动命令
ENTRYPOINT ["/server/RUN.sh"]
