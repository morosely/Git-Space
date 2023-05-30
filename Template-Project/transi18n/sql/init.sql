DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  `schoolId` int(11) NULL DEFAULT NULL,
  `studentType` varchar(332) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)

INSERT INTO `student` VALUES ('1', '小明', 1, 1, 'ARTS');
INSERT INTO `student` VALUES ('2', '小黄', 0, 2, 'SCIENCES');

-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` int(11) NOT NULL,
  `school_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)

INSERT INTO `school` VALUES (1, '第一中学', '111', '610100');
INSERT INTO `school` VALUES (2, '高新二小', '222', '610100');
INSERT INTO `school` VALUES (3, '铁一中', '333', '610100');
