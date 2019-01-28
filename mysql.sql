
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_news_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_news_image` (
	`newsimage` varchar(100) not null COMMENT '本地图片名称',
	`url` varchar(255) COMMENT 'url'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图文内部图形表表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `sa_dt_iv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_iv` (
	`iv` varchar(100) not null COMMENT 'iv名称',
	`media_id` varchar(100) COMMENT '公众号ID',
	`url` varchar(255) COMMENT 'url',
	`type` varchar(30) not null COMMENT '类型image',
	`addtime` timestamp default CURRENT_TIMESTAMP COMMENT '添加时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图片&缩略图表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `sa_dt_voice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_voice` (
	`voice` varchar(100) not null COMMENT 'iv名称',
	`media_id` varchar(100) COMMENT '公众号ID',
	`addtime` timestamp default CURRENT_TIMESTAMP COMMENT '添加时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='语音表';
/*!40101 SET character_set_client = @saved_cs_client */;
DROP TABLE IF EXISTS `sa_dt_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_video` (
	`video` varchar(100) not null COMMENT '视频名称',
	`media_id` varchar(100) COMMENT '公众号ID',
	`title` varchar(100) not null COMMENT '视频标题',
	`introduction` varchar(255) not null COMMENT '视频介绍200字',
	`addtime` timestamp default CURRENT_TIMESTAMP COMMENT '添加时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_news` (
	`id` int(4) primary key not null auto_increment COMMENT '主键',
	`media_id` varchar(100) COMMENT '公众号ID',
	`title` varchar(100) not null COMMENT '图文标题',
	`thumb_media_id` varchar(100) COMMENT '图文消息的封面图片素材id',
	`thumb_id` varchar(100) COMMENT '图文消息的封面本地图片名称',
	`author` varchar(50) not null COMMENT '图文作者',
	`digest` varchar(150) not null COMMENT '图文消息的摘要，不填写会自动抓取',
	`show_cover_pic` TINYINT(1) not null COMMENT '是否显示封面，1显示 0不显示',
	`content` text not null COMMENT '文章内容，支持HTML',
	`content_source_url` varchar(300) not null COMMENT '图文消息的原文地址',--(弃用！直接用服务器地址+各种路径补足，详情 CommandCollection.GetMateNews)
	`need_open_comment` TINYINT(1) not null COMMENT '是否打开评论 1打开 0关闭',
	`only_fans_can_comment` TINYINT(1) not null COMMENT '是否限制评论 1限制 0不限制',

	`type` int COMMENT '类别id',
	`consume` int COMMENT '所需代金券',
	`name_id` int COMMENT '书名ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图文表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_news_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_news_name` (
	`id` int(4) primary key not null auto_increment COMMENT '书名ID',
	`name` varchar(100) COMMENT '书名',
	`digest` varchar(150) not null COMMENT '简介',
	`author` varchar(50) not null COMMENT '作者',
	`type_id` int(4) COMMENT '类型id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='书名表';
insert into sa_dt_news_name(name, digest, author) values('none', '不是图书', 'sa')-- 占用

DROP TABLE IF EXISTS `sa_dt_news_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_news_type` (
	`id` int(4) primary key not null auto_increment COMMENT '类型ID',
	`name` varchar(100) COMMENT '类型名称',
	`protect` TINYINT(1) default 0 COMMENT '是否保护，不可删除 1是 0否',
	`level` int default 0 COMMENT '拉取权限，0为最低都可拉取，3为最高都不可拉取'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图文类型表';
INSERT INTO `sa_dt_news_type` (`name`, `protect`, `level`) VALUES
('全部', 1, 0),('图书', 1, 2),('攻略', 1, 0),('社会', 1, 0),('美食', 1, 0),('养生', 1, 0),('休闲', 1, 0),('攻略(散)', 1, 0);

-- 以下为网站表
DROP TABLE IF EXISTS `sa_dt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_user` (
	`openid` varchar(32) not null COMMENT 'openid',
	`nickname` varchar(32) not null COMMENT '昵称',
	`sex` TINYINT(1) COMMENT '0女1男2保密',
	`vouchers` int not null COMMENT '代金券',
	`autopay` TINYINT(1) COMMENT '0手动1自动 支付',
	`language` varchar(20) COMMENT '语言',
	`country` varchar(32) not null COMMENT '国家',
	`province` varchar(32) not null COMMENT '省',
	`city` varchar(32) not null COMMENT '市',
	`headimgurl` varchar(300) not null COMMENT '头像地址',
	`privilege` varchar(300) default "" COMMENT '特权(用,隔开)',
	`birthday` varchar(10) default "" COMMENT '生日',
	`jointime` timestamp default CURRENT_TIMESTAMP COMMENT '加入时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_consume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_consume` (
	`openid` varchar(32) not null COMMENT 'openid',
	`vouchers` int(4) not null COMMENT '充值数量',
	`out_trade_no` varchar(32) COMMENT '订单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_bookcase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_bookcase` (
	`openid` varchar(32) not null COMMENT 'openid',
	`read` TINYINT(1) default 0 COMMENT '1为正在阅读项',
	`name_id` int COMMENT '书名ID',
	`news_id` int(4) not null COMMENT '图文id(已买到的)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户书架';
/*!40101 SET character_set_client = @saved_cs_client */;

