
# 专业、学院信息初始数据
insert into college_info (id, college) VALUES
(1,'经济管理学院'),(2,'计算机学院');
insert into major_info (id, major, college_id) VALUES
(1,'信息管理与信息系统',1),(2,'经济学',1),(3,'计算机科学与技术',2),(4,'计算机智能',2);

# 头像信息
insert into user_avatar_info (avatar_location) values ('A001.jpg'),('A002.jpg');

# 初始化用户
insert into user_teacher_info (id) values (1);
insert into user_student_info (account_enable, email, gender, phone, student_account, student_class, student_name, student_password, major_info_id, user_avatar_info_id, user_teacher_info_id) VALUES
(1,'75570844@qq.com','Man','15032545895','S2016211050','03011603','管理员','M123456',2,1,1);

# 比赛初始化数据
insert into game_init_info (max_enterprise_number, max_member_number, period, time_stamp, total_year) VALUES
(20,6,4,current_timestamp,5);
insert into game_init_info (max_enterprise_number, max_member_number, period, time_stamp, total_year) VALUES
(40,12,4,current_timestamp,5);