package com.project.americo.jsonbin.service;

import com.project.americo.jsonbin.dto.DiffResponse;
import com.project.americo.jsonbin.entity.File;

public interface IFileService {
	public File setFile(String file, String itsFrom, Integer id);
	
	public boolean isValid(Integer id);
	
	public DiffResponse diffFile(Integer id);
}
