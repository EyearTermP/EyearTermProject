package com.everychecking.voce;

import com.everychecking.library.Callback;
import com.everychecking.library.Timer;

// �����ν� ����� �����ϴ� Ŭ�����̴�.
public class Listener {
	
	private static boolean wasInitialize = false;
	
	// �����ν� ����� �ʱ�ȭ�Ѵ�.
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

	// ������ �ν��ϴ� �޼����̴�.
	public static void listen() {
		System.out.println("This is a speech recognition test. " + "Speak digits from 0-9 into the microphone. "
				+ "Speak 'quit' to quit.");

		// Quit �� ���ߴ����� �Ǻ��ϴ� ������.
		boolean quit = false;
		
		// Quit �� ���ϱ� ������ �ݺ��Ѵ�.
		while (!quit) {
			try {
				// Thread �� 200ms ���� sleep ��Ų��.�ռ� ������ �����̳��� �����ϱ� �����̴�.
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// �����ν��� �� Queue �� �����Ͱ� ���������� Ȯ���Ѵ�.
			while (voce.SpeechInterface.getRecognizerQueueSize() > 0) {
				// �����ν��� �� ���ڿ��� Pop �Ѵ�.
				String s = voce.SpeechInterface.popRecognizedString();

				// Check if the string contains 'quit'.
				if (-1 != s.indexOf("quit")) {
					quit = true;
				}

				System.out.println("You said: " + s);
			}
		}

		// �����ν� �������̽��� �����Ѵ�.
		voce.SpeechInterface.destroy();
	}
}
