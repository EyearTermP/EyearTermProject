package com.everychecking.model.check;

import com.everychecking.database.Database;

// 검사모델의 공통분모를 모은 CheckModel 이다. 검사 후 필요한 InsertModel 을 상속받는다.
public class CheckModel extends InsertModel {
	
	// Insert data
	public void insertData () {
		Database.insertResult(getVisualAcuity(), getIsColorBlindness(), getIsAstigmatism(), getAudioFrequency(), getIsDysacousis());
	}
}
