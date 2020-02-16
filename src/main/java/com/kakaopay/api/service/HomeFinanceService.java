package com.kakaopay.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import com.kakaopay.api.constant.BankCd;
import com.kakaopay.api.entity.HomeFinance;
import com.kakaopay.api.repo.HomeFinanceJpaRepo;

import lombok.RequiredArgsConstructor;
/**
 * 주택 금융 서비스 Service
 * @author bbjjy
 *
 */
@RequiredArgsConstructor
@Service
public class HomeFinanceService {
	
	private final HomeFinanceJpaRepo homeFinanceJpaRepo;
	
	/**
	 * 주택금융 공급 금융기관(은행) 목록을 출력하는 API
	 */
	public List<Map<String,Object>> findAllBank(){
    	List<Map<String,Object>> bankList= new ArrayList<Map<String,Object>>();
    	homeFinanceJpaRepo.findAllBank().forEach(v->{
    		try {
    			Map<String,Object> row = new HashMap<String,Object>();
				row.put("bankCd", v.get("BANK_CD"));
				row.put("bankNm", BankCd.mappingCode((String) v.get("BANK_CD")).getName());
				bankList.add(row);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	});
    	return bankList;
	}
	
	/**
	 * 년도별 각 금융기관의 지원금액 합계를 출력하는 API
	 */
	public List<Map<String,Object>> findYearBankSumAmt(){
    	List<Map<String,Object>> bankSumAmtList= new ArrayList<Map<String,Object>>();
    	for (Map<String, Object> map : homeFinanceJpaRepo.findYearBankSumAmt()) {
    		try {
    			
    			String[] bankAmtList = map.get("DETAIL_AMOUNT").toString().split(",");
    			Map<String,Object> bankAmtMap = new HashMap<String,Object>();
    			for(String bankAmt : bankAmtList) {
    				String[] col = bankAmt.split(":");
    				bankAmtMap.put(BankCd.mappingCode(col[0]).getName(), col[1]);
    			}
    			Map<String,Object> row = new HashMap<String,Object>();
    			row.put("year", map.get("YEAR"));
    			row.put("total_amount", map.get("TOTAL_AMOUNT"));
    			row.put("detail_amount", bankAmtMap);
    			
    			bankSumAmtList.add(row);
    		} catch (Exception e1) {
    			e1.printStackTrace();
    		}
		}
    	return bankSumAmtList;
	}	
	
	/**
	 * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발 
	 */
	public List<Map<String,Object>> findYearBankSumAmtMax(){
    	List<Map<String,Object>> bankSumAmtMaxList= new ArrayList<Map<String,Object>>();
    	String year = "";
    	for (Map<String, Object> map : homeFinanceJpaRepo.findYearBankSumAmtMax()) {
    		try {
    			if(!year.equals(map.get("YEAR").toString())) {
    				Map<String,Object> row = new HashMap<String,Object>();
        			row.put("year", map.get("YEAR"));
        			row.put("bankNm", BankCd.mappingCode(map.get("BANK_CD").toString()).getName());
        			bankSumAmtMaxList.add(row);
    			}
    			year = map.get("YEAR").toString();
    		} catch (Exception e1) {
    			e1.printStackTrace();
    		}
		}
    	return bankSumAmtMaxList;
	}
	
	/**
	 * 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
	 */
	public List<Map<String,Object>> findKebAvgMinMax(){
    	List<Map<String,Object>> kebAvgMinMaxList= new ArrayList<Map<String,Object>>();
    	Map<String,Object> row = new HashMap<String,Object>();
    	row.put("bank", "외환은행");
    	row.put("support_amount", homeFinanceJpaRepo.findKebAvgMinMax());
    	kebAvgMinMaxList.add(row);
    	return kebAvgMinMaxList;
	}	
	
	/**
	 * 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발 
	 * @throws Exception 
	 */
	public List<Map<String,Object>> findBankPredictAmount(String bankNm, String month){
		List<Map<String,Object>> bankAmount= new ArrayList<Map<String,Object>>();
		String bankCd = "";
		try {
			bankCd = BankCd.mappingName(bankNm).getCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
        SimpleRegression simpleRegression = new SimpleRegression(true);
        
        List<Map<String,Object>> list = homeFinanceJpaRepo.findBankPredictAmount(bankCd);
        for (Map<String,Object> row : list) {
        	simpleRegression.addData(Double.parseDouble(row.get("YEARMONTH").toString()), Double.parseDouble(row.get("AMOUNT").toString()));
		}
        /*
        int index = 0;
        for (Object[] yearAndMonthAndAmountByBank : yearAndMonthAndAmountByBanks) {
            String amount = yearAndMonthAndAmountByBank[2].toString();
            simpleRegression.addData(index++, Double.parseDouble(amount));
        }

        for (int i = 0; i < month - 1; ++i) {
            simpleRegression.addData(index, simpleRegression.predict(index));
            index++;
        }
		*/
        Map<String,Object> returnMap = new HashMap<String,Object>();
        StringBuilder yearMonth = new StringBuilder("2018").append(StringUtils.leftPad(month, 2,'0'));
        int predictAmount = (int) simpleRegression.predict(Double.parseDouble(yearMonth.toString()));
        returnMap.put("bank", bankCd);
        returnMap.put("year", 2018);
        returnMap.put("month", month);
        returnMap.put("amount", predictAmount);
        bankAmount.add(returnMap);
		return bankAmount;
	}	
	
}
