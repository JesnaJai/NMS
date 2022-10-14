package com.nintriva.nms.configur;

import com.nintriva.nms.vo.ErrorVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Configuration
public class Exception {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< List<ErrorVo>> handlerMethodArgsNotValidException(MethodArgumentNotValidException exception){
        List<ErrorVo> error = exception.getBindingResult().getAllErrors().stream().map(objectError -> ErrorVo.builder()
                .fieldName(((FieldError) objectError).getField())
                .message(List.of(Objects.requireNonNull(objectError.getDefaultMessage())))
                .build())
                .collect(Collectors.toList());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
