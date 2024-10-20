package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudents();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の検索が行えること() {
    int studentId = 1;
    Student student = sut.findStudentById(studentId);

    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(studentId);
    assertThat(student.getFullName()).isEqualTo("田中太郎");
  }

  @Test
  void 受講生IDで検索したときに存在しない場合はnullを返すこと() {
    int notExistentId = 999;
    Student student = sut.findStudentById(notExistentId);

    assertThat(student).isNull();
  }

  @Test
  void 受講生のコースの全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索が行えること() {
    int studentId = 1;
    List<StudentCourse> courses = sut.findCourseByStudentId(studentId);

    assertThat(courses).isNotNull();
    assertThat(courses.get(0).getStudentId()).isEqualTo(studentId);
  }

  @Test
  void 存在しない受講生IDに紐づくコース情報を検索した場合は空のリストが返ること() {
    int notExistedStudentId = 999;
    List<StudentCourse> courses = sut.findCourseByStudentId(notExistedStudentId);

    assertThat(courses).isNotNull();
    assertThat(courses.size()).isEqualTo(0);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setFullName("宮川心");
    student.setNameRuby("ミヤカワココロ");
    student.setNickname("こころ");
    student.setEmailAddress("example@gmail.com");
    student.setAddress("香川県");
    student.setAge(33);
    student.setGender("女性");
    student.setRemark("");

    sut.registerStudent(student);

    List<Student> actual = sut.searchStudents();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コースの登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setCourseName("新しいコース名");
    course.setStartDate(LocalDate.of(2024, 7, 1));
    course.setEndDate(LocalDate.of(2025, 7, 1));

    sut.registerStudentCourse(course);

    List<StudentCourse> actual = sut.searchStudentCourseList();

    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 受講生の更新が行えること() {
    int studentId = 1;
    Student student = sut.findStudentById(studentId);

    student.setNameRuby("新しいふりがな");
    sut.updateStudent(student);

    Student updatedStudent = sut.findStudentById(studentId);

    assertThat(updatedStudent.getNameRuby()).isEqualTo("新しいふりがな");
  }

  @Test
  void 受講生コース情報のコース名更新が行えること() {
    List<StudentCourse> courses = sut.findCourseByStudentId(1);

    StudentCourse course = courses.get(0);
    course.setCourseName("新しいコース名");

    sut.updateStudentCourse(course);

    List<StudentCourse> updatedCourses = sut.findCourseByStudentId(1);

    assertThat(updatedCourses.get(0).getCourseName()).isEqualTo("新しいコース名");
  }

}
