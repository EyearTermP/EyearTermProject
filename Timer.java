package com.everychecking.library;

public class Timer {

	public static Thread start(Callback callback, long time) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				long endTime = System.currentTimeMillis() + time;
				while (endTime > System.currentTimeMillis()) {}
				callback.callback(null);
			}
		});
		thread.start();
		return thread;
	}
}
