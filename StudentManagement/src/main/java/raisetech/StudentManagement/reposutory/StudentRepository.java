package raisetech.StudentManagement.reposutory;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;


//データベースに対する操作を書く。
@Mapper//MyBatisが管理して使えるようにしてくれる。
public interface StudentRepository {

  //isDeletedがFALSEの人だけを抽出
  @Select("SELECT * FROM students WHERE isDeleted = FALSE")
  List<Student> searchStudents();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  //新規受講生をデータベースに保存するメソッド
  @Insert(
      "INSERT INTO students (fullName, name_ruby, nickname, email_address, address, age, gender, remark, isDeleted) "
          +
          "VALUES (#{fullName}, #{nameRuby}, #{nickname}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, #{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
//idを自動生成
  void insertStudent(Student student);

  @Insert(
      "INSERT INTO student_courses (student_id, course_name, start_date, end_date)" +
         "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})"
  )
  @Options(useGeneratedKeys = true, keyProperty = "id")
//idを自動生成
  void insertStudentCourse(StudentCourses course);

  //受講生情報をid情報を元に取得するメソッド
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  //受講生情報を更新するメソッド
  @Update("UPDATE students SET fullName = #{fullName},  name_ruby = #{nameRuby}, nickname = #{nickname}, email_address = #{emailAddress}, " +
      "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark} WHERE id = #{id}")
  void updateStudent(Student student);
}

