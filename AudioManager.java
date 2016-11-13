package com.everychecking.manager.resource;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.everychecking.resource.Audio;

import javafx.scene.media.AudioClip;

// Audio �ε� �� ����� ��� �� �Ŵ����̴�. �Ŵ����� ���� �и� ���� ResourceManager �� ��ӹ޴´�.
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
