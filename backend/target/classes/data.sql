-- 预埋测试数据

-- 1. 管理员账号 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, gender, role_type, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '系统管理员', 1, 3, 1);

-- 2. 教师账号 (密码: teacher123)
INSERT INTO sys_user (username, password, real_name, gender, role_type, status) VALUES
('teacher001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '张教授', 1, 2, 1),
('teacher002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '李老师', 2, 2, 1);

-- 3. 学生账号 (密码: student123)
INSERT INTO sys_user (username, password, real_name, gender, role_type, status) VALUES
('student001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '王小明', 1, 1, 1),
('student002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '李小红', 2, 1, 1),
('student003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt9hQIu', '张小华', 1, 1, 1);

-- 4. 教师信息
INSERT INTO teacher_info (user_id, teacher_no, college, title, research_field) VALUES
(2, 'T2024001', '计算机学院', '教授', '人工智能'),
(3, 'T2024002', '计算机学院', '副教授', '大数据');

-- 5. 学生信息
INSERT INTO stu_info (user_id, student_no, college, major, grade, class_name, advisor_id) VALUES
(4, 'S2021001', '计算机学院', '计算机科学与技术', '2021', '计科2101', 2),
(5, 'S2021002', '计算机学院', '软件工程', '2021', '软工2101', 2),
(6, 'S2022001', '计算机学院', '计算机科学与技术', '2022', '计科2201', 3);

-- 6. 测试项目
INSERT INTO project (project_no, project_name, project_type, project_level, leader_id, advisor_id, description, status) VALUES
('PRJ2024001', '基于AI的智能问答系统', 1, 2, 4, 2, '本项目旨在开发一个基于大语言模型的智能问答系统', 3),
('PRJ2024002', '校园二手交易平台', 2, 1, 5, 3, '搭建一个面向高校学生的二手物品交易平台', 1);

-- 7. 项目成员
INSERT INTO project_member (project_id, user_id, member_order, role) VALUES
(1, 4, 1, 2),
(1, 5, 2, 1),
(2, 5, 1, 2),
(2, 6, 2, 1);
