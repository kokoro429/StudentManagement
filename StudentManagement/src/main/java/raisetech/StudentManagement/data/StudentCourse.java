package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 受講生コース情報を扱うオブジェクト
 */
@Getter
@Setter
public class StudentCourse {

  //受講生ID。自動採番します。
  private int id;

  //受講生ID。受講生情報のIDと一致します。
  private int studentId;

  //コース名
  private String courseName;

  //受講開始日
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  //受講終了日
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

}
