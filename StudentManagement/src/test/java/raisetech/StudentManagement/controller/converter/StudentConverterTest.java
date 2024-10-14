package raisetech.StudentManagement.controller.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domein.StudentDetail;

class StudentConverterTest {
  private StudentConverter studentConverter;

  @BeforeEach
  public  void setUp() {
    studentConverter = new StudentConverter();
  }

  @Test
  public void 受講生情報と受講生コース情報がid_studentIdで紐づけられ_ひとまとめのデータstudentDetail作成できること() {
    // 受講生オブジェクトの準備
    Student student1 = new Student();
    student1.setId(1);
    student1.setFullName("宮川心");
    student1.setNameRuby("ミヤカワココロ");
    student1.setNickname("こころ");
    student1.setEmailAddress("example@gmail.com");
    student1.setAddress("香川県");
    student1.setAge(33);
    student1.setGender("女性");

    Student student2 = new Student();
    student2.setId(2);
    student2.setFullName("山田太郎");
    student2.setNameRuby("ヤマダタロウ");
    student2.setNickname("タロ");
    student2.setEmailAddress("example@gmail.com");
    student2.setAddress("福井県");
    student2.setAge(38);
    student2.setGender("男性");

    List<Student> studentList = Arrays.asList(student1, student2);

    // 受講生コースオブジェクトの準備
    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);//student1に紐づけ
    course1.setCourseName("Javaフルコース");
    course1.setStartDate(LocalDate.of(2023, 1, 10));
    course1.setEndDate(LocalDate.of(2024, 1, 10));

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(1);//student1に紐づけ
    course2.setCourseName("AWSフルコース");
    course2.setStartDate(LocalDate.of(2023, 2, 10));
    course2.setEndDate(LocalDate.of(2024, 2, 10));

    StudentCourse course3 = new StudentCourse();
    course3.setId(3);
    course3.setStudentId(2);//student2に紐づけ
    course3.setCourseName("AWSフルコース");
    course3.setStartDate(LocalDate.of(2023, 2, 10));
    course3.setEndDate(LocalDate.of(2024, 2, 10));

    List<StudentCourse> studentCourseList = Arrays.asList(course1, course2, course3);

    //実行
    List<StudentDetail> result = studentConverter.convertStudentDetails(studentList, studentCourseList);

    //検証
    assertEquals(2, result.size()); // 2人の学生
    assertEquals(2, result.get(0).getStudentCourseList().size());//宮川心には2つのコースが紐づく
    assertEquals(1, result.get(1).getStudentCourseList().size());//山田太郎には１つのコースが紐づく
  }

}
