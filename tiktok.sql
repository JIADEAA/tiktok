-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: tiktok-dev
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `comment`
--
DROP DATABASE IF EXISTS `tiktok`;
CREATE DATABASE tiktok;
USE tiktok;
DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` varchar(24) NOT NULL,
  `vloger_id` varchar(24) NOT NULL COMMENT '评论的视频是哪个作者（vloger）的关联id',
  `father_comment_id` varchar(24) NOT NULL COMMENT '如果是回复留言，则本条为子留言，需要关联查询',
  `vlog_id` varchar(24) NOT NULL COMMENT '回复的那个视频id',
  `comment_user_id` varchar(24) NOT NULL COMMENT '发布留言的用户id',
  `content` varchar(128) NOT NULL COMMENT '留言内容',
  `like_counts` int NOT NULL COMMENT '留言的点赞总数',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--



--
-- Table structure for table `fans`
--

DROP TABLE IF EXISTS `fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fans` (
  `id` varchar(24) NOT NULL,
  `vloger_id` varchar(24) NOT NULL COMMENT '作家用户id',
  `fan_id` varchar(24) NOT NULL COMMENT '粉丝用户id',
  `is_fan_friend_of_mine` int NOT NULL COMMENT '粉丝是否是vloger的朋友，如果成为朋友，则本表的双方此字段都需要设置为1，如果有一人取关，则两边都需要设置为0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `writer_id` (`vloger_id`,`fan_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='粉丝表\n\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fans`
--



--
-- Table structure for table `my_liked_vlog`
--

DROP TABLE IF EXISTS `my_liked_vlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_liked_vlog` (
  `id` varchar(24) NOT NULL,
  `user_id` varchar(24) NOT NULL COMMENT '用户id',
  `vlog_id` varchar(24) NOT NULL COMMENT '喜欢的短视频id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `writer_id` (`user_id`,`vlog_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞短视频关联表\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_liked_vlog`
--



--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(24) NOT NULL,
  `mobile` varchar(32) NOT NULL COMMENT '手机号',
  `nickname` varchar(16) NOT NULL COMMENT '昵称，媒体号',
  `imooc_num` varchar(16) NOT NULL COMMENT '抖音号，公众号，唯一标识，需要限制修改次数，比如终生1次，每年1次，每半年1次等，可以用于付费修改。',
  `face` varchar(128) NOT NULL COMMENT '头像',
  `sex` int NOT NULL COMMENT '性别 1:男  0:女  2:保密',
  `birthday` date NOT NULL COMMENT '生日',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `district` varchar(32) DEFAULT NULL COMMENT '区县',
  `description` varchar(100) NOT NULL COMMENT '简介',
  `bg_img` varchar(255) DEFAULT NULL COMMENT '个人介绍的背景图',
  `can_imooc_num_be_updated` int NOT NULL COMMENT '抖音号能否被修改，1：默认，可以修改；0，无法修改',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--



--
-- Table structure for table `vlog`
--

DROP TABLE IF EXISTS `vlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vlog` (
  `id` varchar(24) NOT NULL,
  `vloger_id` varchar(24) NOT NULL COMMENT '对应用户表id，vlog视频发布者',
  `url` varchar(255) NOT NULL COMMENT '视频播放地址',
  `cover` varchar(255) NOT NULL COMMENT '视频封面',
  `title` varchar(128) DEFAULT NULL COMMENT '视频标题，可以为空',
  `width` int NOT NULL COMMENT '视频width',
  `height` int NOT NULL COMMENT '视频height',
  `like_counts` int NOT NULL COMMENT '点赞总数',
  `comments_counts` int NOT NULL COMMENT '评论总数',
  `is_private` int NOT NULL COMMENT '是否私密，用户可以设置私密，如此可以不公开给比人看',
  `created_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间 更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='短视频表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vlog`
--


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-02  1:37:06
