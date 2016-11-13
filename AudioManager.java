package com.everychecking.manager.resource;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.everychecking.resource.Audio;

import javafx.scene.media.AudioClip;

// Audio 로드 및 출력을 담당 할 매니저이다. 매니저의 공통 분모를 가진 ResourceManager 를 상속받는다.
public class AudioManager extends ResourceManager {

	public static Audio getAudio(URL url) {
		try {
			return new Audio(url);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Audio getAudio(String fileName) {
		try {
			URL resource = FxmlManager.class.getClassLoader().getResource("com/everychecking/audio/" + fileName);
			String externalForm = resource.toExternalForm();
			return new Audio(externalForm);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void speak(Audio audio) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				audio.getAudioClip().play();
			}
		}).start();
	}
	
	public static void speak(Audio audio, double balance) {
		AudioClip audioClip = audio.getAudioClip();
		audioClip.setBalance(balance);
		new Thread(new Runnable() {
			@Override
			public void run() {
				audioClip.play();
				audioClip.setBalance(0);
			}
		}).start();
	}
	
}
