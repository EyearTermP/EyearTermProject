package com.everychecking.voce;

import com.everychecking.library.Callback;
import com.everychecking.library.Timer;

// 음성인식 모듈을 연동하는 클래스이다.
public class Listener {
	
	private static boolean wasInitialize = false;
	
	// 음성인식 모듈을 초기화한다.
	public static void initailzing() {
		if (wasInitialize)
			return;
		
		voce.SpeechInterface.init("lib", false, true, "./grammar", "digits");
		
		wasInitialize = true;
	}
	
	public static Thread listenAWord(Callback prepareCallback, Callback listenCallback) {
		return Timer.start(new Callback() {
			@Override
			public void callback(Object objct) {
				prepareCallback.callback(null);
				
				boolean quit = false;
				
				while (!quit) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {
						while (voce.SpeechInterface.getRecognizerQueueSize() > 0) {
							String s = voce.SpeechInterface.popRecognizedString().split(" ")[0];
							
							listenCallback.callback(s);
	
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, 1500);
	}

	// 음성을 인식하는 메서드이다.
	public static void listen() {
		System.out.println("This is a speech recognition test. " + "Speak digits from 0-9 into the microphone. "
				+ "Speak 'quit' to quit.");

		// Quit 을 말했는지를 판별하는 변수다.
		boolean quit = false;
		
		// Quit 을 말하기 전까지 반복한다.
		while (!quit) {
			try {
				// Thread 를 200ms 동안 sleep 시킨다.앞선 잡음을 조금이나마 제거하기 위함이다.
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 음성인식을 한 Queue 에 데이터가 남아있음을 확인한다.
			while (voce.SpeechInterface.getRecognizerQueueSize() > 0) {
				// 음성인식을 한 문자열을 Pop 한다.
				String s = voce.SpeechInterface.popRecognizedString();

				// Check if the string contains 'quit'.
				if (-1 != s.indexOf("quit")) {
					quit = true;
				}

				System.out.println("You said: " + s);
			}
		}

		// 음성인식 인터페이스를 제거한다.
		voce.SpeechInterface.destroy();
	}
}
