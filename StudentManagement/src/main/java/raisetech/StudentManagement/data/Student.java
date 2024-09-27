package raisetech.StudentManagement.data;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト
 */
@Getter
@Setter
public class Student {

  //受講生ID。自動採番します。
  private int id;

  //氏名
  private String fullName;

  //ふりがな
  private String nameRuby;

  //ニックネーム
  private String nickname;

  //メールアドレス
  private String emailAddress;

  //住所
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
