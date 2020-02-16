package com.kakaopay.api.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.api.exception.CAuthenticationEntryPointException;
import com.kakaopay.api.model.response.CommonResult;

import lombok.RequiredArgsConstructor;

/**
 * 예외 처리용 controller
 * @author bbjjy
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
 
	//Jwt토큰 없이 api를 호출하였을 경우
    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    
    //Jwt토큰 권한 불충분
    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
        throw new AccessDeniedException("");
    }    
}