package com.kakaopay.api.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kakaopay.api.entity.HomeFinance;
import com.kakaopay.api.entity.User;

/**
 * HomeFinance Table에 질의를 요청하기 위한 Repository 생성
 * @author bbjjy
 *
 */
public interface HomeFinanceJpaRepo extends JpaRepository<HomeFinance, Long> {
	/**
	 * 주택금융 공급 금융기관(은행) 목록을 출력하는 API
	 */
    @Query(value ="SELECT BANK_CD FROM HOME_FINANCE\n" +
                  "GROUP BY BANK_CD"
    		, nativeQuery = true)
    List<Map<String,Object>> findAllBank();	
    
    
    /**
     * 년도별 각 금융기관의 지원금액 합계를 출력하는 API
     */
    @Query(value ="SELECT YEAR\r\n" + 
	    		"            , GROUP_CONCAT(BANK) AS DETAIL_AMOUNT\r\n" + 
	    		"            , SUM(AMOUNT) AS TOTAL_AMOUNT\r\n" + 
	    		"FROM \r\n" + 
	    		"(\r\n" + 
	    		"SELECT BANK_CD || ':' ||SUM(AMOUNT) AS BANK ,YEAR, SUM(AMOUNT) AS AMOUNT\r\n" + 
	    		"FROM    HOME_FINANCE\r\n" + 
	    		"GROUP BY BANK_CD, YEAR\r\n" + 
	    		"ORDER BY YEAR, BANK_CD\r\n" + 
	    		")\r\n" + 
	    		"GROUP BY YEAR\r\n" + 
	    		"ORDER BY YEAR"
    		, nativeQuery = true)
    List<Map<String,Object>> findYearBankSumAmt();	
    
    /**
     * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발 
     */
    @Query(value ="SELECT YEAR, BANK_CD , SUM(AMOUNT) AMOUNT\r\n" + 
    		"FROM HOME_FINANCE\r\n" + 
    		"GROUP BY YEAR, BANK_CD\r\n" + 
    		"ORDER BY YEAR, SUM(AMOUNT) DESC"
    		, nativeQuery = true)
    List<Map<String,Object>> findYearBankSumAmtMax();	
    
    /**
     * 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발 
     */
    @Query(value ="(\r\n" + 
    		"SELECT YEAR, AMOUNT\r\n" + 
    		"FROM\r\n" + 
    		"(\r\n" + 
    		"SELECT YEAR, BANK_CD , AVG(AMOUNT) AMOUNT\r\n" + 
    		"FROM HOME_FINANCE\r\n" + 
    		"WHERE BANK_CD = 'bank08'\r\n" + 
    		"GROUP BY YEAR\r\n" + 
    		"ORDER BY AVG(AMOUNT) ASC\r\n" + 
    		")\r\n" + 
    		"LIMIT 1\r\n" + 
    		")\r\n" + 
    		"UNION ALL\r\n" + 
    		"(\r\n" + 
    		"SELECT YEAR, AMOUNT\r\n" + 
    		"FROM\r\n" + 
    		"(\r\n" + 
    		"SELECT YEAR, BANK_CD , AVG(AMOUNT) AMOUNT\r\n" + 
    		"FROM HOME_FINANCE\r\n" + 
    		"WHERE BANK_CD = 'bank08'\r\n" + 
    		"GROUP BY YEAR\r\n" + 
    		"ORDER BY AVG(AMOUNT) DESC\r\n" + 
    		")\r\n" + 
    		"LIMIT 1\r\n" + 
    		")"
    		, nativeQuery = true)
    List<Map<String,Object>> findKebAvgMinMax();	
    
    /**
     * 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발 
     */
    @Query(value ="SELECT YEAR||LPAD(MONTH,2,0) AS YEARMONTH\r\n" + 
    		"    	    ,  BANK_CD\r\n" + 
    		"    	    ,  AMOUNT\r\n" + 
    		"FROM    HOME_FINANCE\r\n" + 
    		"WHERE BANK_CD = ?1\r\n" + 
    		"ORDER BY YEARMONTH"
    		, nativeQuery = true)
    List<Map<String,Object>> findBankPredictAmount(String bankCd);	
}