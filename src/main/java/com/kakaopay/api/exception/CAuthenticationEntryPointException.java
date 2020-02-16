package com.kakaopay.api.exception;
/**
 * 접근권한 Exception
 * @author bbjjy
 *
 */
public class CAuthenticationEntryPointException extends RuntimeException {
    public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }
 
    public CAuthenticationEntryPointException() {
        super();
    }
}