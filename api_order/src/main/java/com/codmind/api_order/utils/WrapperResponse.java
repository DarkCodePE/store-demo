package com.codmind.api_order.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WrapperResponse<T> {
    private boolean ok;
    private String message;
    private T body;

    public ResponseEntity<WrapperResponse<T>> response(HttpStatus status){
        return new ResponseEntity(this, status);
    }
}
