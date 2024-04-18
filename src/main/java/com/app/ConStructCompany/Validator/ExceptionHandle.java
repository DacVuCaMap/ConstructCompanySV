package com.app.ConStructCompany.Validator;

import com.app.ConStructCompany.Response.PostOrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandle {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<PostOrderResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        PostOrderResponse response = new PostOrderResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<PostOrderResponse> handleException(Exception ex) {
        PostOrderResponse response = new PostOrderResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
