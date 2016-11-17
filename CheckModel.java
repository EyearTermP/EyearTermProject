package com.everychecking.model.check;

import com.everychecking.database.Database;

// �˻���� ����и� ���� CheckModel �̴�. �˻� �� �ʿ��� InsertModel �� ��ӹ޴´�.
public class CheckModel extends InsertModel {
	
	// Insert data
	public void insertData () {
		Database.insertResult(getVisualAcuity(), getIsColorBlindness(), getIsAstigmatism(), getAudioFrequency(), getIsDysacousis());
	}
}
