package com.exercise.dto;

public class StudentResponseDto {
	private String studentId;
	private String studentName;
	private String className;
	private String register;
	private String status;
	
	
	public StudentResponseDto(String studentId, String studentName, String className, String register, String status) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.className = className;
		this.register = register;
		this.status = status;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
