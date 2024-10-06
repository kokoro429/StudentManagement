package raisetech.StudentManagement.servece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {//serchStudentListに対するテスト
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

}
