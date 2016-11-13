package com.everychecking.manager.resource;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

// FXML �� ���� �� �Ŵ����̴�. �Ŵ����� ���� �и� ���� ResourceManager �� ��ӹ޴´�.
public class FxmlManager extends ResourceManager {
	// FXMLLoader �� ���� FXML �� �ҷ��´�.
	public static Parent getParent(String name) {
		try {
			return FXMLLoader.load(FxmlManager.class.getClassLoader().getResource("com/everychecking/fxml/" + name + ".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// FXMLLoader �� ���� FXML �� �ҷ��´�.
	public static FXMLLoader getFXMLLoader(String name) {
		return new FXMLLoader(FxmlManager.class.getClassLoader().getResource("com/everychecking/fxml/" + name + ".fxml"));
	}
}
