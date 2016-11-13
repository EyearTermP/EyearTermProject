package com.everychecking.manager.resource;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

// FXML 를 관리 할 매니저이다. 매니저의 공통 분모를 가진 ResourceManager 를 상속받는다.
public class FxmlManager extends ResourceManager {
	// FXMLLoader 를 통해 FXML 을 불러온다.
	public static Parent getParent(String name) {
		try {
			return FXMLLoader.load(FxmlManager.class.getClassLoader().getResource("com/everychecking/fxml/" + name + ".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// FXMLLoader 를 통해 FXML 을 불러온다.
	public static FXMLLoader getFXMLLoader(String name) {
		return new FXMLLoader(FxmlManager.class.getClassLoader().getResource("com/everychecking/fxml/" + name + ".fxml"));
	}
}
