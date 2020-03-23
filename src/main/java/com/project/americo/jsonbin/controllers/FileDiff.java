package com.project.americo.jsonbin.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.americo.jsonbin.dto.DiffResponse;
import com.project.americo.jsonbin.dto.FileRequest;
import com.project.americo.jsonbin.service.IFileService;


@RestController
@RequestMapping("v1/diff")
public class FileDiff {
	
	private final String OPERATION_SUCESS = "Operação realizada com Sucesso.";
	private final String OPERATION_ERROR = "A string enviada não é de um arquivo Base64 valido.";
	private final String OPERATION_NOT_VALID = "Não há arquivos suficientes para este ID";
	private final String OPERATION_ERROR_INTERNAL = "Erro durante o processamento.";
	
	@Autowired
	IFileService fileService;
	
	public FileDiff(IFileService fileService) {
		this.fileService = fileService;
	}
	
	public FileDiff() {}
	
	@GetMapping("/{id}")
	public ResponseEntity<DiffResponse> diffFile(@PathVariable Integer id){
		DiffResponse response = new DiffResponse();
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		if (fileService.isValid(id)) {
			response = fileService.diffFile(id);
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			
			response.setMessage(OPERATION_NOT_VALID);			
		}
		
		return ResponseEntity.status(httpStatus).body(response);
	}
	
	@PostMapping("/{id}/left")
	public ResponseEntity<String> receiveFileLeft(@PathVariable Integer id,
			@RequestBody FileRequest fileRequest){
		
		return receiveFile(id, fileRequest, "left");
	}
	
	@PostMapping("/{id}/right")
	public ResponseEntity<String> receiveFileRight(@PathVariable Integer id,
			@RequestBody FileRequest fileRequest){
		
		return receiveFile(id, fileRequest, "right");
	}
	
	private ResponseEntity<String> receiveFile(Integer id,
			FileRequest fileRequest, String itsFrom) {
		
		HttpStatus httpStatus = HttpStatus.OK;
		
		String message = OPERATION_SUCESS;
		
		Boolean isBase64 = Base64.isBase64(fileRequest.getFile());
		
		if(isBase64) {
			try {
				fileService.setFile(fileRequest.getFile(), itsFrom, id);				
			}catch (Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				
				message = OPERATION_ERROR_INTERNAL;
			}
		} else {
			httpStatus = HttpStatus.BAD_REQUEST;
			
			message = OPERATION_ERROR;
		}
		
		return ResponseEntity.status(httpStatus).body(message);
	}
	
}
