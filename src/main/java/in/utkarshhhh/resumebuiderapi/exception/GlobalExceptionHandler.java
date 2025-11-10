package in.utkarshhhh.resumebuiderapi.exception;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.info("Inside GlobalExceptionHandler - handleValidationException()");
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
      log.info("Inside GlobalExceptionHandler - handleResourceExistException()");
      Map<String,Object> response = new HashMap<>();
      response.put("message","Resource exist");
      response.put("errors",ex.getMessage());

      return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}
@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex){
    log.info("Inside GlobalExceptionHandler - handleGenericException()");
    Map<String,Object> response = new HashMap<>();
    response.put("message","Something went wrong contact administrator");
    response.put("errors",ex.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
}
}
