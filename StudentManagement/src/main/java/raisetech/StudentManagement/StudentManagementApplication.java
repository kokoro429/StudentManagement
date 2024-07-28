package raisetech.StudentManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  @GetMapping("/student")
  public String getStudent(@RequestParam("name") String name) {
    Student student = repository.searchByName(name);
    return student.getName() + " " + student.getAge() + "歳";
  }

  @GetMapping("/studentList")
  public List<String> getStudentList() {
    List<Student> students = repository.searchAll();
    List<String> studentList = students.stream()
        .map(student -> student.getName() + " " + student.getAge() + "歳")
        .collect(Collectors.toList());//要素をリストに収集
    /*List<String> studentList = new ArrayList<>(); 　//リストを生成
    for (Student student : students) {
      String studentInfo = student.getName() + " " + student.getAge() + "歳";　//studentInfoに情報を取得して入れる
      studentList.add(studentInfo);  //リストに加える
      //リストに情報がなくなるまで繰り返す
      */

    return studentList;
  }

  @PostMapping("/student")
  public void registerStudent(String name, int age) {
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudent(String name, int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name) {
    repository.deleteStudent(name);
  }


/*
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
  }*/

}
