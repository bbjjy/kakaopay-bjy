package com.kakaopay.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.kakaopay.api.entity.User;
import com.kakaopay.api.repo.UserJpaRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HomeFianceControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;
    
	@Before
	public void setUp() throws Exception {
		/*파일 업로드*/
		ClassPathResource classPathResource = new ClassPathResource("사전과제3.csv");

	    MockMultipartFile multipartfile = new MockMultipartFile("file", classPathResource.getInputStream());
		mockMvc.perform(multipart("/csv/upload").file(multipartfile))
		.andExpect(status().isOk());
		
		/*로그인*/
        userJpaRepo.save(User.builder().uid("aa").name("aa").password(passwordEncoder.encode("1234")).roles(Collections.singletonList("ROLE_USER")).build());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "aa");
        params.add("password", "1234");
        MvcResult result = mockMvc.perform(post("/signin").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        token = jsonParser.parseMap(resultString).get("data").toString();		
	}
	
	/**
	 * 주택금융 공급 금융기관(은행)목록 조회 한다
	 * @throws Exception
	 */
	@Test
	public void testSelectAllBankList() throws Exception {
        mockMvc.perform(get("/home/bankall").header("Authorization", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
	}
	
	/**
	 * 년도별 각 금융기관의 지원금액 합계를 출력하는 API.
	 * @throws Exception
	 */
	@Test
	public void testSelectYearBankSumAmtList() throws Exception {
        mockMvc.perform(get("/home/banksumamt").header("Authorization", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
	}

	/**
	 * 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
	 * @throws Exception
	 */
	@Test
	public void testSelectYearBankSumAmtMaxList() throws Exception {
        mockMvc.perform(get("/home/banksumamtmax").header("Authorization", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
	}

	/**
	 * 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
	 * @throws Exception
	 */
	@Test
	public void testSelectKebAvgMinMaxList() throws Exception {
        mockMvc.perform(get("/home/kebavgminmax").header("Authorization", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
	}
	
	/**
	 * 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API
	 * @throws Exception
	 */
	@Test
	public void testSelectBankPredictAmount() throws Exception {
		mockMvc.perform(get("/home/bankpredictamount").header("Authorization", token)
				.param("bankNm", "국민은행")
				.param("month", "2"))
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
	}
	
	/**
	 * Refresh Token 테스트 Bearer token 을 붙혀 request시 갱신된 토큰을 리턴한다.
	 * @throws Exception
	 */
	@Test
	public void testSelectBankPredictAmountRefreshToken() throws Exception {
		mockMvc.perform(get("/home/bankpredictamount").header("Authorization", "Bearer "+token)
				.param("bankNm", "국민은행")
				.param("month", "2"))
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
	}

}
