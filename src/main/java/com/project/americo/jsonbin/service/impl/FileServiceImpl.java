package com.project.americo.jsonbin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.americo.jsonbin.dto.DiffResponse;
import com.project.americo.jsonbin.entity.File;
import com.project.americo.jsonbin.repository.FileRepository;
import com.project.americo.jsonbin.service.IFileService;

@Service
public class FileServiceImpl implements IFileService{
	
	@Autowired
	private FileRepository fileRepository;
	
	@Override
	public File setFile(String fileBase64, String itsFrom, Integer id) {
		
		File file = null;
		
		if(!this.fileRepository.thisFileExist(id, itsFrom).isEmpty()) {
			file = this.fileRepository.thisFileExist(id, itsFrom).get(0);			
		}
		
		if (file != null) {
			file.setFile(fileBase64);
			return this.fileRepository.save(file);
		} else {
			
			file = new File();
			
			file.setCreadeAt(new Date());
			file.setFile(fileBase64);
			file.setKey(id);
			file.setItsFrom(itsFrom);
			
			return this.fileRepository.save(file);	
		}
		
	}


	@Override
	public boolean isValid(Integer id) {

		return this.fileRepository.thisFileExist(id, "right")!= null &&
				this.fileRepository.thisFileExist(id, "left")!= null;
	}


	@Override
	public DiffResponse diffFile(Integer id) {
		
		Map<String, String> files = getFileById(id);
		
		DiffResponse response = new DiffResponse();
		
		if (files.get("left").equals(files.get("right"))) {
			response.setSameSize(true);
			response.setOffsetDiff(-1);
			response.setEqual(true);
			
			response.setMessage("Documentos "+id+" idênticos.");
			
			return response;
		} else {
			
			if(files.get("left").length() == files.get("right").length()) {
				final Integer size = files.get("left").length();
				
				for (Integer index = 0; index < size; index ++ ) {
				
					if(files.get("left").charAt(index) != files.get("right").charAt(index)) {
						response.setSameSize(true);
						response.setOffsetDiff(index);
						response.setEqual(false);
						
						response.setMessage("Documentos "+id+" diferentes a partir do offset "+index+".");
						
						return response;
					}
				}
			} else {
				response.setSameSize(false);
				response.setOffsetDiff(-1);
				response.setEqual(false);
				
				response.setMessage("Documentos "+id+" com tamanhos diferentes.");
				
				return response;
			}
		}
		
		return response;
	}
	
	private Map<String, String> getFileById(Integer id) {
		Map<String, String> result = new HashMap<>();
		
		result.put("left", this.fileRepository.thisFileExist(id, "left").get(0).getFile());
		result.put("right", this.fileRepository.thisFileExist(id, "right").get(0).getFile());
		
		return result;
	}

}
