package com.kakaopay.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.api.entity.User;

/**
 * Table에 질의를 요청하기 위한 Repository 생성
 * @author bbjjy
 *
 */
public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findByUid(String uid);

    Optional<User> findByUidAndProvider(String uid, String provider);	
}