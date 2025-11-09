package in.utkarshhhh.resumebuiderapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
@ExceptionHandler(ResourceExistException.class)
public ResponseEntity<Map<String,Object>> handleResourceExistException(ResourceExistException ex){
      Map<String,Object> response = new HashMap<>();
      response.put("message","Resource exist");
      response.put("errors",ex.getMessage());

      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}
public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex){
    Map<String,Object> response = new HashMap<>();
    response.put("message","Something went wrong contact administrator");
    response.put("errors",ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
}
}
