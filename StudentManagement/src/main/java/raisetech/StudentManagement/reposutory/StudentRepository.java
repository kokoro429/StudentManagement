package raisetech.StudentManagement.reposutory;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コーステーブルに紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return　受講生一覧（全件）
   */
  @Select("SELECT * FROM students")// WHERE isDeleted = FALSE")//isDeletedがFALSEの人だけを抽出する場合
  List<Student> searchStudents();

  /**
   * 受講生の検索を行います。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  /**
   * 受講生のコースの全件検索を行います。
   *
   * @return　受講生のコース情報（全件）
   */
  @Select("SELECT * FROM student_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourse> findCourseByStudentId(int studentId);

  /**
   * 受講生を新規登録します。
   * IDに関しては自動採番を行います。
   *
   * @param student　受講生
   */
  @Insert(
      "INSERT INTO students (fullName, name_ruby, nickname, email_address, address, age, gender, remark, is_deleted) "
          + "VALUES (#{fullName}, #{nameRuby}, #{nickname}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
//idを自動生成
  void registerStudent(Student student);

  /**
   * 受講生コースを新規登録します。
   * IDに関しては自動採番を行います。
   *
   * @param studentCourse　受講生コース情報
   */
  @Insert(
      "INSERT INTO student_courses (student_id, course_name, start_date, end_date)"
          + "VALUES (#{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student　受講生
   */
  @Update(
      "UPDATE students SET fullName = #{fullName},  name_ruby = #{nameRuby}, nickname = #{nickname}, email_address = #{emailAddress}, "
          +
          "address = #{address}, age = #{age}, gender = #{gender}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  /**
   *　受講生コース情報のコース名を更新します。
   *
   * @param studentCourse　受講生コース情報
   */
  @Update("UPDATE student_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentCourse(StudentCourse studentCourse);

}
