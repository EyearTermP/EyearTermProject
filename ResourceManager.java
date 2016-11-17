package com.everychecking.manager.resource;

import java.io.File;

// 매니저의 공통분모의 요소를 가진 클래스이다.
public class ResourceManager {
	// TODO
	public File getFile(String filePath) {
		return new File(filePath);
	}
}
