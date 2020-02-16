package com.kakaopay.api.exception;
/**
 * 로그인 실패 Exception
 * @author bbjjy
 *
 */
public class CSigninFailedException extends RuntimeException {
    public CSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }
 
    public CSigninFailedException(String msg) {
        super(msg);
    }
 
    public CSigninFailedException() {
        super();
    }
}