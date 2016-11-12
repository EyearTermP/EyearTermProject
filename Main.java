package com.everychecking.launcher;
	
import com.everychecking.controller.MainController;
import com.everychecking.manager.resource.FxmlManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

// �� ���α׷��� �����̴�.
public class Main extends Application {
	
	// JavaFX Application �� ���۵ǰ�, ȣ��Ǵ� �޼����̴�.
	@Override
	public void start(Stage primaryStage) {
		try {
			/* FXML */
			FXMLLoader root = FxmlManager.getFXMLLoader("Main");
			
			// Controller
			MainController mainController = new MainController(primaryStage);
			root.setController(mainController);
			
			/* Scene */
			Scene scene = new Scene((Parent) root.load());
			
			/* Stage */
			primaryStage.setTitle("Eyear");
			primaryStage.setScene(scene);
			
			// Show
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// �� ���α׷��� Entry Point, JavaFX �� ��Ī�Ѵ�.
	public static void main(String[] args) {
		launch(args);
	}
}
