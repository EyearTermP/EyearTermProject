package com.everychecking.controller;

import com.everychecking.database.Database;
import com.everychecking.model.data.User;
import com.everychecking.model.data.UserRectCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Main FXML �� ��Ʈ�ѷ�, ��� ��Ʈ�ѷ��� ����и� ���� Controller �� ��ӹ޴´�.
public class MainController extends Controller {

	@FXML
	private HBox mainHBox;

	@FXML
	private JFXTextField name;

	@FXML
	private JFXTextField age;

	@FXML
	private JFXListView<User> list;

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
	@FXML
	void initialize() {
		name.clear();
		age.clear();
		list.getItems().clear();
		
		list.setCellFactory(new javafx.util.Callback<ListView<User>, ListCell<User>>() {

			@Override
			public ListCell<User> call(ListView<User> param) {
				return new UserRectCell();
			}
		});
		
		Database.getUserList(list);
	}

	// Constructor
	public MainController(Stage stage) {
		Controller.setStage(stage);
	}

	// Actions ..
	public void nextAction() {
		Database.createUser(name.getText(), Integer.parseInt(age.getText()));
		Database.getUserList(list);
		name.clear();
		age.clear();
	}

	public void nextWithNumberAction() {
		Database.createSession(list.getSelectionModel().getSelectedItem().getNo());
		changeScene("Menu", new MenuController());
	}
	
	public void removeUserAcion() {
		Database.removeUser(list.getSelectionModel().getSelectedItem().getNo());
		Database.getUserList(list);
	}
}
