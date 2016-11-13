package com.everychecking.manager.resource;

import java.net.URL;

import javafx.scene.image.Image;

// Image �� ���� �� �Ŵ����̴�. �Ŵ����� ���� �и� ���� ResourceManager �� ��ӹ޴´�.
public class ImageManager extends ResourceManager {
	
	public static Image getImage(String fileName) {
		URL resource = FxmlManager.class.getClassLoader().getResource("com/everychecking/image/" + fileName);
		String externalForm = resource.toExternalForm();
		Image image = new Image(externalForm);
		return image;
	}
}
