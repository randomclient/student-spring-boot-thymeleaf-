package com.exercise.dto;

public class ClassResponseDto {
	private String id;
	private String name;
	
	public ClassResponseDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public ClassResponseDto() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
