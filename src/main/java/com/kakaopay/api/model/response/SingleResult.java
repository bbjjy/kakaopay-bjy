package com.kakaopay.api.model.response;

import lombok.Getter;
import lombok.Setter;
/**
 * 단일건 리턴
 * @author bbjjy
 *
 * @param <T>
 */
@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    private T data;
}