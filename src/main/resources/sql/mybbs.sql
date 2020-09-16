/*
 Navicat Premium Data Transfer

 Source Server         : aliyun_lin__docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 39.105.17.34:8306
 Source Schema         : mybbs

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 16/09/2020 12:20:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `in_time` datetime DEFAULT NULL,
  `role_level` int(11) unsigned NOT NULL DEFAULT '1',
  `telephone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `bid` varchar(200) NOT NULL COMMENT '博客id',
  `title` varchar(200) NOT NULL COMMENT '博客标题',
  `content` longtext NOT NULL COMMENT '博客内容',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序 0 普通  1 置顶',
  `views` int(10) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `likes` int(10) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `author_id` int(20) NOT NULL COMMENT '作者id',
  `author_name` varchar(200) NOT NULL COMMENT '作者名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_update` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `comment_id` varchar(200) NOT NULL COMMENT '评论唯一id',
  `topic_category` int(1) NOT NULL COMMENT '1博客 2问答',
  `topic_id` varchar(200) NOT NULL COMMENT '评论主题id',
  `user_id` int(20) DEFAULT NULL COMMENT '评论者id',
  `user_name` varchar(200) NOT NULL COMMENT '评论者昵称',
  `content` longtext NOT NULL COMMENT '评论内容',
  `gmt_create` datetime NOT NULL COMMENT '评论创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `qid` varchar(200) NOT NULL COMMENT '问题id',
  `title` varchar(200) NOT NULL COMMENT '问题标题',
  `content` longtext NOT NULL COMMENT '问题内容',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0 未解决 1 已解决',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序 0 普通  1 置顶',
  `views` int(10) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `author_id` int(20) NOT NULL COMMENT '作者id',
  `author_name` varchar(200) NOT NULL COMMENT '作者名',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_update` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
