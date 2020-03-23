package com.project.americo.jsonbin.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.americo.jsonbin.dto.DiffResponse;
import com.project.americo.jsonbin.dto.FileRequest;
import com.project.americo.jsonbin.service.IFileService;

public class FileDiffTest {
	
	private final String OPERATION_SUCESS = "Operação realizada com Sucesso.";
	private final String OPERATION_ERROR = "A string enviada não é de um arquivo Base64 valido.";
	
	@Mock
	private IFileService fileService;

	@InjectMocks
	private FileDiff fileDiffTest;

	
	@BeforeEach
	public void initialize() {
		initMocks(this);		
	}
	
	@Test
	public void diffFileBadRequestTest() {
		
		Integer id = 12;
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		DiffResponse diffResponse = new DiffResponse();
		
		diffResponse.setMessage("Não há arquivos suficientes para este ID");
		diffResponse.setOffsetDiff(null);
		diffResponse.setSameSize(false);
		diffResponse.setEqual(false);
		
		ResponseEntity<DiffResponse> responseExpectd;
		
		responseExpectd = ResponseEntity.status(httpStatus).body(diffResponse);
			
		ResponseEntity<DiffResponse> response = fileDiffTest.diffFile(id);
		
		assertThat(response.getStatusCode()).isEqualTo(responseExpectd.getStatusCode());
		assertThat(response.getBody().getMessage()).isEqualTo(responseExpectd.getBody().getMessage());
	}
	
	@Test
	public void diffFileValidTest() {
		
		Integer id = 12;
		
		when(fileService.isValid(id)).thenReturn(true);
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		DiffResponse diffResponse = new DiffResponse();
		
		diffResponse.setMessage("Não há arquivos suficientes para este ID");
		diffResponse.setOffsetDiff(null);
		diffResponse.setSameSize(false);
		diffResponse.setEqual(false);

		when(fileService.diffFile(id)).thenReturn(diffResponse);
		
		ResponseEntity<DiffResponse> responseExpectd;
		
		responseExpectd = ResponseEntity.status(httpStatus).body(diffResponse);
			
		ResponseEntity<DiffResponse> response = fileDiffTest.diffFile(id);
		
		assertThat(response.getStatusCode()).isEqualTo(responseExpectd.getStatusCode());
		assertThat(response.getBody().getMessage()).isEqualTo(responseExpectd.getBody().getMessage());
	}
	
	@Test
	public void receiveFileLeftTest() {
		
		Integer id = 12;
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		String stringResponse = OPERATION_SUCESS;
		
		FileRequest fileRequest = new FileRequest();
		
		fileRequest.setFile("teste");
		
		ResponseEntity<String> responseExpectd;
		
		responseExpectd = ResponseEntity.status(httpStatus).body(stringResponse);
			
		ResponseEntity<String> response = fileDiffTest.receiveFileLeft(id, fileRequest);
		
		assertThat(response.getStatusCode()).isEqualTo(responseExpectd.getStatusCode());
		assertThat(response.getBody()).isEqualTo(responseExpectd.getBody());
	}
	
	@Test
	public void receiveFileLeftIsNotBase64Test() {
		
		Integer id = 12;
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		String stringResponse = OPERATION_ERROR;
		
		FileRequest fileRequest = new FileRequest();
		
		fileRequest.setFile("ç");
		
		ResponseEntity<String> responseExpectd;
		
		responseExpectd = ResponseEntity.status(httpStatus).body(stringResponse);
			
		ResponseEntity<String> response = fileDiffTest.receiveFileLeft(id, fileRequest);
		
		assertThat(response.getStatusCode()).isEqualTo(responseExpectd.getStatusCode());
		assertThat(response.getBody()).isEqualTo(responseExpectd.getBody());
	}
	
	@Test
	public void receiveFileRightTest() {
		
		Integer id = 12;
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		String stringResponse = OPERATION_SUCESS;
		
		FileRequest fileRequest = new FileRequest();
		
		fileRequest.setFile("teste");
		
		ResponseEntity<String> responseExpectd;
		
		responseExpectd = ResponseEntity.status(httpStatus).body(stringResponse);
			
		ResponseEntity<String> response = fileDiffTest.receiveFileRight(id, fileRequest);
		
		assertThat(response.getStatusCode()).isEqualTo(responseExpectd.getStatusCode());
		assertThat(response.getBody()).isEqualTo(responseExpectd.getBody());
	}

}
