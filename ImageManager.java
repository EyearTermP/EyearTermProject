package com.everychecking.manager.resource;

import java.net.URL;

import javafx.scene.image.Image;

// Image 를 관리 할 매니저이다. 매니저의 공통 분모를 가진 ResourceManager 를 상속받는다.
public class ImageManager extends ResourceManager {
	
	public static Image getImage(String fileName) {
		URL resource = FxmlManager.class.getClassLoader().getResource("com/everychecking/image/" + fileName);
		String externalForm = resource.toExternalForm();
		Image image = new Image(externalForm);
		return image;
	}
}
