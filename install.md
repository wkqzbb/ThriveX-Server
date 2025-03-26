# Installation

## 配置数据库-使用已有的数据库

> 假设你已经有了一个MySQL8的数据库，并且拥有root的权限。


使用`root`登录，执行下面的命令创建一个数据库：
```sql
CREATE DATABASE IF NOT EXISTS `ThriveX` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `ThriveX`;
```

然后创建用户

```sql
create user 'thrive'@'%' identified by 'ThriveX@123?';
grant all privileges on ThriveX.* to 'thrive'@'%';
flush privileges;
```
* `thrive`: 用户名
* `ThriveX@123?`: 密码

> 建议自定义用户名密码

## 配置数据库-使用容器启动新的数据库服务

```shell
docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=ThriveX@123? -e MYSQL_DATABASE=ThriveX -e MYSQL_USER=thrive -e MYSQL_PASSWORD=ThriveX@123? registry.cn-hangzhou.aliyuncs.com/liuyi778/mysql:8.0
```


## 启动服务

```shell
docker run -tid --net=host --name server -e PORT=9003 -e DB_USERNAME=thrive -e DB_HOST=172.178.178.10 -e DB_PASSWORD=ThriveX@123? -e DB_NAME=ThriveX -e DB_PORT=3306 registry.cn-hangzhou.aliyuncs.com/thrive/server
```

* `DB_USERNAME`: 数据库用户名
* `DB_HOST`: 数据库地址,如果是容器启动的,建议做端口映射后使用宿主机IP地址(如果Server容器非`host`模式不要使用: `localhost`、`127.0.0.1`)
* `DB_PASSWORD`: 数据库密码
* `DB_NAME`: 数据库名
* `DB_PORT`: 数据库端口
* `PORT`: 服务端口,默认为`9003`
* `--net=host`: 使用宿主机网络，当使用此参数之后,数据库地址可以使用`localhost`或者`127.0.0.1`

## 查看日志

```shell
docker logs -f server
```
