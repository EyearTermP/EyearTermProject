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

// ��� ��Ʈ�ѷ��� ����и� ���� Ŭ�����̴�.
public class Controller {

	private static Stage stage = null;
	private static Stack<Parent> history = new Stack<>();

	// ��� ��Ʈ�ѷ��� �������� ������ Home ��ư�� ���� Action �� ����� �޼����̴�.
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

	// ��� ��Ʈ�ѷ��� �������� ������ Back ��ư�� ���� Action �� ����� �޼����̴�.
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
			alert.setContentText("�������� �׽�Ʈ�� �� ��� ����ũ�� �������ֽñ� �ٶ��ϴ�.");
			alert.showAndWait();
		}

		if (controller instanceof AudioAcuityController) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Audio Acuity");
			alert.setHeaderText("Microphone");
			alert.setContentText("�̾����� �������ֽñ� �ٶ��ϴ�.");
			alert.showAndWait();
		}

		if (controller instanceof AudioFrequencyController) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Audio Acuity");
			alert.setHeaderText("Microphone");
			alert.setContentText("�̾����� �������ֽñ� �ٶ��ϴ�.");
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
			alert.setContentText("�÷°˻� �����Ͱ� �����ϴ�. ��� �����͸� ����� ���� �÷°˻縦 �����Ͻðڽ��ϱ�?");
			name = "VisualAcuity";
			controller = new VisualAcuityController();
		} else if (checkModel.getIsColorBlindness() == null) {
			alert.setContentText("���Ͱ˻� �����Ͱ� �����ϴ�. ��� �����͸� ����� ���� ���Ͱ˻縦 �����Ͻðڽ��ϱ�?");
			name = "ColorBlindness";
			controller = new ColorBlindnessController();
		} else if (checkModel.getIsAstigmatism() == null) {
			alert.setContentText("���ð˻� �����Ͱ� �����ϴ�. ��� �����͸� ����� ���� ���ð˻縦 �����Ͻðڽ��ϱ�?");
			name = "Astigmatism";
			controller = new AstigmatismController();
		} else if (checkModel.getAudioFrequency() == null) {
			alert.setContentText("��û���ļ��˻� �����Ͱ� �����ϴ�. ��� �����͸� ����� ���� ��û���ļ��˻縦 �����Ͻðڽ��ϱ�?");
			name = "AudioFrequency";
			controller = new AudioFrequencyController();
		} else if (checkModel.getIsDysacousis() == null) {
			alert.setContentText("�¿� û�°˻� �����Ͱ� �����ϴ�. ��� �����͸� ����� ���� �¿� û�°˻縦 �����Ͻðڽ��ϱ�?");
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
