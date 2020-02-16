package com.kakaopay.api.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * list 리턴
 * @author bbjjy
 *
 * @param <T>
 */
@Getter
@Setter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}
