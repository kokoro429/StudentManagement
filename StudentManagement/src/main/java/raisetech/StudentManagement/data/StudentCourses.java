package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class StudentCourses {

  private String id;
  private String studentId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate endDate;
}
