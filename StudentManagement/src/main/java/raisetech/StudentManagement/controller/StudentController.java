package raisetech.StudentManagement.controller;

import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.servece.StudentService;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  // 受講生リストを取得して表示
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.serchStudentList();
    List<StudentCourses> studentCourses = service.serchStudentCourseList();

    return converter.convertStudentDetails(students, studentCourses);
  }

  // 受講生コースリストを取得して表示
  @GetMapping("/studentCourseList")
  public String getStudentCourseList(Model model) {
    List<StudentCourses> studentCourseList = service.serchStudentCourseList();
    model.addAttribute("studentCourseList", studentCourseList);
    return "studentCourseList";
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    //コース情報のリストを空で初期化
    studentDetail.setStudentCourses(Arrays.asList(new StudentCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStudent";
    }

    //StudentDetailからStudentオブジェクトを所得
    Student student = studentDetail.getStudent();
    //新規受講生を登録
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  //受講生情報更新処理
  //受講生情報を取得して更新画面に渡す
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable int id, Model model) {
    StudentDetail studentDetail = service.findStudentAndCourseById(id);
    if (studentDetail.getStudentCourses() == null || studentDetail.getStudentCourses().isEmpty()) {
      System.out.println("コース情報が存在しません");
    }

    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  //更新処理
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentAndCourse(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
