package com.kakaopay.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * CSV Controller 테스트
 * @author bbjjy
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CsvControllerTest {

	@Autowired
    private MockMvc mockMvc;
	/**
	 * csv 업로드
	 * @throws Exception
	 */
	@Test
	public void testUpload() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource("사전과제3.csv");

	    MockMultipartFile multipartfile = new MockMultipartFile("file", classPathResource.getInputStream());
		mockMvc.perform(multipart("/csv/upload").file(multipartfile))
		.andExpect(status().isOk());
	}

}
