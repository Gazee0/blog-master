package com.xiaobin.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//指定返回的状态码-返回到404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends  RuntimeException{
    public NotFoundException(){

    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
