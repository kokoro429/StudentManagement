package raisetech.StudentManagement;

import java.awt.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private Map<String, String> student = new HashMap<>();//Mapの初期化

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/student")
  public Map<String, String> getStudent() {
    return student;
  }

  @PostMapping("/student")
  public void setStudent(String name, String age) {
    student.put(name, age);
  }

  @PostMapping("/updateName")
  public void updateStudentName(String oldName, String newName) {
    if (student.containsKey(oldName)) {
      String age = student.remove(oldName);//古い名前を削除して、ageのみを取得＆格納
      student.put(newName, age);//新しい名前に変更
    }
  }

  @PostMapping("/updateAge")
  public void updateStudentAge(String name, String newAge) {
    if (student.containsKey(name)) {//名前が同じ時に年齢のみ書き換え
      student.put(name, newAge);
    }
  }

}
