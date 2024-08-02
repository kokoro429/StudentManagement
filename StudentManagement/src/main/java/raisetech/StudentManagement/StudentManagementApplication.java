package raisetech.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping(value = "/studentList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<Student> getStudentList() {
    return repository.search();
  }

  @GetMapping(value = "/studentCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<StudentCourses> getStudentCourseList() {
    return repository.searchStudentCourses();
  }
}
