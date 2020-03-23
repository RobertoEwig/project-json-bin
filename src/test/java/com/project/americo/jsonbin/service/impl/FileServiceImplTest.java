package com.project.americo.jsonbin.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.project.americo.jsonbin.dto.DiffResponse;
import com.project.americo.jsonbin.entity.File;
import com.project.americo.jsonbin.repository.FileRepository;
import com.project.americo.jsonbin.service.IFileService;

public class FileServiceImplTest {
	
	@Mock
	private FileRepository fileRepository;

	@Mock
	private IFileService fileServiceImpl;

	
	@InjectMocks
	private FileServiceImpl fileService;

	
	@BeforeEach
	public void initialize() {
		initMocks(this);		
	}
	
	@Test
	public void setFileTest() {
		String fileBase64 = "teste";
		String itsFrom = "right";
		Integer id = 12;
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
		
		when(fileRepository.thisFileExist(id, itsFrom))
		.thenReturn(reponseFileExist);
		
		when(fileRepository.save(file))
		.thenReturn(file);
		
		File responseExpectd = file;
			
		File response = fileService.setFile(fileBase64, itsFrom, id);
		
		assertThat(response.getKey()).isEqualTo(responseExpectd.getKey());
		assertThat(response.getFile()).isEqualTo(responseExpectd.getFile());
		assertThat(response.getItsFrom()).isEqualTo(responseExpectd.getItsFrom());
	}
	
	@Test
	public void isValidTest() {
		Integer id = 13;
		String fileBase64 = "teste";
		String itsFrom = "left";
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(reponseFileExist);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(reponseFileExist);
			
		boolean response = fileService.isValid(id);
		
		assertThat(response).isEqualTo(true);
	}
	
	@Test
	public void isValidNull1Test() {
		Integer id = 13;
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(null);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(null);
			
		boolean response = fileService.isValid(id);
		
		assertThat(response).isEqualTo(false);
	}
	
	@Test
	public void isValidNull2Test() {
		Integer id = 13;
		String fileBase64 = "teste";
		String itsFrom = "left";
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(reponseFileExist);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(null);
			
		boolean response = fileService.isValid(id);
		
		assertThat(response).isEqualTo(false);
	}
	
	@Test
	public void diffFileEqualTest() {
		Integer id = 13;
		String fileBase64 = "teste";
		String itsFrom = "left";
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(reponseFileExist);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(reponseFileExist);
			
		DiffResponse response = fileService.diffFile(id);
		
		DiffResponse diffResponse = new DiffResponse();
		
		diffResponse.setMessage("Documentos 13 idênticos.");
		diffResponse.setOffsetDiff(-1);
		diffResponse.setSameSize(true);
		diffResponse.setEqual(true);
		
		assertThat(response.getOffsetDiff()).isEqualTo(diffResponse.getOffsetDiff());
		assertThat(response.getMessage()).isEqualTo(diffResponse.getMessage());
	}
	
	@Test
	public void diffFileNoEqual1Test() {
		Integer id = 13;
		String fileBase64 = "teste";
		String itsFrom = "left";
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(reponseFileExist);
		
		ArrayList<File> reponseFileExist2 = new ArrayList<File>();
		
		File file2 = new File();
		file2.setCreadeAt(new Date());
		file2.setFile("tes");
		file2.setItsFrom(itsFrom);
		file2.setKey(id);
		
		reponseFileExist2.add(file2);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(reponseFileExist2);
			
		DiffResponse response = fileService.diffFile(id);
		
		DiffResponse diffResponse = new DiffResponse();
		
		diffResponse.setMessage("Documentos 13 com tamanhos diferentes.");
		diffResponse.setOffsetDiff(-1);
		diffResponse.setSameSize(false);
		diffResponse.setEqual(false);
		
		assertThat(response.getOffsetDiff()).isEqualTo(diffResponse.getOffsetDiff());
		assertThat(response.getMessage()).isEqualTo(diffResponse.getMessage());
	}
	
	@Test
	public void diffFileNoEqual2Test() {
		Integer id = 13;
		String fileBase64 = "teste";
		String itsFrom = "left";
		
		ArrayList<File> reponseFileExist = new ArrayList<File>();
		
		File file = new File();
		file.setCreadeAt(new Date());
		file.setFile(fileBase64);
		file.setItsFrom(itsFrom);
		file.setKey(id);
		
		reponseFileExist.add(file);
				
		when(fileRepository.thisFileExist(id, "right"))
			.thenReturn(reponseFileExist);
		
		ArrayList<File> reponseFileExist2 = new ArrayList<File>();
		
		File file2 = new File();
		file2.setCreadeAt(new Date());
		file2.setFile("test1");
		file2.setItsFrom(itsFrom);
		file2.setKey(id);
		
		reponseFileExist2.add(file2);
		
		when(fileRepository.thisFileExist(id, "left"))
			.thenReturn(reponseFileExist2);
			
		DiffResponse response = fileService.diffFile(id);
		
		DiffResponse diffResponse = new DiffResponse();
		
		diffResponse.setMessage("Documentos 13 diferentes a partir do offset 4.");
		diffResponse.setOffsetDiff(4);
		diffResponse.setSameSize(true);
		diffResponse.setEqual(false);
		
		assertThat(response.getOffsetDiff()).isEqualTo(diffResponse.getOffsetDiff());
		assertThat(response.getMessage()).isEqualTo(diffResponse.getMessage());
	}
}
