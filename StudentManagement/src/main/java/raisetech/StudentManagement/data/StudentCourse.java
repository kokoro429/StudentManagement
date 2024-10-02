package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 受講生コース情報を扱うオブジェクト
 */
@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {

  //受講生ID。自動採番します。
  private int id;

  //受講生ID。受講生情報のIDと一致します。
  private int studentId;

  //コース名
  @NotBlank(message = "コース名は必須です")
  private String courseName;

  //受講開始日
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  //受講終了日
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

}
