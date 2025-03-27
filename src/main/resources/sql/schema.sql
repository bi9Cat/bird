-- 用户信息表
CREATE TABLE `tb_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `phoneNumber` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `nickName` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(256) DEFAULT NULL COMMENT '用户密码',
  `directSupervisorId` bigint DEFAULT NULL COMMENT '直属主管ID',
  `departmentId` bigint DEFAULT NULL COMMENT '所属部门ID',
  `userType` tinyint DEFAULT NULL COMMENT '用户类型 0-普通用户;1-部门主管',
  `status` tinyint DEFAULT NULL COMMENT '用户状态 0-正常; 1-已删除',
  `loginMethod` tinyint DEFAULT NULL COMMENT '登录方式 0-普通登录; 1-第三方登录',
  `createTime` bigint DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

-- 部门信息表
CREATE TABLE `tb_department_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `departmentName` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `supervisorId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '部门主管ID',
  `status` tinyint DEFAULT NULL COMMENT '部门状态 0-正常; 1-已删除',
  `createTime` bigint DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci