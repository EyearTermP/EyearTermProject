package com.everychecking.controller;

import javafx.fxml.FXML;

// Visual Menu FXML 의 컨트롤러, 모든 컨트롤러의 공통분모를 모은 Controller 를 상속받는다.
public class VisualMenuController extends Controller {

	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		System.out.println("!!");
	}

	public void visualAcuityTestAction() {
		changeScene("VisualAcuity", new VisualAcuityController());
	}

	public void colorBlindnessTestAction() {
		changeScene("ColorBlindness", new ColorBlindnessController());
	}

	public void astigmatismTestAction() {
		changeScene("Astigmatism", new AstigmatismController());
	}
}
