package com.lcwd.electronic.store.exceptions;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message){
        super(message);
    }
    public  BadApiRequest(){
        super("Bad Request !!");
    }
}
