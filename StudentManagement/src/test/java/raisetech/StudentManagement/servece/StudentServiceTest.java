package raisetech.StudentManagement.servece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

//Mock化　Stab
//Mock ito
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;
  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {//searchStudentListに対するテスト
    //事前準備
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudents()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList();

    //検証
    verify(repository, times(1)).searchStudents();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);

    //後処理
  }

  @Test
  void 受講生詳細検索_IDに紐づく受講生情報が存在する場合_正しい受講生詳細が返されること() {//findStudentAndCourseByIdの正常系に対するテスト
    // 事前準備
    int studentId = 1;
    Student student = new Student();
    student.setId(studentId);
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.findStudentById(studentId)).thenReturn(student);
    when(repository.findCourseByStudentId(studentId)).thenReturn(studentCourseList);

    //実行
    StudentDetail actual = sut.findStudentAndCourseById(studentId);

    //検証
    assertEquals(studentId, actual.getStudent().getId());
    verify(repository, times(1)).findStudentById(studentId);
    verify(repository, times(1)).findCourseByStudentId(studentId);
  }

  @Test
  void 受講生詳細検索_IDに紐づく受講生情報が存在しない場合_例外が投げられること() {//findStudentAndCourseByIdの異常系に対するテスト
    //事前準備
    int invalidStudentId = 9999999;

    when(repository.findStudentById(invalidStudentId)).thenReturn(null);

    // 実行 & 検証
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      sut.findStudentAndCourseById(invalidStudentId);
    });

    assertEquals("指定されたIDの受講生は存在しません。", thrown.getMessage());
    verify(repository, times(1)).findStudentById(invalidStudentId);
  }

  @Test
  void 受講生登録が正常に行われること() {//registerStudentの正常系に対するテスト
    //事前準備
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentCourse studentCourse = new StudentCourse();
    studentCourseList.add(studentCourse);

    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    doNothing().when(repository).registerStudent(student);
    doNothing().when(repository).registerStudentCourse(studentCourse);

    // 実行
    StudentDetail actual = sut.registerStudent(studentDetail);

    // 検証
    assertEquals(student, actual.getStudent());
    assertEquals(studentCourseList, actual.getStudentCourseList());
    verify(repository, times(1)).registerStudent(student);
    for (StudentCourse course : studentCourseList) {
      verify(repository, times(1)).registerStudentCourse(studentCourse);
    }
  }

  @Test
  void initStudentCourseが正しく動作すること() {
    //事前準備
    Student student = new Student(1, "宮川心", "ミヤカワココロ", "こころ", "example@gmail.com", "香川県", 33, "女性", "" ,false);
    StudentCourse studentCourse = new StudentCourse(1, 1, "Javaフルコース", LocalDate.of(2023, 1, 10), LocalDate.of(2024, 1, 10));

    // 実行_パッケージプライベートメソッドを直接呼び出す
    sut.initStudentCourse(studentCourse, student);

    // 検証
    assertEquals(1, studentCourse.getStudentId());
    assertEquals(LocalDate.now(), studentCourse.getStartDate());
    assertEquals(LocalDate.now().plusYears(1), studentCourse.getEndDate());
  }

  @Test
  void 受講生詳細とコース情報が正常に更新されること() {
    //事前準備
    Student student = new Student(1, "宮川心", "ミヤカワココロ", "こころ", "example@gmail.com", "香川県", 33, "女性", "" ,false);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentCourse studentCourse = new StudentCourse();
    studentCourseList.add(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    when(repository.existsById(student.getId())).thenReturn(true);

    // 実行
    sut.updateStudentAndCourse(studentDetail);

    //検証
    verify(repository, times(1)).existsById(student.getId());
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
  }

  @Test
  void 存在しない受講生IDに対して例外がスローされること() {
    //事前準備
    Student student = new Student(9999999, "宮川心", "ミヤカワココロ", "こころ", "example@gmail.com", "香川県", 33, "女性", "" ,false);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);
    when(repository.existsById(student.getId())).thenReturn(false);

    // 実行 & 検証
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      sut.updateStudentAndCourse(studentDetail);
    });

    //例外メッセージの検証
    assertEquals("指定されたIDの受講生は存在しません。", thrown.getMessage());

    // repositoryの検証
    verify(repository, times(1)).existsById(student.getId());
    verify(repository, times(0)).updateStudent(any());
    verify(repository, times(0)).updateStudentCourse(any());
  }
}
