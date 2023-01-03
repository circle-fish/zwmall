
-- ----------------------------
-- Table structure for goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `goods_sku`;
CREATE TABLE `goods_sku`  (
                              `id` int NOT NULL AUTO_INCREMENT COMMENT '商品规格id',
                              `goodsId` int NOT NULL COMMENT '商品id',
                              `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格名称',
                              `goodsAttr` json NOT NULL COMMENT '商品属性json对象',
                              `price` decimal(10, 2) NOT NULL COMMENT '价格',
                              `originalPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '原价',
                              `costPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '进价',
                              `stock` int NOT NULL DEFAULT 0 COMMENT '库存',
                              `bannersId` int NOT NULL COMMENT '轮播图集合id',
                              `sortNo` int NOT NULL DEFAULT 1 COMMENT '规格排序号',
                              `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                              `updateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 522 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------
INSERT INTO `goods_sku` VALUES (457, 90, '6GB+64GB 桃子西柚', '[{\"key\": \"版本\", \"value\": \"6GB+64GB\"}, {\"key\": \"颜色\", \"value\": \"桃子西柚\"}]', 1.12, NULL, NULL, 78, 163, 0, '2020-05-17 16:57:26', '2020-06-30 10:45:00');
INSERT INTO `goods_sku` VALUES (458, 90, '6GB+64GB 四季春奶绿', '[{\"key\": \"版本\", \"value\": \"6GB+64GB\"}, {\"key\": \"颜色\", \"value\": \"四季春奶绿\"}]', 2099.00, NULL, NULL, 104, 162, 1, '2020-05-17 16:57:27', '2020-05-17 21:23:04');
INSERT INTO `goods_sku` VALUES (459, 90, '6GB+64GB 黑巧风暴', '[{\"key\": \"版本\", \"value\": \"6GB+64GB\"}, {\"key\": \"颜色\", \"value\": \"黑巧风暴\"}]', 2099.00, NULL, NULL, 66, 161, 2, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (460, 90, '6GB+64GB 白桃乌龙', '[{\"key\": \"版本\", \"value\": \"6GB+64GB\"}, {\"key\": \"颜色\", \"value\": \"白桃乌龙\"}]', 2099.00, NULL, NULL, 40, 160, 3, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (461, 90, '6GB+64GB 蓝莓薄荷', '[{\"key\": \"版本\", \"value\": \"6GB+64GB\"}, {\"key\": \"颜色\", \"value\": \"蓝莓薄荷\"}]', 2099.00, NULL, NULL, 80, 159, 4, '2020-05-17 16:57:27', '2020-05-17 22:20:00');
INSERT INTO `goods_sku` VALUES (462, 90, '6GB+128GB 桃子西柚', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"桃子西柚\"}]', 2299.00, NULL, NULL, 69, 163, 5, '2020-05-17 16:57:27', '2020-05-18 18:16:04');
INSERT INTO `goods_sku` VALUES (463, 90, '6GB+128GB 四季春奶绿', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"四季春奶绿\"}]', 2299.00, NULL, NULL, 87, 162, 6, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (464, 90, '6GB+128GB 黑巧风暴', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"黑巧风暴\"}]', 2299.00, NULL, NULL, 77, 161, 7, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (465, 90, '6GB+128GB 白桃乌龙', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"白桃乌龙\"}]', 2299.00, NULL, NULL, 88, 160, 8, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (466, 90, '6GB+128GB 蓝莓薄荷', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"蓝莓薄荷\"}]', 2299.00, NULL, NULL, 99, 159, 9, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (467, 90, '8GB+128GB 桃子西柚', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"桃子西柚\"}]', 2499.00, NULL, NULL, 208, 163, 10, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (468, 90, '8GB+128GB 四季春奶绿', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"四季春奶绿\"}]', 2499.00, NULL, NULL, 300, 162, 11, '2020-05-17 16:57:27', '2020-06-02 23:25:00');
INSERT INTO `goods_sku` VALUES (469, 90, '8GB+128GB 黑巧风暴', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"黑巧风暴\"}]', 2499.00, NULL, NULL, 99, 161, 12, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (470, 90, '8GB+128GB 白桃乌龙', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"白桃乌龙\"}]', 2499.00, NULL, NULL, 101, 160, 13, '2020-05-17 16:57:27', '2020-06-13 18:39:09');
INSERT INTO `goods_sku` VALUES (471, 90, '8GB+128GB 蓝莓薄荷', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"蓝莓薄荷\"}]', 2499.00, NULL, NULL, 98, 159, 14, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (472, 90, '8GB+256GB 桃子西柚', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"桃子西柚\"}]', 2799.00, NULL, NULL, 49, 163, 15, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (473, 90, '8GB+256GB 四季春奶绿', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"四季春奶绿\"}]', 2799.00, NULL, NULL, 28, 162, 16, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (474, 90, '8GB+256GB 黑巧风暴', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"黑巧风暴\"}]', 2799.00, NULL, NULL, 53, 161, 17, '2020-05-17 16:57:27', '2020-06-01 08:03:06');
INSERT INTO `goods_sku` VALUES (475, 90, '8GB+256GB 白桃乌龙', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"白桃乌龙\"}]', 2799.00, NULL, NULL, 68, 160, 18, '2020-05-17 16:57:27', '2020-05-18 16:08:31');
INSERT INTO `goods_sku` VALUES (476, 90, '8GB+256GB 蓝莓薄荷', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"蓝莓薄荷\"}]', 2799.00, NULL, NULL, 12, 159, 19, '2020-05-17 16:57:27', '2020-05-18 17:45:00');
INSERT INTO `goods_sku` VALUES (477, 91, '8GB+256GB 星空蓝', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"星空蓝\"}]', 4999.00, NULL, NULL, 102, 119, 0, '2020-05-17 17:15:25', '2020-06-30 10:45:00');
INSERT INTO `goods_sku` VALUES (478, 91, '8GB+256GB 珍珠白', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"珍珠白\"}]', 4999.00, NULL, NULL, 70, 118, 1, '2020-05-17 17:15:25', '2020-05-18 17:25:00');
INSERT INTO `goods_sku` VALUES (479, 91, '12GB+256GB 星空蓝', '[{\"key\": \"版本\", \"value\": \"12GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"星空蓝\"}]', 5499.00, NULL, NULL, 89, 119, 2, '2020-05-17 17:15:25', '2020-06-04 21:25:00');
INSERT INTO `goods_sku` VALUES (480, 91, '12GB+256GB 珍珠白', '[{\"key\": \"版本\", \"value\": \"12GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"珍珠白\"}]', 5499.00, NULL, NULL, 67, 118, 3, '2020-05-17 17:15:25', NULL);
INSERT INTO `goods_sku` VALUES (481, 91, '12GB+512GB 星空蓝', '[{\"key\": \"版本\", \"value\": \"12GB+512GB\"}, {\"key\": \"颜色\", \"value\": \"星空蓝\"}]', 5999.00, NULL, NULL, 23, 119, 4, '2020-05-17 17:15:25', NULL);
INSERT INTO `goods_sku` VALUES (482, 91, '12GB+512GB 珍珠白', '[{\"key\": \"版本\", \"value\": \"12GB+512GB\"}, {\"key\": \"颜色\", \"value\": \"珍珠白\"}]', 5999.00, NULL, NULL, 156, 118, 5, '2020-05-17 17:15:25', '2020-05-18 18:20:00');
INSERT INTO `goods_sku` VALUES (483, 92, '8GB+128GB 钛银黑', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"钛银黑\"}]', 3999.00, NULL, NULL, 1000, 120, 0, '2020-05-17 17:40:34', '2020-06-30 10:45:00');
INSERT INTO `goods_sku` VALUES (484, 92, '8GB+128GB 冰海蓝', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"冰海蓝\"}]', 3999.00, NULL, NULL, 1088, 121, 1, '2020-05-17 17:40:34', '2020-06-13 18:39:09');
INSERT INTO `goods_sku` VALUES (485, 92, '8GB+128GB 蜜桃金', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"蜜桃金\"}]', 3999.00, NULL, NULL, 4334, 122, 2, '2020-05-17 17:40:34', NULL);
INSERT INTO `goods_sku` VALUES (486, 92, '8GB+256GB 钛银黑', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"钛银黑\"}]', 4299.00, NULL, NULL, 12, 120, 3, '2020-05-17 17:40:34', '2020-06-02 10:45:00');
INSERT INTO `goods_sku` VALUES (487, 92, '8GB+256GB 冰海蓝', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"冰海蓝\"}]', 4299.00, NULL, NULL, 34, 121, 4, '2020-05-17 17:40:34', NULL);
INSERT INTO `goods_sku` VALUES (488, 92, '8GB+256GB 蜜桃金', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"蜜桃金\"}]', 4299.00, NULL, NULL, 45, 122, 5, '2020-05-17 17:40:34', NULL);
INSERT INTO `goods_sku` VALUES (489, 92, '12GB+256GB 钛银黑', '[{\"key\": \"版本\", \"value\": \"12GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"钛银黑\"}]', 4699.00, NULL, NULL, 35, 120, 6, '2020-05-17 17:40:34', NULL);
INSERT INTO `goods_sku` VALUES (490, 92, '12GB+256GB 冰海蓝', '[{\"key\": \"版本\", \"value\": \"12GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"冰海蓝\"}]', 4699.00, NULL, NULL, 31, 121, 7, '2020-05-17 17:40:34', NULL);
INSERT INTO `goods_sku` VALUES (491, 92, '12GB+256GB 蜜桃金', '[{\"key\": \"版本\", \"value\": \"12GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"蜜桃金\"}]', 4699.00, NULL, NULL, 899, 122, 8, '2020-05-17 17:40:34', '2020-05-18 17:30:00');
INSERT INTO `goods_sku` VALUES (492, 93, '黑色 512GB', '[{\"key\": \"颜色\", \"value\": \"黑色\"}, {\"key\": \"容量\", \"value\": \"512GB\"}]', 19999.00, NULL, 15000.00, 0, 123, 0, '2020-05-17 17:48:00', NULL);
INSERT INTO `goods_sku` VALUES (493, 94, '6GB+128GB 月幕白', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"月幕白\"}]', 2799.00, NULL, NULL, 1000, 132, 0, '2020-05-18 18:30:44', '2020-06-30 10:45:00');
INSERT INTO `goods_sku` VALUES (494, 94, '6GB+128GB 天际蓝', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"天际蓝\"}]', 2799.00, NULL, NULL, 1000, 131, 1, '2020-05-18 18:30:44', '2020-05-18 18:32:24');
INSERT INTO `goods_sku` VALUES (495, 94, '8GB+128GB 月幕白', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"月幕白\"}]', 3399.00, NULL, NULL, 1000, 132, 2, '2020-05-18 18:30:44', '2020-05-18 18:32:24');
INSERT INTO `goods_sku` VALUES (496, 94, '8GB+128GB 天际蓝', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"天际蓝\"}]', 3399.00, NULL, NULL, 1000, 131, 3, '2020-05-18 18:30:44', '2020-06-02 09:10:00');
INSERT INTO `goods_sku` VALUES (497, 94, '8GB+256GB 月幕白', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"月幕白\"}]', 3699.00, NULL, NULL, 1000, 132, 4, '2020-05-18 18:30:44', '2020-05-18 18:32:24');
INSERT INTO `goods_sku` VALUES (498, 94, '8GB+256GB 天际蓝', '[{\"key\": \"版本\", \"value\": \"8GB+256GB\"}, {\"key\": \"颜色\", \"value\": \"天际蓝\"}]', 3699.00, NULL, NULL, 1000, 131, 5, '2020-05-18 18:30:44', '2020-05-18 18:32:24');
INSERT INTO `goods_sku` VALUES (499, 95, '6GB+128GB 花影惊鸿', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"花影惊鸿\"}]', 1899.00, NULL, NULL, 3996, 136, 0, '2020-05-18 19:13:54', '2020-06-30 19:54:18');
INSERT INTO `goods_sku` VALUES (500, 95, '6GB+128GB 时光独白', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"时光独白\"}]', 1899.00, NULL, NULL, 4000, 135, 1, '2020-05-18 19:13:54', NULL);
INSERT INTO `goods_sku` VALUES (501, 95, '6GB+128GB 紫玉幻境', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"紫玉幻境\"}]', 1899.00, NULL, NULL, 3995, 134, 2, '2020-05-18 19:13:54', '2020-05-21 23:32:39');
INSERT INTO `goods_sku` VALUES (502, 95, '6GB+128GB 深海微光', '[{\"key\": \"版本\", \"value\": \"6GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"深海微光\"}]', 1899.00, NULL, NULL, 4000, 133, 3, '2020-05-18 19:13:54', NULL);
INSERT INTO `goods_sku` VALUES (503, 95, '8GB+128GB 花影惊鸿', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"花影惊鸿\"}]', 2399.00, NULL, NULL, 4000, 136, 4, '2020-05-18 19:13:54', NULL);
INSERT INTO `goods_sku` VALUES (504, 95, '8GB+128GB 时光独白', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"时光独白\"}]', 2399.00, NULL, NULL, 4000, 135, 5, '2020-05-18 19:13:54', '2020-06-30 10:45:00');
INSERT INTO `goods_sku` VALUES (505, 95, '8GB+128GB 紫玉幻境', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"紫玉幻境\"}]', 2399.00, NULL, NULL, 4000, 134, 6, '2020-05-18 19:13:54', NULL);
INSERT INTO `goods_sku` VALUES (506, 95, '8GB+128GB 深海微光', '[{\"key\": \"版本\", \"value\": \"8GB+128GB\"}, {\"key\": \"颜色\", \"value\": \"深海微光\"}]', 2399.00, NULL, NULL, 4000, 133, 7, '2020-05-18 19:13:54', NULL);
INSERT INTO `goods_sku` VALUES (507, 96, '64GB 红色', '[{\"key\": \"存储容量\", \"value\": \"64GB\"}, {\"key\": \"外观\", \"value\": \"红色\"}]', 3299.00, NULL, NULL, 3000, 145, 0, '2020-05-18 19:34:42', '2020-06-16 18:05:00');
INSERT INTO `goods_sku` VALUES (508, 96, '64GB 黑色', '[{\"key\": \"存储容量\", \"value\": \"64GB\"}, {\"key\": \"外观\", \"value\": \"黑色\"}]', 3299.00, NULL, NULL, 3000, 144, 1, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (509, 96, '64GB 白色', '[{\"key\": \"存储容量\", \"value\": \"64GB\"}, {\"key\": \"外观\", \"value\": \"白色\"}]', 3299.00, NULL, NULL, 3000, 143, 2, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (510, 96, '128GB 红色', '[{\"key\": \"存储容量\", \"value\": \"128GB\"}, {\"key\": \"外观\", \"value\": \"红色\"}]', 3799.00, NULL, NULL, 3000, 145, 3, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (511, 96, '128GB 黑色', '[{\"key\": \"存储容量\", \"value\": \"128GB\"}, {\"key\": \"外观\", \"value\": \"黑色\"}]', 3799.00, NULL, NULL, 3000, 144, 4, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (512, 96, '128GB 白色', '[{\"key\": \"存储容量\", \"value\": \"128GB\"}, {\"key\": \"外观\", \"value\": \"白色\"}]', 3799.00, NULL, NULL, 3000, 143, 5, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (513, 96, '256GB 红色', '[{\"key\": \"存储容量\", \"value\": \"256GB\"}, {\"key\": \"外观\", \"value\": \"红色\"}]', 4599.00, NULL, NULL, 3000, 145, 6, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (514, 96, '256GB 黑色', '[{\"key\": \"存储容量\", \"value\": \"256GB\"}, {\"key\": \"外观\", \"value\": \"黑色\"}]', 4599.00, NULL, NULL, 3000, 144, 7, '2020-05-18 19:34:42', '2020-05-18 19:39:18');
INSERT INTO `goods_sku` VALUES (515, 96, '256GB 白色', '[{\"key\": \"存储容量\", \"value\": \"256GB\"}, {\"key\": \"外观\", \"value\": \"白色\"}]', 4599.00, NULL, NULL, 3000, 143, 8, '2020-05-18 19:34:42', '2020-06-02 14:10:00');
INSERT INTO `goods_sku` VALUES (516, 97, '512GB 深空灰', '[{\"key\": \"存储容量\", \"value\": \"512GB\"}, {\"key\": \"外观\", \"value\": \"深空灰\"}]', 12699.00, NULL, NULL, 3432, 150, 0, '2020-05-18 19:51:49', '2020-06-30 19:54:18');
INSERT INTO `goods_sku` VALUES (517, 97, '512GB 银色', '[{\"key\": \"存储容量\", \"value\": \"512GB\"}, {\"key\": \"外观\", \"value\": \"银色\"}]', 12699.00, NULL, NULL, 3434, 151, 1, '2020-05-18 19:51:49', '2020-06-03 18:30:00');
INSERT INTO `goods_sku` VALUES (518, 97, '512GB 暗夜绿', '[{\"key\": \"存储容量\", \"value\": \"512GB\"}, {\"key\": \"外观\", \"value\": \"暗夜绿\"}]', 12699.00, NULL, NULL, 3434, 152, 2, '2020-05-18 19:51:49', '2020-06-16 19:20:00');
INSERT INTO `goods_sku` VALUES (519, 97, '512GB 金色', '[{\"key\": \"存储容量\", \"value\": \"512GB\"}, {\"key\": \"外观\", \"value\": \"金色\"}]', 12699.00, NULL, NULL, 3434, 153, 3, '2020-05-18 19:51:49', '2020-05-18 19:53:18');
INSERT INTO `goods_sku` VALUES (520, 98, '98英寸 黑色', '[{\"key\": \"尺寸\", \"value\": \"98英寸\"}, {\"key\": \"颜色\", \"value\": \"黑色\"}]', 19999.00, NULL, NULL, 90, 166, 0, '2020-05-21 22:49:16', '2020-06-17 13:54:46');
INSERT INTO `goods_sku` VALUES (521, 99, '34英寸 黑色', '[{\"key\": \"尺寸\", \"value\": \"34英寸\"}, {\"key\": \"颜色\", \"value\": \"黑色\"}]', 2499.00, NULL, NULL, 199, 167, 0, '2020-05-21 22:57:52', '2020-06-30 19:54:18');
