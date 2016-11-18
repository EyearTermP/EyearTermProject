package com.everychecking.controller;

import java.util.Random;

import com.everychecking.library.Timer;
import com.everychecking.library.Callback;
import com.everychecking.manager.resource.AudioManager;
import com.everychecking.model.check.CheckModel;
import com.everychecking.resource.Audio;
import com.everychecking.session.Session;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

// Audio Acuity FXML �� ��Ʈ�ѷ�, ��Ʈ�ѷ��� ����и� ���� CheckController �� ��ӹ޴´�.
public class AudioAcuityController extends CheckController {

	private CheckModel checkModel;
	
	private Audio beep;
	
	private boolean isRight;
	private int count;
	
	@FXML
	Text messageText;
	
	@FXML
	JFXButton left;
	
	@FXML
	JFXButton right;
	
	public AudioAcuityController() {
		beep = AudioManager.getAudio("beep.mp3");
	}

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
	@FXML
	void initialize() {
		checkModel = Session.getCheckModel();
		checkModel.setIsDysacousis(0);

		count = 0;

		messageText.setText("��� �� �Ҹ��� ��µ˴ϴ�.");
		
		isRight = new Random().nextBoolean();

		left.setDisable(true);
		right.setDisable(true);
		
		Timer.start(new Callback() {
			
			@Override
			public void callback(Object object) {
				speakSound();
				messageText.setText("��,�� �� �Ҹ��� �鸮�� ���� �������ּ���.");

				left.setDisable(false);
				right.setDisable(false);
			}
		}, 3000);
	}

	public void leftAudioAcuityAction() {
		int dysacousis = 0;
		
		if (isRight) {
			dysacousis = checkModel.getIsDysacousis() | 1;
			checkModel.setIsDysacousis(dysacousis);
		}
		
		next();
	}

	public void rightAudioAcuityAction() {
		int dysacousis = 0;
		
		if (!isRight) {
			dysacousis = checkModel.getIsDysacousis() | 2;
			checkModel.setIsDysacousis(dysacousis);
		}
		
		next();
	}
	
	private void next() {
		count++;
		
		if (count > 4) {
			confirmChecking();
			return;
		}

		messageText.setText("��� �� �Ҹ��� ��µ˴ϴ�.");
		
		isRight = new Random().nextBoolean();

		left.setDisable(true);
		right.setDisable(true);
		
		Timer.start(new Callback() {
			
			@Override
			public void callback(Object object) {
				speakSound();
				messageText.setText("��,�� �� �Ҹ��� �鸮�� ���� �������ּ���.");

				left.setDisable(false);
				right.setDisable(false);
			}
		}, 1500);
	}

	public void speakSound() {
		AudioManager.speak(beep, isRight?1:-1);
	}

	public void helpAction () {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Audio Acuity");
		alert.setHeaderText("Help");
		alert.setContentText("�̾����� ���� �� ������ ȯ�濡�� �˻縦 �����մϴ�. �Ҹ��� �������� �鸰�ٸ� ���� ��ư�� ������, �����ʿ��� �鸰�ٸ� ������ ��ư�� ���� �˻縦 �����մϴ�.");
		alert.showAndWait();
	}
}
