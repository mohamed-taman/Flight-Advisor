package org.siriusxi.htec.fa.infra.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.siriusxi.htec.fa.infra.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final Logger logger = LogManager.getLogger();
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiCallError<String>> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        logger.error("NotFoundException {}\n", request.getRequestURI(), ex);
        
        return ResponseEntity
                       .status(HttpStatus.NOT_FOUND)
                       .body(new ApiCallError<>("Not found exception", List.of(ex.getMessage())));
    }
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiCallError<String>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        logger.error("ValidationException {}\n", request.getRequestURI(), ex);
        
        return ResponseEntity
                       .badRequest()
                       .body(new ApiCallError<>("Validation exception", List.of(ex.getMessage())));
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiCallError<String>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logger.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);
        
        return ResponseEntity
                       .badRequest()
                       .body(new ApiCallError<>("Missing request parameter", List.of(ex.getMessage())));
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiCallError<Map<String, String>>> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        logger.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);
        
        Map<String, String> details = new HashMap<>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ex.getValue() == null ? "" : ex.getValue().toString());
        details.put("errorMessage", ex.getMessage());
        
        return ResponseEntity
                       .badRequest()
                       .body(new ApiCallError<>("Method argument type mismatch", List.of(details)));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiCallError<Map<String, String>>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logger.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);
        
        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });
        
        return ResponseEntity
                       .badRequest()
                       .body(new ApiCallError<>("Method argument validation failed", details));
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiCallError<String>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
//        logger.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);
//
//        return ResponseEntity
//                       .status(HttpStatus.FORBIDDEN)
//                       .body(new ApiCallError<>("Access denied!", List.of(ex.getMessage())));
//    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCallError<String>> handleInternalServerError(HttpServletRequest request, Exception ex) {
        logger.error("handleInternalServerError {}\n", request.getRequestURI(), ex);
        
        return ResponseEntity
                       .status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(new ApiCallError<>("Internal server error", List.of(ex.getMessage())));
    }
}

record ApiCallError<T>(
        @JsonProperty("message") String message,
        @JsonProperty("details") List<T> details) {
}
