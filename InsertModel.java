package com.everychecking.model.check;

import com.everychecking.model.data.VisualAcuity;

// 입력 모델이다. 검사 후 필요한 메서드들을 정의해둔다.
public class InsertModel {
	
	// Properties ..
	private VisualAcuity visualAcuity = null;
	private Boolean isColorBlindness = null;
	private Boolean isAstigmatism = null;
	private Integer audioFrequency = null;
	private Integer isDysacousis = null;

	// Getter Setters ..
	public VisualAcuity getVisualAcuity() {
		return visualAcuity;
	}
	public Boolean getIsColorBlindness() {
		return isColorBlindness;
	}
	public Boolean getIsAstigmatism() {
		return isAstigmatism;
	}
	public Integer getAudioFrequency() {
		return audioFrequency;
	}
	public Integer getIsDysacousis() {
		return isDysacousis;
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
	public void setIsDysacousis(Integer isDysacousis) {
		this.isDysacousis = isDysacousis;
	}
}
