package com.everychecking.controller;

import javafx.fxml.FXML;

// Visual Menu FXML 의 컨트롤러, 모든 컨트롤러의 공통분모를 모은 Controller 를 상속받는다.
public class AudioMenuController extends Controller {

	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		
	}

	public void audioFrequencyTestAction() {
		changeScene("AudioFrequency", new AudioFrequencyController());
	}

	public void audioAcuityTestAction() {
		changeScene("AudioAcuity", new AudioAcuityController());
	}
}
