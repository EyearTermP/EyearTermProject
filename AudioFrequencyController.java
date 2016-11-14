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

// Audio Frequency FXML �� ��Ʈ�ѷ�, ��Ʈ�ѷ��� ����и� ���� CheckController �� ��ӹ޴´�.
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
	
	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
	@FXML
	void initialize() {
		checkModel = Session.getCheckModel();
		
		count = -1;
		
		messageText.setText("�غ� �Ǽ̴ٸ� O��ư�� �����ּ���.");
		
		cantButton.setDisable(true);
	}
	
	public void canAction() {
				
		messageText.setText("��� �� �Ҹ��� ��µ˴ϴ�.");
		
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
				
				messageText.setText("�Ҹ��� �鸮�Ŵٸ�, O�� �������ּ���. (" + hertz[count] + "Hz)");
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
		alert.setContentText("�̾����� ���� �� ������ ȯ�濡�� �˻縦 �����մϴ�. ���ļ��� �鸰�ٸ� 'O'��ư��, �鸮�� �ʴ´ٸ� 'X'��ư�� �����Ͽ� �˻縦 �����մϴ�.");
		alert.showAndWait();
	}
}
