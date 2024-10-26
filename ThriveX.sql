-- MySQL dump 10.13  Distrib 8.0.39, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: test_thrive
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

create database ThriveX;
use ThriveX;

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `description` varchar(200) DEFAULT NULL COMMENT '文章介绍',
  `content` text NOT NULL COMMENT '文章主要内容',
  `cover` varchar(300) DEFAULT NULL COMMENT '文章封面',
  `view` int DEFAULT '0' COMMENT '文章浏览量',
  `comment` int DEFAULT '0' COMMENT '评论数量',
  `tag_ids` varchar(100) DEFAULT NULL COMMENT '该文章所绑定的标签ID',
  `create_time` varchar(255) NOT NULL COMMENT '文章创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2188 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (2187,'Hello World','当你看到这篇文章时就证明安装完成，一切就绪！','当你看到这篇文章时就证明安装完成，一切就绪！\n',NULL,2,0,'3','1729224230508');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_cate`
--

DROP TABLE IF EXISTS `article_cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_cate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL COMMENT '文章ID',
  `cate_id` int NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_cate_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1433 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章和分类的中间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_cate`
--

LOCK TABLES `article_cate` WRITE;
/*!40000 ALTER TABLE `article_cate` DISABLE KEYS */;
INSERT INTO `article_cate` VALUES (1432,2187,1);
/*!40000 ALTER TABLE `article_cate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_config`
--

DROP TABLE IF EXISTS `article_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `top` tinyint(1) DEFAULT '0' COMMENT '文章是否置顶',
  `status` enum('show','no_home','hide') DEFAULT 'show' COMMENT '文章状态',
  `password` varchar(100) DEFAULT '' COMMENT '是否文章加密',
  `article_id` int NOT NULL COMMENT '对应的文章id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_config_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_config`
--

LOCK TABLES `article_config` WRITE;
/*!40000 ALTER TABLE `article_config` DISABLE KEYS */;
INSERT INTO `article_config` VALUES (1,0,'show','',2187);
/*!40000 ALTER TABLE `article_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cate`
--

DROP TABLE IF EXISTS `cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '分类名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '分类图标',
  `url` varchar(255) DEFAULT '/' COMMENT '分类链接',
  `mark` varchar(100) NOT NULL COMMENT '分类标识',
  `level` int DEFAULT NULL COMMENT '分类级别',
  `order` int NOT NULL COMMENT '分类顺序',
  `type` varchar(10) DEFAULT 'cate' COMMENT '导航还是分类',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `cate_pk` (`mark`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cate`
--

LOCK TABLES `cate` WRITE;
/*!40000 ALTER TABLE `cate` DISABLE KEYS */;
INSERT INTO `cate` VALUES (1,'测试分类','💻','/','kfbj',0,1,'cate'),(50,'首页','💎','/','home',0,0,'nav'),(51,'足迹','⛳️','/footprint','zj',0,9,'nav'),(52,'关于我','👋','/my','my',0,12,'nav'),(53,'友人','😇','/friend','yr',0,10,'nav'),(54,'留言墙','💌','/wall/all','wall',0,11,'nav'),(55,'GitHub','🔥','https://github.com/LiuYuYang01/ThriveX-Blog','github',0,999,'nav'),(59,'统计','📊','/data','data',0,8,'nav');
/*!40000 ALTER TABLE `cate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '评论者名称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '评论者头像',
  `content` text NOT NULL COMMENT '评论内容',
  `email` varchar(100) DEFAULT NULL COMMENT '评论者邮箱',
  `url` varchar(500) DEFAULT NULL COMMENT '评论者网站',
  `article_id` int NOT NULL COMMENT '所属文章ID',
  `comment_id` int DEFAULT '0' COMMENT '二级评论',
  `audit_status` int DEFAULT '0' COMMENT '是否审核',
  `create_time` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=515 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (514,'宇阳','https://q1.qlogo.cn/g?b=qq&nk=3311118881&s=640','记得点个star','3311118881@qq.com','https://liuyuyang.net/',2187,0,0,'1729225111457');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `footprint`
--

DROP TABLE IF EXISTS `footprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `footprint` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` varchar(1500) DEFAULT NULL COMMENT '内容',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `position` varchar(255) NOT NULL COMMENT '坐标纬度',
  `images` json DEFAULT NULL COMMENT '图片',
  `create_time` varchar(255) NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `footprint_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `footprint`
--

LOCK TABLES `footprint` WRITE;
/*!40000 ALTER TABLE `footprint` DISABLE KEYS */;
INSERT INTO `footprint` VALUES (33,'测试足迹','测试足迹','测试足迹','119.138475,33.6119','[]','1599667200000');
/*!40000 ALTER TABLE `footprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '网站标题',
  `description` varchar(255) NOT NULL COMMENT '网站描述',
  `email` varchar(100) DEFAULT NULL COMMENT '网站邮箱',
  `image` varchar(255) NOT NULL COMMENT '网站logo',
  `url` varchar(500) DEFAULT NULL COMMENT '网站链接',
  `rss` varchar(500) DEFAULT NULL,
  `type_id` int NOT NULL COMMENT '网站所绑定的类型',
  `audit_status` int NOT NULL DEFAULT '0' COMMENT '是否审核',
  `create_time` varchar(255) NOT NULL COMMENT '网站创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link`
--

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
INSERT INTO `link` VALUES (50,'这是一个网站','这是一个网站的描述','liuyuyang1024@yeah.net','http://127.0.0.1:5000/1.jpg','/','/',1,1,'1723533206613');
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link_type`
--

DROP TABLE IF EXISTS `link_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `link_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '类型名称',
  `is_admin` int NOT NULL DEFAULT '0' COMMENT '用户是否可选择',
  `order` int NOT NULL COMMENT '显示顺序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='网站类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link_type`
--

LOCK TABLES `link_type` WRITE;
/*!40000 ALTER TABLE `link_type` DISABLE KEYS */;
INSERT INTO `link_type` VALUES (1,'生活类',0,4),(2,'技术类',0,5),(3,'全站置顶',1,1),(4,'推荐',1,2),(5,'大佬',1,3),(6,'聚合类',0,6);
/*!40000 ALTER TABLE `link_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_article_layout` varchar(255) DEFAULT NULL COMMENT '文章列表布局',
  `right_sidebar` text COMMENT '右侧功能栏',
  `swiper_image` varchar(255) DEFAULT NULL COMMENT '首页背景图',
  `swiper_text` text COMMENT '打字机效果',
  `covers` text,
  `dark_logo` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `favicon` varchar(255) DEFAULT NULL,
  `font` varchar(255) DEFAULT NULL,
  `footer` text,
  `keyword` varchar(255) NOT NULL,
  `light_logo` varchar(255) DEFAULT NULL,
  `subhead` varchar(255) DEFAULT NULL,
  `social` text,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'classics','[\"author\", \"hotArticle\", \"newComments\"]','https://bu.dusays.com/2024/04/24/6628990012b51.jpg','[\"这是第一行1\", \"这是第二行2\", \"这是第三行3\"]','[]','https://res.liuyuyang.net/usr/images/dark_logo.png','记录所学知识，缩短和大神的差距！','https://res.liuyuyang.net/usr/images/favicon.ico','https://res.liuyuyang.net/LXGWWenKai.ttf','一直对网站开发领域很感兴趣，从小就希望有一个属于自己的网站，在17年时候成功进入站长圈，并通过各种自学，以及各种折腾，才有了你现在看到的这个网站','宇阳,Thrive,前端,Python,Java','https://res.liuyuyang.net/usr/images/light_logo.png','花有重开日, 人无再少年','[{\"name\":\"GitHub\",\"url\":\"https://github.com/LiuYuYang01\"},{\"name\":\"Gitee\",\"url\":\"https://gitee.com/liu_yu_yang666\"},{\"name\":\"Juejin\",\"url\":\"https://juejin.cn/user/3083456627092078/posts\"},{\"name\":\"CSDN\",\"url\":\"https://blog.csdn.net/haodian666?type=blog\"},{\"name\":\"QQ\",\"url\":\"http://wpa.qq.com/msgrd?v=3&uin=3311118881&site=qq&menu=yes\"}]','宇阳','https://liuyuyang.net/');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `mark` varchar(100) NOT NULL COMMENT '角色标识',
  `description` varchar(255) NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_pk` (`name`),
  UNIQUE KEY `role_pk_3` (`id`),
  UNIQUE KEY `role_pk_2` (`mark`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'超级管理员','admin','最高权限'),(2,'用户','user','普通用户'),(5,'作者','author','发布文章、查看文章列表');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(100) NOT NULL COMMENT '路由路径',
  `description` varchar(255) NOT NULL COMMENT '路由描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `routes_pk_2` (`id`),
  UNIQUE KEY `routes_pk` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,'/','仪表盘'),(2,'/create','创作'),(5,'/setup','系统'),(6,'/article','文章管理'),(7,'/tag','标签管理'),(8,'/comment','评论管理'),(9,'/cate','分类管理'),(10,'/web','网站管理'),(11,'/swiper','轮播图管理'),(12,'/user','用户管理'),(13,'/role','角色管理'),(14,'/rss','订阅中心'),(15,'/chart','文件系统'),(16,'/stats','数据可视化'),(17,'/iter','更新日志'),(20,'/route','路由管理'),(21,'/file','文件管理'),(23,'/footprint','足迹管理'),(24,'/work','工作台'),(25,'/wall','留言管理');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_role`
--

DROP TABLE IF EXISTS `route_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL COMMENT '路由id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `routes_role_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_role`
--

LOCK TABLES `route_role` WRITE;
/*!40000 ALTER TABLE `route_role` DISABLE KEYS */;
INSERT INTO `route_role` VALUES (54,1,5),(55,6,5),(56,2,5),(57,7,2),(58,9,2),(59,8,2),(60,6,2),(61,2,2),(62,5,2),(63,4,2),(209,25,1),(210,24,1),(211,23,1),(212,21,1),(213,14,1),(214,17,1),(215,20,1),(216,1,1),(217,2,1),(218,5,1),(219,6,1),(220,7,1),(221,8,1),(222,9,1),(223,10,1),(224,11,1),(225,12,1),(226,13,1),(227,15,1),(228,16,1);
/*!40000 ALTER TABLE `route_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swiper`
--

DROP TABLE IF EXISTS `swiper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swiper` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swiper`
--

LOCK TABLES `swiper` WRITE;
/*!40000 ALTER TABLE `swiper` DISABLE KEYS */;
INSERT INTO `swiper` VALUES (1,'半山腰的风景很美，然而我还是更想到山顶看看','The scenery halfway up the mountain is beautiful, but I still prefer to see the mountaintop','https://bu.dusays.com/2023/11/10/654e2cf6055b0.jpg','/');
/*!40000 ALTER TABLE `swiper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (3,'测试标签');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '用户名称',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `info` varchar(255) DEFAULT NULL COMMENT '用户介绍',
  `role_id` varchar(50) NOT NULL COMMENT '用户角色ID',
  `create_time` varchar(255) NOT NULL COMMENT '用户创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_pk` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','E10ADC3949BA59ABBE56E057F20F883E','宇阳','3311118881@qq.com','https://res.liuyuyang.net/usr/images/avatar.jpg','再渺小的星光，也有属于他的光芒!','1','1723533206613');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wall`
--

DROP TABLE IF EXISTS `wall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wall` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT '神秘人' COMMENT '留言人名称',
  `content` varchar(255) NOT NULL COMMENT '留言内容',
  `color` varchar(100) DEFAULT '#ffe3944d' COMMENT '留言墙颜色',
  `email` varchar(100) DEFAULT NULL COMMENT '留言者邮箱',
  `cate_id` int NOT NULL,
  `audit_status` int DEFAULT '0' COMMENT '是否审核',
  `create_time` varchar(255) NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wall_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='留言墙';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wall`
--

LOCK TABLES `wall` WRITE;
/*!40000 ALTER TABLE `wall` DISABLE KEYS */;
INSERT INTO `wall` VALUES (104,'测试','测试测试测试测试测试','#fcafa24d','3311118881@qq.com',6,1,'1729231268305'),(107,'测试','测试测试测试测试测试','#fcafa24d','3311118881@qq.com',6,0,'1729231268305'),(108,'测试','测试测试测试测试测试','#fcafa24d','3311118881@qq.com',6,0,'1729231268305');
/*!40000 ALTER TABLE `wall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wall_cate`
--

DROP TABLE IF EXISTS `wall_cate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wall_cate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `mark` varchar(100) NOT NULL COMMENT '分类标识',
  `order` int NOT NULL COMMENT '分类顺序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wall_cate_pk_2` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='留言分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wall_cate`
--

LOCK TABLES `wall_cate` WRITE;
/*!40000 ALTER TABLE `wall_cate` DISABLE KEYS */;
INSERT INTO `wall_cate` VALUES (1,'全部','all',1),(2,'想对我说的话','info',2),(3,'对我的建议','suggest',3),(6,'其他','other',6);
/*!40000 ALTER TABLE `wall_cate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-26 20:52:11
