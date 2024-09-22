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

  @Select("SELECT * FROM students")// WHERE isDeleted = FALSE")//isDeletedがFALSEの人だけを抽出する場合
  List<Student> searchStudents();

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  //新規受講生をデータベースに保存するメソッド
  @Insert(
      "INSERT INTO students (fullName, name_ruby, nickname, email_address, address, age, gender, remark, isDeleted) "
          + "VALUES (#{fullName}, #{nameRuby}, #{nickname}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
//idを自動生成
  void registerStudent(Student student);

  //新規受講生コースをデータベースに保存するメソッド
  @Insert(
      "INSERT INTO student_courses (student_id, course_name, start_date, end_date)"
          + "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
//idを自動生成
  void registerStudentCourse(StudentCourses studentCourses);

  //受講生情報をid情報を元に取得するメソッド
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  //受講生情報を更新するメソッド
  @Update(
      "UPDATE students SET fullName = #{fullName},  name_ruby = #{nameRuby}, nickname = #{nickname}, email_address = #{emailAddress}, "
          +
          "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  //受講生コース情報をid情報を元に取得するメソッド
  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourses> findCourseByStudentId(int studentId);

  //受講生情報を更新するメソッド
  // 受講生コース情報を更新するメソッド
  @Update("UPDATE student_courses SET course_name = #{courseName}, start_date = #{startDate}, end_date = #{endDate} WHERE id = #{id} AND student_id = #{studentId}")
  void updateStudentCourse(StudentCourses studentCourses);

}
