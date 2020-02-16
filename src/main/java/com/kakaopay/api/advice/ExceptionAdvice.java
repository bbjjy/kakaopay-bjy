package com.kakaopay.api.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kakaopay.api.exception.CAuthenticationEntryPointException;
import com.kakaopay.api.exception.CSigninFailedException;
import com.kakaopay.api.model.response.CommonResult;
import com.kakaopay.api.service.ResponseService;

import lombok.RequiredArgsConstructor;
/**
 * ExceptionAdvice
 * @author bbjjy
 *
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
 
    private final ResponseService responseService;
 
    //exception handler
    @ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }
    
    //로그인 실패 exception
    @ExceptionHandler(CSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult SigninFailed(HttpServletRequest request, CSigninFailedException e) {
    	return responseService.getFailResult(-1,"계정이 존재하지 않거나 비밀번호가 정확하지 않습니다.");
    }    
    
    //jwt 토큰 없이 접근시
    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(-1, "해당 리소스에 접근하기 위한 권한이 없습니다.");
    }    
}