package raisetech.StudentManagement.servece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.reposutory.StudentRepository;

@Service//Springが認識してくれるようになる。
public class StudentService {

  private StudentRepository repository;
  private Student student;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> serchStudentList() {//検索処理
    //全学生のリストを取得
    List<Student> allStudents = repository.searchStudents();

    //絞り込みをする。年齢が30代の人のみを抽出する。
    List<Student> studentsInTheir30s = allStudents.stream()
        .filter(allStudent -> allStudent.getAge() >= 30 && allStudent.getAge() < 40)
        .collect(Collectors.toList());
    //抽出したリストをコントローラーに渡す。
    return studentsInTheir30s;
  }


  public List<StudentCourses> serchStudentCourseList() {
    //コースの全情報を取得
    List<StudentCourses> allStudentCourses = repository.searchStudentCourses();

    //絞り込み検索で「Javaコース」のコース情報のみを抽出する
    List<StudentCourses> javaFullCourseEnrollments = allStudentCourses.stream()
        .filter(allStudentCourse -> allStudentCourse.getCourseName().equals("Javaフルコース"))
        .collect(Collectors.toList());
    //抽出したリストをコントローラーに返す。
    return javaFullCourseEnrollments;
  }
}
