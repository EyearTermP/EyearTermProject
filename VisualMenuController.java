package com.everychecking.controller;

import javafx.fxml.FXML;

// Visual Menu FXML �� ��Ʈ�ѷ�, ��� ��Ʈ�ѷ��� ����и� ���� Controller �� ��ӹ޴´�.
public class VisualMenuController extends Controller {

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
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
