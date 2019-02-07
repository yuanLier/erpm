
# 专业、学院信息初始数据
insert into college_info (id, college) VALUES
 (1,'经济管理学院'),(2,'计算机学院');
insert into major_info (id, major, college_id) VALUES
 (1,'信息管理与信息系统',1),(2,'经济学',1),(3,'计算机科学与技术',2),(4,'计算机智能',2);

# 头像信息
insert into user_avatar_info (avatar_location) values ('A001.jpg'),('A002.jpg');