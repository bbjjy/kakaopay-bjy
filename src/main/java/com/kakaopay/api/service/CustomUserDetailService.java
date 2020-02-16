package com.kakaopay.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kakaopay.api.exception.CUserNotFoundException;
import com.kakaopay.api.repo.UserJpaRepo;

import lombok.RequiredArgsConstructor;
/**
 * 
 * @author bbjjy
 *
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
 
    private final UserJpaRepo userJpaRepo;
 
    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
    }
}
