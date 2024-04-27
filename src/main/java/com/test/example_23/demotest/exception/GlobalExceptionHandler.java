package com.test.example_23.demotest.exception;

import com.test.example_23.demotest.payload.response.GenericErrorResponse;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus,
                                                      HttpHeaders httpHeaders,
                                                      WebRequest request,
                                                      @Nullable List<String> errors,
                                                      @Nullable Map<String, String> fieldErrors) {
        GenericErrorResponse errorResponse = new GenericErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        String servletPath = ((ServletWebRequest) request).getRequest().getServletPath();
        errorResponse.setPath(servletPath);
        if (!ObjectUtils.isEmpty(errors)) {
            errorResponse.setErrorMessages(errors);
        }

        if (!ObjectUtils.isEmpty(fieldErrors)) {
            errorResponse.setFieldErrors(fieldErrors);
        }
        return new ResponseEntity<>(errorResponse, httpHeaders, httpStatus);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus,
                                                      WebRequest request,
                                                      @Nullable List<String> errors,
                                                      @Nullable Map<String, String> fieldErrors) {
        return buildErrorResponse(httpStatus, new HttpHeaders(), request, errors, fieldErrors);
    }

    @ExceptionHandler(UserServiceProcessException.class)
    protected ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest webRequest) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, webRequest, Collections.singletonList(ex.getMessage()), null);
    }
}
