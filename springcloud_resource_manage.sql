/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : springcloud_resource_manage

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 07/06/2023 16:41:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imgUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片地址',
  `imgSize` int(11) NOT NULL COMMENT '图片大小',
  `imgType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '图片类型',
  `isDel` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(0-未删除，1-删除)',
  `createTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (1, 'http://localhost:7001/images/1686122330251.jpg', 192078, '\"tt\"', 1, '2023-06-07 16:30:41');
INSERT INTO `image` VALUES (2, 'http://localhost:7001/images/1686122354050.jpg', 148772, '\"tt\"', 1, '2023-06-07 16:30:41');
INSERT INTO `image` VALUES (3, 'http://localhost:7001/images/1686122434108.jpg', 148772, ' ', 1, '2023-06-07 15:20:41');
INSERT INTO `image` VALUES (4, 'http://localhost:7001/images/1686123138915.jpg', 192078, '\"tt\"', 1, '2023-06-07 16:30:47');
INSERT INTO `image` VALUES (5, 'http://localhost:7001/images/1686123141423.jpg', 148772, '\"tt\"', 1, '2023-06-07 16:30:41');
INSERT INTO `image` VALUES (6, 'http://localhost:7001/images/1686123609537.jpg', 148772, '\"tt\"', 1, '2023-06-07 16:30:47');
INSERT INTO `image` VALUES (7, 'http://localhost:7001/images/1686126589345.jpg', 148772, '\"tt\"', 1, '2023-06-07 16:30:47');
INSERT INTO `image` VALUES (8, 'http://localhost:7001/images/1686126595284.jpg', 325042, '\"tt\"', 1, '2023-06-07 16:30:41');
INSERT INTO `image` VALUES (9, 'http://localhost:7001/images/1686127206752.jpg', 637700, '\"qq\"', 0, '2023-06-07 16:40:06');

-- ----------------------------
-- Table structure for img_type
-- ----------------------------
DROP TABLE IF EXISTS `img_type`;
CREATE TABLE `img_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imgTypeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of img_type
-- ----------------------------
INSERT INTO `img_type` VALUES (2, '\"qq\"', 1);

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `noteTitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记内容',
  `noteType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '笔记类型',
  `isDel` int(1) NOT NULL COMMENT '逻辑删除(0-未删除，1-删除)',
  `createTime` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发表时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (1, '1', '12', ' ', 0, '2023-06-07 16:32:23');
INSERT INTO `note` VALUES (2, 'we', 'we', 'notes', 0, '2023-06-07 16:32:51');
INSERT INTO `note` VALUES (3, 'qq', 'qq', 'notes', 0, '2023-06-07 16:33:36');
INSERT INTO `note` VALUES (4, 'wf', 'wf', 'notes', 0, '2023-06-07 16:39:47');

-- ----------------------------
-- Table structure for note_type
-- ----------------------------
DROP TABLE IF EXISTS `note_type`;
CREATE TABLE `note_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `noteTypeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of note_type
-- ----------------------------
INSERT INTO `note_type` VALUES (1, 'notes', 1);

-- ----------------------------
-- Table structure for recycle_bin
-- ----------------------------
DROP TABLE IF EXISTS `recycle_bin`;
CREATE TABLE `recycle_bin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL DEFAULT 0,
  `imageId` int(11) NULL DEFAULT 0 COMMENT '垃圾回收(图片)id',
  `videoId` int(11) NULL DEFAULT 0 COMMENT '垃圾回收(视频)id',
  `noteId` int(11) NULL DEFAULT 0 COMMENT '垃圾回收(笔记)id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recycle_bin
-- ----------------------------
INSERT INTO `recycle_bin` VALUES (3, 1, 8, 0, 0);

-- ----------------------------
-- Table structure for u_image
-- ----------------------------
DROP TABLE IF EXISTS `u_image`;
CREATE TABLE `u_image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `imageId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_image
-- ----------------------------
INSERT INTO `u_image` VALUES (5, 1, 9);

-- ----------------------------
-- Table structure for u_note
-- ----------------------------
DROP TABLE IF EXISTS `u_note`;
CREATE TABLE `u_note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `noteId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_note
-- ----------------------------
INSERT INTO `u_note` VALUES (2, 1, 1);
INSERT INTO `u_note` VALUES (4, 1, 2);
INSERT INTO `u_note` VALUES (5, 1, 3);
INSERT INTO `u_note` VALUES (6, 1, 4);

-- ----------------------------
-- Table structure for u_video
-- ----------------------------
DROP TABLE IF EXISTS `u_video`;
CREATE TABLE `u_video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `videoId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_video
-- ----------------------------
INSERT INTO `u_video` VALUES (3, 1, 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'yjm', '654321', 'http://localhost:8004/avatar/1686126664957.jpg', '13566666666', '1@qq.com');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `videoUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '视频地址',
  `videoSize` double NOT NULL COMMENT '视频大小',
  `videoType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频类型',
  `isDel` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(0-未删除，1-删除)',
  `createTime` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES (1, 'http://localhost:7002/videos/1686124327832.mp4', 6649757, 'video', 0, '2023-06-07 16:31:49');
INSERT INTO `video` VALUES (2, 'http://localhost:7002/videos/1686124332139.mp4', 6649757, 'video', 0, '2023-06-07 16:31:49');
INSERT INTO `video` VALUES (3, 'http://localhost:7002/videos/1686126671306.mp4', 6649757, 'video', 1, '2023-06-07 16:31:53');
INSERT INTO `video` VALUES (4, 'http://localhost:7002/videos/1686127194450.mp4', 6649757, 'video', 0, '2023-06-07 16:39:54');

-- ----------------------------
-- Table structure for video_type
-- ----------------------------
DROP TABLE IF EXISTS `video_type`;
CREATE TABLE `video_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `videoTypeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_type
-- ----------------------------
INSERT INTO `video_type` VALUES (1, 'video', 1);

SET FOREIGN_KEY_CHECKS = 1;
