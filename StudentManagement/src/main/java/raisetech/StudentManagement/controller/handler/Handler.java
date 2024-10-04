package raisetech.StudentManagement.controller.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.StudentManagement.exception.TestException;

@ControllerAdvice
public class Handler {

  @ExceptionHandler(TestException.class)
  public ResponseEntity<Map<String, String>> handleTestException(TestException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage()); // "message"キーでエラーメッセージを設定
    // MapをResponseEntityで返す
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
    ValidationErrorResponse errorResponse = new ValidationErrorResponse();
    Map<String, String> errors = new HashMap<>();
    // バリデーションエラーのフィールドとメッセージを収集
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    errorResponse.setErrors(errors); // ValidationErrorResponseのsetterを使用
    // BAD_REQUEST (400) エラーとしてクライアントに返す
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
