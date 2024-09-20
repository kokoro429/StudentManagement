package raisetech.StudentManagement.servece;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourses;
import raisetech.StudentManagement.domein.StudentDetail;
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
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      studentCourses.setStudentId(studentDetail.getStudent().getId());
      studentCourses.setStartDate(LocalDate.now());
      studentCourses.setEndDate(LocalDate.now().plusYears(1));
      repository.registerStudentCourse(studentCourses);
    }
  }

  //受講生情報を更新するメソッド
  //受講生情報を取得
  public Student findStudentById(int id) {
    return repository.findStudentById(id);
  }

  //受講生コース情報を取得
  public List<StudentCourses> findCoursesByStudentId(int studentId) {
    return repository.findCourseByStudentId(studentId);
  }

  //受講生+コース情報を更新
  @Transactional
  public void updateStudentAndCourse(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    // コース情報を取得してデバッグ
    List<StudentCourses> courses = repository.findCourseByStudentId(
        studentDetail.getStudent().getId());
    System.out.println("Courses retrieved: " + courses);  // デバッグ: 取得したコース情報をコンソールに出力

    for (StudentCourses studentCourses : studentDetail.getStudentCourses()) {
      //System.out.println("Updating course with ID: " + studentCourses.getId() + ", Student ID: " + studentCourses.getStudentId());//デバッグ用
      repository.updateStudentCourse(studentCourses);
    }
  }
}
