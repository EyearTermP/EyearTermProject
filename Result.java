package com.everychecking.model.data;

import java.sql.Date;

// 검사 결과의 모델이다.
public class Result {
	// Properties ..
	private Date checkDay;
	private User user;
	private VisualAcuity visualAcuity;
	private Boolean isColorBlindness;
	private Boolean isAstigmatism;
	private Integer audioFrequency;
	private Integer isDysacousis;
	
	// Constructors ..
	public Result (User user, Date checkDay, Double visualAcuityLeft, Double visualAcuityRight, Boolean isColorBlindness, Boolean isAstigmatism, Integer audioFrequency, Integer isDysacousis) {
		this(user, checkDay, new VisualAcuity(visualAcuityLeft, visualAcuityRight), isColorBlindness, isAstigmatism, audioFrequency, isDysacousis);
	}
	
	public Result (User user, Date checkDay, VisualAcuity visualAcuity, Boolean isColorBlindness, Boolean isAstigmatism, Integer audioFrequency, Integer isDysacousis) {
		this.user = user;
		this.checkDay = checkDay;
		this.visualAcuity = visualAcuity;
		this.isColorBlindness = isColorBlindness;
		this.isAstigmatism = isAstigmatism;
		this.audioFrequency = audioFrequency;
		this.isDysacousis = isDysacousis;
	}
	
	// Getters ..
	public User getUser() {
		return user;
	}
	public VisualAcuity getVisualAcuity() {
		return visualAcuity;
	}
	public Boolean isColorBlindness() {
		return isColorBlindness;
	}
	public Boolean isAstigmatism() {
		return isAstigmatism;
	}
	public Integer getAudioFrequency() {
		return audioFrequency;
	}
	public Integer isDysacousis() {
		return isDysacousis;
	}
	public Date getCheckDay() {
		return checkDay;
	}
	
	// Setters ..
	public void setUser(User user) {
		this.user = user;
	}
	public void setVisualAcuity(VisualAcuity visualAcuity) {
		this.visualAcuity = visualAcuity;
	}
	public void setIsColorBlindness(Boolean isColorBlindness) {
		this.isColorBlindness = isColorBlindness;
	}
	public void setIsAstigmatism(Boolean isAstigmatism) {
		this.isAstigmatism = isAstigmatism;
	}
	public void setAudioFrequency(Integer audioFrequency) {
		this.audioFrequency = audioFrequency;
	}
	public void setDysacousis(Integer isDysacousis) {
		this.isDysacousis = isDysacousis;
	}
	public void setCheckDay(Date checkDay) {
		this.checkDay = checkDay;
	}
}
