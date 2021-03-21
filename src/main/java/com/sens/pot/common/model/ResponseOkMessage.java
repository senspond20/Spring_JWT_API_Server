package com.sens.pot.common.model;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ResponseOkMessage implements ResponseMessage{
    private LocalDateTime timestamp;
    private int code;
    private String success;
    private Object data;

    public ResponseOkMessage(Object data){
        this.code = 200;
        this.success = "ok";
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    public ResponseEntity<?> build(){
        return ResponseEntity.ok().body(this);
    }
}
