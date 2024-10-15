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

  private StudentConverter sut;

  @BeforeEach
  public void setUp() {
    sut = new StudentConverter();
  }

  @Test
  public void 受講生情報と受講生コース情報がid_studentIdで紐づけられ_ひとまとめのデータstudentDetail作成できること() {
    // 受講生オブジェクトの準備。コンストラクタで初期化
    Student student1 = new Student(1, "宮川心", "ミヤカワココロ", "こころ", "example@gmail.com", "香川県", 33, "女性", "", false);
    Student student2 = new Student(2, "山田太郎", "ヤマダタロウ", "タロ", "example@gmail.com", "福井県", 38, "男性", "" ,false);

    List<Student> studentList = Arrays.asList(student1, student2);

    // 受講生コースオブジェクトの準備（同様にコンストラクタを使用）
    StudentCourse course1 = new StudentCourse(1, 1, "Javaフルコース", LocalDate.of(2023, 1, 10), LocalDate.of(2024, 1, 10));
    StudentCourse course2 = new StudentCourse(2, 1, "AWSフルコース", LocalDate.of(2023, 2, 10), LocalDate.of(2024, 2, 10));
    StudentCourse course3 = new StudentCourse(3, 2, "AWSフルコース", LocalDate.of(2023, 2, 10), LocalDate.of(2024, 2, 10));

    List<StudentCourse> studentCourseList = Arrays.asList(course1, course2, course3);

    //実行
    List<StudentDetail> result = sut.convertStudentDetails(studentList, studentCourseList);

    //検証
    assertEquals(2, result.size()); // 2人の学生
    assertEquals(2, result.get(0).getStudentCourseList().size());//宮川心には2つのコースが紐づく
    assertEquals(1, result.get(1).getStudentCourseList().size());//山田太郎には１つのコースが紐づく
  }

}
