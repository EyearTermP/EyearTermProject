package com.everychecking.model.data;

import javafx.scene.control.ListCell;

public class ResultRectCell extends ListCell<Result> {
	@Override
	public void updateItem(Result item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			if (item.getUser() == null) {
				this.setText("������ �˻糯¥ �̸� / �¿� �÷� / ���Ͱ˻� / ���ð˻� / ��û ���ļ� �뿪 / �¿� û���˻�");
				return;
			}
			
			String left = item.getVisualAcuity().getLeft()!=null?("��"+item.getVisualAcuity().getLeft()):"No Data";
			String right = item.getVisualAcuity().getRight()!=null?("��"+item.getVisualAcuity().getRight()):"No Data";
			String visualAcuity = (item.getVisualAcuity().getLeft()==null&&item.getVisualAcuity().getRight()==null)?"No Data":left+" "+right;
			String colorBlindness = item.isColorBlindness()!=null?(item.isColorBlindness()?"������":"����"):"No Data";
			String astigmatism = item.isAstigmatism()!=null?(item.isAstigmatism()?"������":"����"):"No Data";
			String audioFrequency = item.getAudioFrequency()!=null?item.getAudioFrequency() + "Hz(" + getAgeLayer(item.getAudioFrequency()) + ")":"No Data";
			Integer dysacousisValue = item.isDysacousis();
			String dysacousis = dysacousisValue!=null?dysacousisValue==0?"����":( ((dysacousisValue&2)==2?" ��":"") + ((dysacousisValue&1)==1?" ��":"") + " �̻�"):"No Data";

			this.setText(item.getCheckDay() + " " + item.getUser().getName() + " / " + visualAcuity + " / " + colorBlindness + " / " + astigmatism + " / " + audioFrequency + " / " + dysacousis);
		}
	}
	
	private String getAgeLayer(int audioFrequency) {
		String result = null;
		
		if (audioFrequency >= 20000) {
			result = "0 - 9";
		} else if (audioFrequency >= 19000) {
			result = "10 - 14";
		} else if (audioFrequency >= 18000) {
			result = "15 - 19";
		} else if (audioFrequency >= 17000) {
			result = "20 - 24";
		} else if (audioFrequency >= 16000) {
			result = "25 - 29";
		} else if (audioFrequency >= 15000) {
			result = "30 - 39";
		} else if (audioFrequency >= 12000) {
			result = "40 - 49";
		} else if (audioFrequency >= 10000) {
			result = "50 - 69";
		} else {
			result = "70 -";
		}
		
		return result;
	}
}
