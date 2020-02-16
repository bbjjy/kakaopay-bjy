package com.kakaopay.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.api.model.response.ListResult;
import com.kakaopay.api.service.HomeFinanceService;
import com.kakaopay.api.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * 주택 금융 서비스 controller
 * @author bbjjy
 *
 */
@Api(tags = {"2. HomeFiance"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/home")
public class HomeFianceController {
	
	private final HomeFinanceService homeFinanceService; 
	private final ResponseService responseService;
	
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})	
	@ApiOperation(value = "주택금융 공급 금융기관(은행)목록", notes = "주택금융 공급 금융기관(은행)목록 조회 한다.")
    @GetMapping(value = "/bankall")
    public ListResult<Map<String, Object>> selectAllBankList() {
		return responseService.getListResult(homeFinanceService.findAllBank());
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})	
	@ApiOperation(value = "년도별 각 금융기관의 지원금액 합계", notes = "년도별 각 금융기관의 지원금액 합계를 출력하는 API.")
	@GetMapping(value = "/banksumamt")
	public ListResult<Map<String, Object>> selectYearBankSumAmtList() {
		return responseService.getListResult(homeFinanceService.findYearBankSumAmt());
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})	
	@ApiOperation(value = "각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명", notes = "각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API")
	@GetMapping(value = "/banksumamtmax")
	public ListResult<Map<String, Object>> selectYearBankSumAmtMaxList() {
		return responseService.getListResult(homeFinanceService.findYearBankSumAmtMax());
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})	
	@ApiOperation(value = "전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액", notes = "전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API")
	@GetMapping(value = "/kebavgminmax")
	public ListResult<Map<String, Object>> selectKebAvgMinMaxList() {
		return responseService.getListResult(homeFinanceService.findKebAvgMinMax());
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})	
	@ApiOperation(value = "특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측", notes = "특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API")
	@GetMapping(value = "/bankpredictamount")
	public ListResult<Map<String, Object>> selectBankPredictAmount(@ApiParam(value = "은행명", required = true) @RequestParam String bankNm,
															@ApiParam(value = "예측월", required = true) @RequestParam String month) {
		return responseService.getListResult(homeFinanceService.findBankPredictAmount(bankNm,month));
	}
}
