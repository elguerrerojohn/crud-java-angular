package crud.java_angular.demo.infrastructure.adapter.in.web;

import crud.java_angular.demo.domain.exception.DomainValidationException;
import crud.java_angular.demo.domain.exception.TareaNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejo centralizado de errores en el borde web.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TareaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(TareaNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<Map<String, Object>> handleDomainValidation(DomainValidationException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST, "Error de validación");
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        body.put("errores", errores);
        return ResponseEntity.badRequest().body(body);
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(baseBody(status, mensaje));
    }

    private Map<String, Object> baseBody(HttpStatus status, String mensaje) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("mensaje", mensaje);
        return body;
    }
}
