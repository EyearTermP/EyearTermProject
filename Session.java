package com.everychecking.session;

import com.everychecking.model.check.CheckModel;
import com.everychecking.model.data.User;

public class Session {
	
	private static User user = null;
	
	private static CheckModel checkModel = null;
	
	public static void clearSession() {
		user = null;
		checkModel = null;
	}

	public static User getUser() {
		return user;
	}
	public static CheckModel getCheckModel() {
		if (checkModel == null) {
			checkModel = new CheckModel();
		}
		
		return checkModel;
	}

	public static void setUser(User user) {
		Session.user = user;
	}
	public static void setCheckModel(CheckModel checkModel) {
		Session.checkModel = checkModel;
	}
}
