package com.computatongsin.computatongsin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private T data;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(data);
    }
    
    public static <T> ResponseDto<T> fail(T data) {
        return new ResponseDto<>(data);
    }
}
