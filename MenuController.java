package com.everychecking.controller;

import javafx.fxml.FXML;

// Main FXML 의 컨트롤러, 모든 컨트롤러의 공통분모를 모은 Controller 를 상속받는다.
public class MenuController extends Controller {

	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		
	}

	// Actions ..
	public void visualTestAction() {
		changeScene("VisualMenu", new VisualMenuController());
	}

	public void audioTestAction() {
		changeScene("AudioMenu", new AudioMenuController());
	}

	public void resultRecordAction() {
		changeScene("Result", new ResultController());
	}
}
