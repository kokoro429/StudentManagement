package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.searchStudents();
    assertThat(actual.size()).isEqualTo(5);
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
}