package com.project.americo.jsonbin.dto;

public class DiffResponse {
	private String message;
	private boolean isEqual;
	private boolean sameSize;
	private Integer offsetDiff;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isEqual() {
		return isEqual;
	}
	public void setEqual(boolean isEqual) {
		this.isEqual = isEqual;
	}
	public boolean isSameSize() {
		return sameSize;
	}
	public void setSameSize(boolean sameSize) {
		this.sameSize = sameSize;
	}
	public Integer getOffsetDiff() {
		return offsetDiff;
	}
	public void setOffsetDiff(Integer offsetDiff) {
		this.offsetDiff = offsetDiff;
	}
}
