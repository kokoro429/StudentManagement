package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.servece.StudentService;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator;
  private ObjectMapper objectMapper;

  private Student student;
  private StudentCourse course;
  private StudentDetail studentDetail;

  @BeforeEach
  void setUp() {
    // ObjectMapperの初期化
    objectMapper = new ObjectMapper();
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    // 受講生オブジェクトの準備
    student = new Student();
    student.setId(999);
    student.setFullName("宮川心");
    student.setNameRuby("ミヤカワココロ");
    student.setNickname("こころ");
    student.setEmailAddress("example@gmail.com");
    student.setAddress("香川県");
    student.setAge(33);
    student.setGender("女性");

    // 受講生コースオブジェクトの準備
    course = new StudentCourse();
    course.setCourseName("Javaフルコース");

    // 受講生詳細オブジェクトの準備
    studentDetail = new StudentDetail(student, List.of(course));
  }


  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {//受講生一覧検索
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void IDに紐づく任意の受講生情報検索が実行できて空のリストが返ってくること()
      throws Exception {//受講生検索
    mockMvc.perform(get("/student/{id}", 999))
        .andExpect(status().isOk());

    verify(service, times(1)).findStudentAndCourseById(999);
  }

  @Test
  void 受講生情報の登録が実行できて空で返ってくること() throws Exception {//受講生登録
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON) // コンテンツタイプを設定
            .content(
                """
                        {
                         "student" : {
                            "fullName" : "宮川心",
                            "nameRuby" : "ミヤカワココロ",
                            "nickname" : "こころ",
                            "emailAddress" : "example@gmail.com",
                            "address" : "香川県",
                            "age" : "33",
                            "gender" : "女性",
                            "remark" : ""
                          },
                          "studentCourseList" : [
                          {
                            "courseName" : "Javaフルコース"
                          }
                          ]
                        }                                            
                    """
            ))
        .andExpect(status().isOk()); // ステータスコードが200 OKであることを期待

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生情報の更新が実行できて空で返ってくること() throws Exception {//受講生登録
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON) // コンテンツタイプを設定
            .content(
                """
                        {
                         "student" : {
                            "id" : "1",
                            "fullName" : "宮川心",
                            "nameRuby" : "ミヤカワココロ",
                            "nickname" : "こころ",
                            "emailAddress" : "example@gmail.com",
                            "address" : "香川県",
                            "age" : "33",
                            "gender" : "女性",
                            "remark" : ""
                          },
                          "studentCourseList" : [
                          {
                             "id" : "2",
                             "studentId" : "1",
                             "courseName" : "Javaフルコース",
                             "startDate" : "2024-01-01",
                             "endDate" : "2025-01-01"
                          }
                          ]
                        }                                            
                    """
            ))
        .andExpect(status().isOk()); // ステータスコードが200 OKであることを期待

    verify(service, times(1)).updateStudentAndCourse(any());
  }

  @Test
  void apiが利用できない時にNotFoundExceptionをスローすること() throws Exception {
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }

  //入力チェック
  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でFullNameに何も入力しなかった時に入力チェックにかかること() {
    student.setFullName("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(2);
    assertThat(violations).extracting("message")
        .contains("名前は必須です", "名前は1文字以上50文字以内で入力してください");
  }

  @Test
  void fullNameが1文字以上50文字以内でなかった時に入力チェックにかかること() {
    student.setFullName("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("名前は1文字以上50文字以内で入力してください");
  }

  @Test
  void 受講生詳細の受講生でnameRubyに何も入力しなかった時に入力チェックにかかること() {
    student.setNameRuby("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("名前（フリガナ）は必須です");
  }

  @Test
  void 受講生詳細の受講生でnameRubyにカタカナ以外を入力した時に入力チェックにかかること() {
    student.setNameRuby("aaaa");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("名前（フリガナ）はカタカナで入力してください");
  }

  @Test
  void 受講生詳細の受講生でnicknameに30文字以上入力した時に入力チェックにかかること() {
    student.setNickname("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("ニックネームは30文字以内で入力してください");
  }

  @Test
  void 受講生詳細の受講生でメールアドレスを入力しなかった時に入力チェックにかかること() {
    student.setEmailAddress("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("メールアドレスは必須です");
  }

  @Test
  void 受講生詳細の受講生で正しくない形のメールアドレスを入力した時に入力チェックにかかること() {
    student.setEmailAddress("aaaa-bbbb");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("正しいメールアドレスを入力してください");
  }

  @Test
  void 受講生詳細の受講生で住所を100文字以上入力した時に入力チェックにかかること() {
    student.setAddress(
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("住所は100文字以内で入力してください");
  }

  @Test
  void 受講生詳細の受講生コースでコース名を入力しなかった時に入力チェックにかかること() {
    course.setCourseName("");

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(course);
    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .contains("コース名は必須です");
  }
}
