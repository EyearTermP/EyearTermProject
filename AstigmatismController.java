package com.everychecking.controller;

import com.everychecking.model.check.CheckModel;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXRadioButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

// Astigmatism FXML 의 컨트롤러, 컨트롤러의 공통분모를 모은 CheckController 를 상속받는다.
public class AstigmatismController extends CheckController {

	@FXML
	ToggleGroup answer;
	
	@FXML
	JFXRadioButton yesToggle;
	
	private CheckModel checkModel;
	
	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
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
		alert.setContentText("화면에 보이는 사진에 선 굵기가 일정하게 보이지 않는다면 '네'를 선택 후 확인버튼을 누른 다음 검사를 진행합니다.");
		alert.showAndWait();
	}
}
