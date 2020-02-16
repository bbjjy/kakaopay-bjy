package com.kakaopay.api.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kakaopay.api.constant.BankCd;
import com.kakaopay.api.constant.converter.BankCdConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주택금융 공급현황 분석 데이터 jpa entity
 * @author bbjjy
 *
 */

@Builder // builder를 사용할수 있게 합니다.
@Entity // jpa entity임을 알립니다.
@Getter // user 필드값의 getter를 자동으로 생성합니다.
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
@Table(name = "HomeFinance") // 'home_finance' 테이블과 매핑됨을 명시
public class HomeFinance {
    @Id // primaryKey임을 알립니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  pk생성전략을 DB에 위임한다는 의미입니다. mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됩니다.
    private long msrl;
    
    @Column(nullable = false) // 연도
    private String year;
    @Column(nullable = false) // 월
    private String month;
    @Column(nullable = false) // 금액
    private long amount;
    
    @Convert(converter = BankCdConverter.class)
    @Column(nullable = false) // 은행코드
    private String bankCd;
}
