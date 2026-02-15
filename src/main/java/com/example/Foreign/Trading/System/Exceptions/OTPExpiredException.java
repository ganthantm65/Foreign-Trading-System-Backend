package com.example.Foreign.Trading.System.Exceptions;

public class OTPExpiredException extends Exception{
    public OTPExpiredException(String message){
        super(message);
    }
}
