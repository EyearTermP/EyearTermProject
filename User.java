package com.everychecking.model.data;

// 사용자 모델이다.
public class User {
	// Properties ..
	private int no;
	private String name;
	private int age;
	
	// Constructors ..
	public User(String name, int age) {
		this (-1, name, age);
	}
	
	public User(int no, String name, int age) {
		this.setNo(no);
		this.name = name;
		this.age = age;
	}

	// Getters ..
	public int getNo() {
		return no;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}

	// Setters ..
	public void setNo(int no) {
		this.no = no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
