package com.everychecking.controller;

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

// Audio Frequency FXML 의 컨트롤러, 컨트롤러의 공통분모를 모은 CheckController 를 상속받는다.
public class AudioFrequencyController extends CheckController {
	
	private CheckModel checkModel;
	
	private Audio [] beep = new Audio[10];
		
	private int count;
	
	private int [] hertz = {5000, 8000, 10000, 12000, 15000, 16000, 17000, 18000, 19000, 20000};
	
	@FXML
	Text messageText;
	
	@FXML
	JFXButton canButton;
	@FXML
	JFXButton cantButton;
	
	public AudioFrequencyController() {
		beep[0] = AudioManager.getAudio("5000.wav");
		beep[1] = AudioManager.getAudio("8000.wav");
		beep[2] = AudioManager.getAudio("10000.wav");
		beep[3] = AudioManager.getAudio("12000.wav");
		beep[4] = AudioManager.getAudio("15000.wav");
		beep[5] = AudioManager.getAudio("16000.wav");
		beep[6] = AudioManager.getAudio("17000.wav");
		beep[7] = AudioManager.getAudio("18000.wav");
		beep[8] = AudioManager.getAudio("19000.wav");
		beep[9] = AudioManager.getAudio("20000.wav");
	}
	
	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		checkModel = Session.getCheckModel();
		
		count = -1;
		
		messageText.setText("준비가 되셨다면 O버튼을 눌러주세요.");
		
		cantButton.setDisable(true);
	}
	
	public void canAction() {
				
		messageText.setText("잠시 후 소리가 출력됩니다.");
		
		canButton.setDisable(true);
		cantButton.setDisable(true);
		
		count++;
		
		if(count == 10){
			count = 9;
			cantAction();
		}
		
		Timer.start(new Callback() {
			@Override
			public void callback(Object object) {
				AudioManager.speak(beep[count]);
				
				canButton.setDisable(false);
				cantButton.setDisable(false);
				
				messageText.setText("소리가 들리신다면, O를 선택해주세요. (" + hertz[count] + "Hz)");
			}		
		}, 1000);
				
	}
	
	public void cantAction() {
		checkModel.setAudioFrequency(hertz[count]);
		confirmChecking();
	}
	
	public void helpAction () {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Audio Frecuency");
		alert.setHeaderText("Help");
		alert.setContentText("이어폰을 착용 후 조용한 환경에서 검사를 진행합니다. 주파수가 들린다면 'O'버튼을, 들리지 않는다면 'X'버튼을 선택하여 검사를 진행합니다.");
		alert.showAndWait();
	}
}
