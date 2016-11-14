package com.everychecking.controller;

import com.everychecking.model.check.CheckModel;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXRadioButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

// Astigmatism FXML �� ��Ʈ�ѷ�, ��Ʈ�ѷ��� ����и� ���� CheckController �� ��ӹ޴´�.
public class AstigmatismController extends CheckController {

	@FXML
	ToggleGroup answer;
	
	@FXML
	JFXRadioButton yesToggle;
	
	private CheckModel checkModel;
	
	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
	@FXML
	void initialize() {
		checkModel = (CheckModel)Session.getCheckModel();
	}
	
	public void confilmAction() {
		Boolean answer = null;

		if (this.answer.getSelectedToggle() != null) {
			answer = yesToggle.isSelected();
		}
		
		checkModel.setIsAstigmatism(answer);
		confirmChecking();
	}

	public void helpAction () {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Astigmatism");
		alert.setHeaderText("Help");
		alert.setContentText("ȭ�鿡 ���̴� ������ �� ���Ⱑ �����ϰ� ������ �ʴ´ٸ� '��'�� ���� �� Ȯ�ι�ư�� ���� ���� �˻縦 �����մϴ�.");
		alert.showAndWait();
	}
}
