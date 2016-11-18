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

// Audio Acuity FXML 의 컨트롤러, 컨트롤러의 공통분모를 모은 CheckController 를 상속받는다.
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

	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		checkModel = Session.getCheckModel();
		checkModel.setIsDysacousis(0);

		count = 0;

		messageText.setText("잠시 후 소리가 출력됩니다.");
		
		isRight = new Random().nextBoolean();

		left.setDisable(true);
		right.setDisable(true);
		
		Timer.start(new Callback() {
			
			@Override
			public void callback(Object object) {
				speakSound();
				messageText.setText("좌,우 중 소리가 들리는 쪽을 선택해주세요.");

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

		messageText.setText("잠시 후 소리가 출력됩니다.");
		
		isRight = new Random().nextBoolean();

		left.setDisable(true);
		right.setDisable(true);
		
		Timer.start(new Callback() {
			
			@Override
			public void callback(Object object) {
				speakSound();
				messageText.setText("좌,우 중 소리가 들리는 쪽을 선택해주세요.");

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
		alert.setContentText("이어폰을 착용 후 조용한 환경에서 검사를 진행합니다. 소리가 왼족에서 들린다면 왼쪽 버튼을 누르고, 오른쪽에서 들린다면 오른쪽 버튼을 눌러 검사를 진행합니다.");
		alert.showAndWait();
	}
}
