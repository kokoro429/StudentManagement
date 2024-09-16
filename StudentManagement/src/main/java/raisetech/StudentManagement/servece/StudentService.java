package raisetech.StudentManagement.servece;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.reposutory.StudentRepository;

@Service//Springが認識してくれるようになる。
public class StudentService {

  private StudentRepository repository;

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

  @Transactional
  public void registerStudent(Student student) {
    //必要に応じてバリデーション処理などをここで行う

    // 新規受講生をデータベースに保存
    repository.insertStudent(student);
  }

  //新しくコース情報を登録するメソッド
  public void registerStudentCourse(StudentCourses course) {
    //コース情報をデータベースに保存
    repository.insertStudentCourse(course);
  }


  //受講生情報を更新するメソッド
  //受講生情報を取得
  public Student findStudentById(int id){
    return repository.findStudentById(id);
  }

  //受講生情報を更新
  public void updateStudent(Student student) {
    repository.updateStudent(student);
  }
}
