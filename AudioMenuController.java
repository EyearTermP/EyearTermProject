package com.everychecking.controller;

import javafx.fxml.FXML;

// Visual Menu FXML �� ��Ʈ�ѷ�, ��� ��Ʈ�ѷ��� ����и� ���� Controller �� ��ӹ޴´�.
public class AudioMenuController extends Controller {

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
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
