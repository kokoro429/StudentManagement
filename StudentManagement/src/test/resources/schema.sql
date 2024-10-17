CREATE TABLE IF NOT EXISTS students
(
  id int PRIMARY KEY AUTO_INCREMENT,
  fullName varchar(100) NOT NULL,
  name_ruby varchar(100) NOT NULL,
  nickname varchar(100),
  email_address varchar(100) NOT NULL,
  address varchar(200),
  age smallint,
  gender varchar(200),
  remark varchar(250),
  is_deleted boolean
);

CREATE TABLE IF NOT EXISTS student_courses
(
id int PRIMARY KEY AUTO_INCREMENT,
student_id int NOT NULL,
course_name varchar(100) NOT NULL,
start_date date NOT NULL,
end_date date NOT NULL
);
