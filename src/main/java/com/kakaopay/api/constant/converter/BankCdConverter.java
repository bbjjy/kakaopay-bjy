package com.kakaopay.api.constant.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kakaopay.api.constant.BankCd;

/**
 * jpa데이터의 코드값을 enum의 코드값으로 맵핑시켜 리턴 시킵니다. 
 * @author bbjjy
 *
 */
@Converter
public class BankCdConverter implements AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
			return BankCd.mappingCode(attribute).getCode();
		} catch (Exception e) {
			return attribute;
		}
	}

	@Override
	public String convertToEntityAttribute(String bankCd) {
		try {
			return BankCd.mappingCode(bankCd).getName();
		} catch (Exception e) {
			return bankCd;
		}
	}

}
