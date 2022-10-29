package com.computatongsin.computatongsin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }
    
    public static <T> ResponseDto<T> fail(HttpStatus httpStatus, String msg) {
        return new ResponseDto<>(false, null, new Error(httpStatus, msg));
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private HttpStatus httpStatus;
        private String msg;
    }
}
