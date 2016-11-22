package com.everychecking.resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.AudioClip;

public class Audio {

	private AudioClip audioClip;

	public Audio(String filePath) throws MalformedURLException, UnsupportedAudioFileException, IOException {
		this(new AudioClip(filePath));
	}

	public Audio(URL url) throws UnsupportedAudioFileException, IOException {
		this(new AudioClip(url.toExternalForm()));
	}
	
	public Audio(AudioClip audioClip) {
		this.audioClip = audioClip;
	}

	public AudioClip getAudioClip() {
		return audioClip;
	}

	public void setAudioClip(AudioClip audioClip) {
		this.audioClip = audioClip;
	}
}
