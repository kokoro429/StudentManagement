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

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    //新規受講生を登録
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  //受講生情報更新処理
  //受講生情報を取得して更新画面に渡す
  @GetMapping("/student/{id}")
  public StudentDetail editStudent(@PathVariable int id) {
    return service.findStudentAndCourseById(id);
  }

  //更新処理
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudentAndCourse(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
