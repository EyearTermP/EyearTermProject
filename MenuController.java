package com.everychecking.controller;

import javafx.fxml.FXML;

// Main FXML �� ��Ʈ�ѷ�, ��� ��Ʈ�ѷ��� ����и� ���� Controller �� ��ӹ޴´�.
public class MenuController extends Controller {

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
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
