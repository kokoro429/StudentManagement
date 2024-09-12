package raisetech.StudentManagement.controller;

import java.time.LocalDate;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.servece.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.serchStudentList();
    List<StudentCourses> studentCourses = service.serchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentCourses));
    return "studentList";
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.serchStudentCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(new Student());
    //コース情報のリストを空で初期化
    studentDetail.setStudentCourses(new ArrayList<>());
    studentDetail.getStudentCourses().add(new StudentCourses()); // 初期の空コースを1つ追加
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
    service.registerStudent(student);

    // StudentIDを各コースに設定し、コース情報を登録
    for (StudentCourses course : studentDetail.getStudentCourses()) {
      course.setStudentId(student.getId());
      service.registerStudentCourse(course);
    }

    return "redirect:/studentList";
  }
}
