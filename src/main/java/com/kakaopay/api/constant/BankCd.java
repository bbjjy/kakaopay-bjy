package com.kakaopay.api.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 은행 코드 Enum class
 * @author bbjjy
 *
 */
@Getter
@AllArgsConstructor
public enum BankCd {
    NHUF("주택도시기금", "public01"),
    KB("국민은행", "bank01"),
    WOORI("우리은행", "bank02"),
    SHINHAN("신한은행", "bank03"),
    KOREA_CITY("한국시티은행", "bank04"),
    HANA("하나은행", "bank05"),
    NH_SH("농협은행/수협은행", "bank06"),
    KEB("외환은행", "bank08"),
    ETC_BANK("기타은행", "bank99");

    private final String name;
    private final String code;
    
    public static BankCd mappingCode(String bankCd) throws Exception {
    	return Arrays.stream(BankCd.values())
    			.filter(v->v.getCode().equals(bankCd))
    			.findAny().orElseThrow(() -> new Exception());
    }
    
    public static BankCd mappingName(String bankNm) throws Exception {
    	return Arrays.stream(BankCd.values())
    			.filter(v->v.getName().equals(bankNm))
    			.findAny().orElseThrow(() -> new Exception());
    }
}
