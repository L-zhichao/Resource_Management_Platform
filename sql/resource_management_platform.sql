/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : resource_management_platform

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 31/07/2023 10:57:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item_apply
-- ----------------------------
DROP TABLE IF EXISTS `item_apply`;
CREATE TABLE `item_apply`  (
  `apply_id` bigint NOT NULL COMMENT '申请id 主键',
  `apply_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人',
  `apply_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请内容',
  `apply_money` double NOT NULL COMMENT '申请金额',
  `apply_status` int NOT NULL DEFAULT -1 COMMENT '申请状态',
  `apply_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_apply
-- ----------------------------
INSERT INTO `item_apply` VALUES (1685278230009585665, 'admain', '测试申请', 52.6, -1, '2023-07-29 21:16:56');

-- ----------------------------
-- Table structure for item_response
-- ----------------------------
DROP TABLE IF EXISTS `item_response`;
CREATE TABLE `item_response`  (
  `response_id` bigint NOT NULL COMMENT 'id主键',
  `apply_id` bigint NULL DEFAULT NULL COMMENT '申请id',
  `apply_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人',
  `response_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回应人',
  `response_status` int NULL DEFAULT -1 COMMENT '回应状态',
  `response_result` int NULL DEFAULT NULL COMMENT '回应结果（同意与否）',
  `result_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果原因',
  `response_time` datetime NULL DEFAULT NULL COMMENT '回应时间',
  PRIMARY KEY (`response_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_response
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint NOT NULL COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码（加密后）',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `user_status` int NOT NULL DEFAULT 1 COMMENT '用户状态（管理员还是普通用户）',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱地址',
  `registration_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE COMMENT '设置字段唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1685270500158922754, 'admain', '93E9A27A5F2C8947D7BE8A407D8DA8B4', 'F843C650-9577-44FB-A12C-A7F9E4EC31F8', '管理员', 0, 'admain@email.com', '2023-07-29 09:49:28');

SET FOREIGN_KEY_CHECKS = 1;

-- 物品
-- auto-generated definition
create table item
(
    item_id  bigint auto_increment comment '物品ID'
        primary key,
    itemname varchar(10)  null comment '物品名称',
    number   int          null comment '数量',
    price    double       null comment '价格',
    imgs     varchar(500) null comment '图片',
    video    varchar(500) null comment '视屏'
)
    comment '物品';



-- 物品损坏记录
-- auto-generated definition
create table damage_record
(
    damage_record_id       bigint auto_increment
        primary key,
    damage_record_desc     varchar(200)         null comment '损坏描述',
    damage_record_time     datetime             null comment '物品损坏时间',
    itemname               varchar(20)          null comment '物品名称',
    username               varchar(20)          null comment '用户姓名',
    damage_record_isread   tinyint(1) default 0 null comment '管理员是否已读',
    damage_record_img      varchar(200)         null comment '物品损坏图片',
    damage_record_ishandle tinyint(1) default 0 null comment '是否处理'
)
    comment '物品损坏记录';

CREATE TABLE `fundsmgm`
(
    `id`          bigint(30) NOT NULL COMMENT '主键ID',
    `asset`       varchar(20) default NULL COMMENT '资产名称',
    `asset_value` varchar(20) default NULL COMMENT '资产价值',
    `judge`       int(5)      default null comment '资产是否可支配（1/0）',#可支配为1，不可支配为0
        `update_time` datetime    default null comment '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

