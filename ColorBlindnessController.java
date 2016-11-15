package com.everychecking.controller;

import java.util.ArrayList;
import java.util.Random;

import com.everychecking.manager.resource.ImageManager;
import com.everychecking.model.check.CheckModel;
import com.everychecking.resource.ColorBlindnessImage;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

// Color Blindness FXML �� ��Ʈ�ѷ�, ��Ʈ�ѷ��� ����и� ���� CheckController �� ��ӹ޴´�.
public class ColorBlindnessController extends CheckController {
	
	private CheckModel checkModel;
	
	private ArrayList<ColorBlindnessImage> imageList = new ArrayList<ColorBlindnessImage>();
	
	private Random random;
	
	private String currentValue;
	
	private int correctCount = 0;
	
	private int wrongCount;
	
	@FXML
	private ImageView testImage;
	
	@FXML
	private JFXTextField valueInput;
	
	@FXML
	private Text prepareText;
	
	public ColorBlindnessController() {
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("5.PNG"), "" + 5));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("6.png"), "" + 6));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("8.png"), "" + 8));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("12.png"), "" + 12));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("15.png"), "" + 15));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("16.png"), "" + 16));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("29.png"), "" + 29));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("73.png"), "" + 73));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("74.png"), "" + 74));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("74_2.png"), "" + 74));
		imageList.add(new ColorBlindnessImage(ImageManager.getImage("i.png"), "i"));
		
		random = new Random();
		
		wrongCount = 0;
	}
	
	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
	@FXML
	void initialize() {
		checkModel = Session.getCheckModel();
		
		setImageRandomable();
	}
	
	private void setImageRandomable() {
		int size = imageList.size();
		
		if (size < 1)
			return;
		
		int nextInt = random.nextInt(size);
		ColorBlindnessImage colorBlindnessImage = imageList.get(nextInt);
		testImage.setImage(colorBlindnessImage.getImage());
		currentValue = colorBlindnessImage.getValue();
		imageList.remove(nextInt);
	}
	
	public void obscurityAction() {
		wrongAnswer();
	}
	
	public void confirmAction() {
		if (currentValue.equals(valueInput.getText().toLowerCase())) {
			correctCount++;
			
			if (correctCount > 5) {
				correctAnswer();
			}
			
			setImageRandomable();
			valueInput.setText("");
			valueInput.requestFocus();
		} else {
			wrongAnswer();
		}
		prepareText.setText("(Correct:" + correctCount + " Wrong:" + wrongCount + ")");
	}
	
	private void correctAnswer() {
		checkModel.setIsColorBlindness(false);
		confirmChecking();
	}

	private void wrongAnswer() {
		wrongCount++;
		
		if (wrongCount > 1) {
			checkModel.setIsColorBlindness(true);
			confirmChecking();
		}
	}

	public void helpAction () {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Color Blindness");
		alert.setHeaderText("Help");
		alert.setContentText("ȭ���� ���� Ȥ�� ���ڵ��� ���δٸ� ���̴� ���ڸ� �Է� ĭ�� �Է� �� �Է¹�ư�� �����ϴ�. ���ڵ��� �ѷ��ϰ� ������ �ʴ´ٸ� �Һи� ��ư�� ���� ���� �˻縦 �����մϴ�.");
		alert.showAndWait();
	}
}
