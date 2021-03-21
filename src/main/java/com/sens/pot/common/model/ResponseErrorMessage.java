package com.sens.pot.common.model;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ResponseErrorMessage implements ResponseMessage{
    private LocalDateTime timestamp;
    private int code;
    private String message;
    private String error;
    
    public ResponseErrorMessage(HttpStatus status, String message){
        this.code = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    public ResponseEntity<?> build(){
        return ResponseEntity.status(HttpStatus.valueOf(this.code)).body(this);
    }
}
