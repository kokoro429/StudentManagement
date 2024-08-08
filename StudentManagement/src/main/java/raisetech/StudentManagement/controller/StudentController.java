package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.servece.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping(value = "/studentList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<Student> getStudentList() {
    return service.serchStudentList();
  }

  @GetMapping(value = "/studentCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<StudentCourses> getStudentCourseList() {
    return service.serchStudentCourseList();
  }

}
