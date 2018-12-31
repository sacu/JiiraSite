
CREATE DATABASE IF NOT EXISTS `sa_jiira_db`;
USE `sa_jiira_db`;
set names 'utf8';

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- dt 普通可变数据表
-- st 静态数据表

-- 以下为后台管理
DROP TABLE IF EXISTS `sa_dt_aduser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_aduser` (
	`id` int(4) primary key not null auto_increment COMMENT '主键',
	`username` varchar(32) not null COMMENT '帐号',
	`password` varchar(32) not null COMMENT '密码',
	`nickname` varchar(32) not null COMMENT '昵称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

-- 以下为网站表
DROP TABLE IF EXISTS `sa_dt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_user` (
	`id` int(4) primary key not null auto_increment COMMENT '主键',
	`username` varchar(32) not null COMMENT '帐号',
	`password` varchar(32) not null COMMENT '密码',
	`nickname` varchar(32) not null COMMENT '昵称',
	`sex` TINYINT(1) COMMENT '0女1男2保密',
	`jointime` timestamp default CURRENT_TIMESTAMP COMMENT '加入时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_asset` (
	`id` int(4) primary key not null auto_increment COMMENT '主键',
	`userid` int(4) not null COMMENT '用户id',
	`points` int(4) not null COMMENT '积分',
	`money` int(4) not null COMMENT '人民币'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_consume` (
	`consumeid` int(4) primary key not null auto_increment COMMENT '主键',
	`userid` int(4) not null COMMENT '用户id',
	`points` int(4) not null COMMENT '消费的积分',
	`money` int(4) not null COMMENT '消费的人民币',
	`time` timestamp default CURRENT_TIMESTAMP COMMENT '消费时间(负数为花费)',
	`describe` varchar(50) COMMENT '描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消费记录表';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `sa_st_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_st_book` (
	`bookid` int(4) primary key not null auto_increment COMMENT '主键',
	`type` TINYINT(1) not null COMMENT '0书1漫画',
	`bookname` varchar(50) not null COMMENT '书名',
	`about` varchar(256) not null COMMENT '简介',
	`money` int(4) not null default 0 COMMENT '需要人民币',
	`points` int(4) not null default 0 COMMENT '需要积分',
	`freechapter` int(4) COMMENT '免费章节'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商城图书列表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_bookcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_bookcase` (
	`id` int(4) primary key not null auto_increment COMMENT '主键',
	`userid` int(4) not null COMMENT '用户id',
	`bookid` int(4) not null COMMENT '图书id',
	`pay` TINYINT(1) not null COMMENT '0未付费·付费',
	`redchapter` int(4) not null default 0 COMMENT '读取到章节',
	`redchapterpage` int(4) not null default 0 COMMENT '读取到章节的页数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户书架';
/*!40101 SET character_set_client = @saved_cs_client */;

