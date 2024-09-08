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
    return repository.searchStudents();
  }

  public List<StudentCourses> serchStudentCourseList() {
    return repository.searchStudentCourses();
  }

  public void registerStudent(Student student) {
    //必要に応じてバリデーション処理などをここで行う

    // 新規受講生をデータベースに保存
    repository.insertStudent(student);
  }

}
