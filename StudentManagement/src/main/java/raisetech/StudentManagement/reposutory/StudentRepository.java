package raisetech.StudentManagement.reposutory;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;


//データベースに対する操作を書く。
@Mapper//MyBatisが管理して使えるようにしてくれる。
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> searchStudents();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

}

