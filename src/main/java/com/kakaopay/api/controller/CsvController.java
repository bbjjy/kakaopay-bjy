package com.kakaopay.api.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kakaopay.api.constant.BankCd;
import com.kakaopay.api.entity.HomeFinance;
import com.kakaopay.api.model.response.ListResult;
import com.kakaopay.api.repo.HomeFinanceJpaRepo;
import com.kakaopay.api.service.ResponseService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
/**
 * CSV 업로드 controller
 * @author bbjjy
 *
 */
@Api(tags = {"1. Init"})
@RequiredArgsConstructor
@RestController
public class CsvController {
	private final HomeFinanceJpaRepo homeFinanceJpaRepo;
	private final ResponseService responseService;
	
	@ApiOperation(value = "CSV 업로드", notes = "CSV 업로드 한다.")
    @PostMapping(value = "/csv/upload")
    public ListResult<HomeFinance> upload(@RequestParam("file") MultipartFile multipartFile) {
    	Reader reader;
		try {
			reader = new InputStreamReader(multipartFile.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] s;
            while ((s = csvReader.readNext()) != null) {
                int i = 2;
                for(BankCd bankCd : BankCd.values()){
                    homeFinanceJpaRepo.save(HomeFinance.builder().year(s[0]).month(s[1])
                    		.bankCd(bankCd.getCode())
                    		.amount(Integer.parseInt(s[i].replaceAll("[^0-9]","")))
                    		.build());
                    i++;
                }
            }			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return responseService.getListResult(homeFinanceJpaRepo.findAll());
    }
}
