package raisetech.StudentManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト
 */
@Getter
@Setter
public class Student {

  //受講生ID。自動採番します。
  @NotBlank(message = "IDは必須です")
  private int id;

  //氏名
  @NotBlank(message = "名前は必須です")
  @Size(min = 1, max = 50, message = "名前は1文字以上50文字以内で入力してください")
  private String fullName;

  //ふりがな
  @NotBlank(message = "名前（フリガナ）は必須です")
  @Pattern(regexp = "^[ァ-ヶー]*$", message = "名前（フリガナ）はカタカナで入力してください")
  private String nameRuby;

  //ニックネーム
  @Size(max = 30, message = "ニックネームは30文字以内で入力してください")
  private String nickname;

  //メールアドレス
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "正しいメールアドレスを入力してください")
  private String emailAddress;

  //住所
  @Size(max = 100, message = "住所は100文字以内で入力してください")
  private String address;

  //年齢
  private int age;

  //性別
  private String gender;

  //備考
  private String remark;

  //キャンセルフラグ
  private boolean isDeleted;

}
