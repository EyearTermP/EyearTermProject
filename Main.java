package com.everychecking.launcher;
	
import com.everychecking.controller.MainController;
import com.everychecking.manager.resource.FxmlManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

// 이 프로그램의 런쳐이다.
public class Main extends Application {
	
	// JavaFX Application 이 시작되고, 호출되는 메서드이다.
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

	// 이 프로그램의 Entry Point, JavaFX 를 런칭한다.
	public static void main(String[] args) {
		launch(args);
	}
}
