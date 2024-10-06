package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.handler.ValidationErrorResponse;
import raisetech.StudentManagement.domein.StudentDetail;
import raisetech.StudentManagement.servece.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 　受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return　受講生詳細一覧（全件）
   */
  @Operation(
      summary = "一覧検索",
      description = "受講生の一覧を検索します。",
      responses = {
      @ApiResponse(responseCode = "200", description = "受講生の詳細情報一覧が返されます。"),
      @ApiResponse(responseCode = "500", description = "サーバーエラーが発生しました。")
      }
  )
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生詳細の検索です。 IDに紐づく任意の受講生情報を取得します。
   *
   * @param id 　受講生ID
   * @return　受講生詳細
   */
  @Operation(
      summary = "一覧検索",
      description = "受講生の一覧を検索します。",
      responses = {
          @ApiResponse(responseCode = "200", description = "受講生の詳細情報一覧が返されます。"),
          @ApiResponse(responseCode = "404", description = "受講生のデータが存在しません。"),
          @ApiResponse(responseCode = "500", description = "サーバーエラーが発生しました。")
      }
  )
  @GetMapping("/student/{id}")
  public ResponseEntity<?> getStudent(@PathVariable int id) {
    StudentDetail studentDetail = service.findStudentAndCourseById(id);
    return ResponseEntity.ok(studentDetail);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(
      summary = "受講生登録",
      description = "受講生を登録します。",
      responses = {
          @ApiResponse(responseCode = "200", description = "受講生が正常に登録され、受講生詳細情報が返されます。"),
          @ApiResponse(responseCode = "400", description = "リクエストパラメータが無効です。バリデーションエラーメッセージが含まれます。",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ValidationErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "サーバーエラーが発生しました。")
      })
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の更新を行います。 キャンセルフラグの更新もここで行います（論理削除）
   *
   * @param studentDetail 　受講生詳細
   * @return　実行結果
   */
  @Operation(
      summary = "受講生登録",
      description = "受講生を登録します。",
      responses = {
          @ApiResponse(responseCode = "200", description = "受講生が正常に登録され、受講生詳細情報が返されます。"),
          @ApiResponse(responseCode = "400", description = "リクエストパラメータが無効です。バリデーションエラーメッセージが含まれます。",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ValidationErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "指定されたIDの受講生は存在しません。"),
          @ApiResponse(responseCode = "500", description = "サーバーエラーが発生しました。")
      })
  @PutMapping("/updateStudent")
  public ResponseEntity<StudentDetail> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudentAndCourse(studentDetail);
    return ResponseEntity.ok(studentDetail);
  }
}
