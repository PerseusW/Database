# Using SQL

[SQL Official Documents](https://dev.mysql.com/doc/refman/5.7/en/)

drop table Course; drop table Student; drop table SC; drop table Teacher;

create table Course (id int primary key comment '课程号', title char(20) not null comment '课程名', dept_name char(2) comment '院系名', credit int comment '学分'); create table Student (id int primary key comment '学号', name char(20) not null comment '姓名', dept_name char(2) comment '院系名'); create table SC (student_id int, course_id int, year int, grade int, primary key(student_id, course_id)); create table Teacher (id int primary key comment '教师编号', name char(8) not null comment '教师姓名', dept_name char(2) comment '所在系', salary int comment '工资');

describe Course; describe Student; describe SC; describe Teacher;



alter table Student drop age;

alter table Student add age smallint;

alter table Student modify age int;



delete from Student;

insert into Student (id, name, dept_name, age) values (171860601, 'Student1', 'CS', 20), (171860602, 'Student2', 'CS', 21), (171860603, 'Student3', 'CS', 20), (171860604, 'Student4', 'CS', 19), (171860605, 'Student5', 'MA', 20), (171860606, 'Student6', 'MA', 19), (171860607, 'Student7', 'PY', 20), (171860608, 'Student8', 'PY', 20), (171860609, 'Student9', 'CH', 22), (171860610, 'Student10', 'CH', 19);

select * from Student;



delete from Course;

insert into Course (id, title, dept_name, credit) values (101, 'Database', 'CS', 3), (102, 'Java', 'CS', 2), (201, 'Calculus', 'MA', 5), (301, 'Thermodynamics', 'PY', 3), (401, 'Organic Chemistry', 'CH', 4);

select * from Course;



delete from SC;

insert into SC (student_id, course_id, year, grade) values (171860601, 101, 2017, 80), (171860601, 102, 2017, 88), (171860602, 101, 2018, 75), (171860602, 102, 2018, 60), (171860603, 101, 2019, 95), (171860603, 201, 2019, 94), (171860604, 201, 2018, 88), (171860604, 301, 2019, 89), (171860605, 201, 2017, 42), (171860605, 101, 2018, 59), (171860606, 201, 2017, 95), (171860606, 101, 2017, 98), (171860607, 301, 2018, 60), (171860607, 401, 2019, 60), (171860608, 301, 2017, 85), (171860608, 102, 2019, 75), (171860609, 401, 2019, 75), (171860609, 301, 2019, 80), (171860610, 401, 2019, 78), (171860610, 102, 2018, 80);

select * from SC;



delete from Teacher;

insert into Teacher (id, name, dept_name, salary) values (1, 'HuWei', 'CS', 20000), (2, 'HuHua', 'MA', 18000), (3, 'YuJing', 'CH', 12000), (4, 'BuLei', 'CS', 15000), (5, 'TaoYu', 'MA', 12000);

select * from Teacher;



select distinct Student.name from Student, SC, Course where Student.id=SC.student_id and SC.course_id=Course.id and Course.dept_name='CS';

select name, dept_name from Teacher where name like 'Hu%';

select id, name from Student where id not in (select distinct student_id from SC where year<=2018);

select dept_name, max(salary) from Teacher group by dept_name;



update SC set grade=grade-2 where course_id=(select id from Course where title='Database');

update SC set grade=grade+2 where course_id=(select id from Course where title='Database');



delete from SC where SC.student_id in (select id from Student where 80>(select avg(sc2.grade) from (select * from SC) as sc2 where sc2.student_id=Student.id));



create view creditView as select student_id, sum(credit) from SC, Course where course_id=id group by student_id;

select * from creditView;



drop table SC;

show tables;