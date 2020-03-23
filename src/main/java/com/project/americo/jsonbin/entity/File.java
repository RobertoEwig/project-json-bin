package com.project.americo.jsonbin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file")
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
    private Integer id;

	@Column(nullable = false)
    private Integer key;
	
	@Column(name = "name", nullable = false)
	private String itsFrom;
	
	@Column(nullable = false)
	private String file;
	
	@Column(name = "create_at", nullable = false)
	private Date creadeAt;

	public Integer getId() {
		return id;
	}

	public String getItsFrom() {
		return itsFrom;
	}

	public void setItsFrom(String name) {
		this.itsFrom = name;
	}

	public Date getCreadeAt() {
		return creadeAt;
	}

	public void setCreadeAt(Date date) {
		this.creadeAt = date;
	}
	
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	
}	
