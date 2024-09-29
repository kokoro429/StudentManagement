package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
  List<Student> searchStudents();

  /**
   * 受講生の検索を行います。
   *
   * @param id 　受講生ID
   * @return　受講生
   */
  Student findStudentById(int id);

  /**
   * 受講生のコースの全件検索を行います。
   *
   * @return　受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 　受講生ID
   * @return　受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> findCourseByStudentId(int studentId);

  /**
   * 受講生を新規登録します。 IDに関しては自動採番を行います。
   *
   * @param student 　受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コースを新規登録します。 IDに関しては自動採番を行います。
   *
   * @param studentCourse 　受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 　受講生
   */
  void updateStudent(Student student);

  /**
   * 　受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 　受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生IDが存在しているかを確認します。
   *
   * @param id 受講生ID
   * @return 受講生が存在する場合はtrue、存在しない場合はfalse
   */
  boolean existsById(int id);

}
