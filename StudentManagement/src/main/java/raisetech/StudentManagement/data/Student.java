package raisetech.StudentManagement.data;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

  private int id;
  private String fullName;
  private String nameRuby;
  private String nickname;
  private String emailAddress;
  private String address;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

  // コース情報のリストを追加
  private List<StudentCourses> studentCourses;

}
