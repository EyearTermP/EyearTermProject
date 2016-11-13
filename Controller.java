package com.everychecking.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

import com.everychecking.manager.resource.FxmlManager;
import com.everychecking.model.check.CheckModel;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// 모든 컨트롤러의 공통분모를 모은 클래스이다.
public class Controller {

	private static Stage stage = null;
	private static Stack<Parent> history = new Stack<>();

	// 모든 컨트롤러가 공통으로 가지는 Home 버튼에 대한 Action 을 기술한 메서드이다.
	public void homeAction() {
		if (stage == null) {
			System.out.println("stage is null.");
			return;
		}

		stage.getScene().setRoot(history.get(0));
		history.clear();

		Session.clearSession();
		return;
	}

	// 모든 컨트롤러가 공통으로 가지는 Back 버튼에 대한 Action 을 기술한 메서드이다.
	public void backAction() {
		if (stage == null) {
			System.out.println("stage is null.");
			return;
		}

		stage.getScene().setRoot(history.pop());
	}

	// Changing scene
	public FXMLLoader changeScene(String name, Controller controller) {
		if (getStage() == null) {
			System.out.println("stage is null.");
			return null;
		}

		FXMLLoader root = FxmlManager.getFXMLLoader(name);
		root.setController(controller);

		try {
			getHistory().push(getStage().getScene().getRoot());
			getStage().getScene().setRoot((Parent) root.load());

			FXMLLoader homeBack = FxmlManager.getFXMLLoader("HomeBack");
			homeBack.setController(this);

			ObservableList<Node> children = ((AnchorPane) getStage().getScene().getRoot()).getChildren();
			ObservableList<Node> homeBackChildren = ((AnchorPane) homeBack.load()).getChildren();

			JFXButton homeButton = (JFXButton) homeBackChildren.get(0);
			children.add(homeButton);

			JFXButton backButton = (JFXButton) homeBackChildren.get(0);
			children.add(backButton);

			if (controller instanceof CheckController) {
				FXMLLoader help = FxmlManager.getFXMLLoader("Help");
				help.setController(controller);

				ObservableList<Node> helpChildren = ((AnchorPane) help.load()).getChildren();

				JFXButton helpButton = (JFXButton) helpChildren.get(0);
				children.add(helpButton);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (controller instanceof VisualAcuityController) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Audio Acuity");
			alert.setHeaderText("Microphone");
			alert.setContentText("음성으로 테스트를 할 경우 마이크를 연결해주시길 바랍니다.");
			alert.showAndWait();
		}

		if (controller instanceof AudioAcuityController) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Audio Acuity");
			alert.setHeaderText("Microphone");
			alert.setContentText("이어폰을 연결해주시길 바랍니다.");
			alert.showAndWait();
		}

		if (controller instanceof AudioFrequencyController) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Audio Acuity");
			alert.setHeaderText("Microphone");
			alert.setContentText("이어폰을 연결해주시길 바랍니다.");
			alert.showAndWait();
		}

		return root;
	}

	public void confirmChecking() {
		CheckModel checkModel = Session.getCheckModel();

		String name = null;
		Controller controller = null;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Check");
		alert.setHeaderText("You have to check");

		if (checkModel.getVisualAcuity() == null) {
			alert.setContentText("시력검사 데이터가 없습니다. 결과 데이터를 만들기 전에 시력검사를 진행하시겠습니까?");
			name = "VisualAcuity";
			controller = new VisualAcuityController();
		} else if (checkModel.getIsColorBlindness() == null) {
			alert.setContentText("색맹검사 데이터가 없습니다. 결과 데이터를 만들기 전에 색맹검사를 진행하시겠습니까?");
			name = "ColorBlindness";
			controller = new ColorBlindnessController();
		} else if (checkModel.getIsAstigmatism() == null) {
			alert.setContentText("난시검사 데이터가 없습니다. 결과 데이터를 만들기 전에 난시검사를 진행하시겠습니까?");
			name = "Astigmatism";
			controller = new AstigmatismController();
		} else if (checkModel.getAudioFrequency() == null) {
			alert.setContentText("가청주파수검사 데이터가 없습니다. 결과 데이터를 만들기 전에 가청주파수검사를 진행하시겠습니까?");
			name = "AudioFrequency";
			controller = new AudioFrequencyController();
		} else if (checkModel.getIsDysacousis() == null) {
			alert.setContentText("좌우 청력검사 데이터가 없습니다. 결과 데이터를 만들기 전에 좌우 청력검사를 진행하시겠습니까?");
			name = "AudioAcuity";
			controller = new AudioAcuityController();
		} else {
			checkModel.insertData();
			backAction();
			return;
		}

		getHistory().pop();

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			changeScene(name, controller);
		} else {
			checkModel.insertData();
			backAction();
		}
	}

	// Getter Setters ..
	public Stage getStage() {
		return stage;
	}

	public Stack<Parent> getHistory() {
		return history;
	}

	public static void setStage(Stage stage) {
		Controller.stage = stage;
	}

	public static void setHistory(Stack<Parent> history) {
		Controller.history = history;
	}
}
