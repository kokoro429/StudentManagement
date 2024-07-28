package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//データベースそのもの。マイばてぃすが管理して使えるようにしてくれる。ここに書いていく。
@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM student WHERE name = #{name}")
//WHERE以降はテーブルの中から一つの要素だけをとってくるため。#以降はmybatisの機能。mySQLでは書けない。一個目のnameはデータベースのこと。
  Student searchByName(String name);//#{name}のnameはここの引数のname

  @Select("SELECT * FROM student")//WHEREを消してすべて取得
  List<Student> searchAll();

  @Insert("INSERT student values(#{name},#{age})")
  void registerStudent(String name, int age);

  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
  void updateStudent(String name, int age);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);
}
