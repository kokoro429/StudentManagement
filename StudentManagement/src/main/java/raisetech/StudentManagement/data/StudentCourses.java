package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourses {

  private int id;
  private int studentId;
  private String courseName = "";
  private LocalDate startDate = LocalDate.now(); // 今日の日付を初期値に設定;
  private LocalDate endDate = LocalDate.now().plusMonths(6); // 6ヶ月後を初期値に設定;
}
