package com.everychecking.model.data;

import javafx.scene.control.ListCell;

public class ResultRectCell extends ListCell<Result> {
	@Override
	public void updateItem(Result item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			if (item.getUser() == null) {
				this.setText("마지막 검사날짜 이름 / 좌우 시력 / 색맹검사 / 난시검사 / 가청 주파수 대역 / 좌우 청각검사");
				return;
			}
			
			String left = item.getVisualAcuity().getLeft()!=null?("좌"+item.getVisualAcuity().getLeft()):"No Data";
			String right = item.getVisualAcuity().getRight()!=null?("우"+item.getVisualAcuity().getRight()):"No Data";
			String visualAcuity = (item.getVisualAcuity().getLeft()==null&&item.getVisualAcuity().getRight()==null)?"No Data":left+" "+right;
			String colorBlindness = item.isColorBlindness()!=null?(item.isColorBlindness()?"비정상":"정상"):"No Data";
			String astigmatism = item.isAstigmatism()!=null?(item.isAstigmatism()?"비정상":"정상"):"No Data";
			String audioFrequency = item.getAudioFrequency()!=null?item.getAudioFrequency() + "Hz(" + getAgeLayer(item.getAudioFrequency()) + ")":"No Data";
			Integer dysacousisValue = item.isDysacousis();
			String dysacousis = dysacousisValue!=null?dysacousisValue==0?"정상":( ((dysacousisValue&2)==2?" 좌":"") + ((dysacousisValue&1)==1?" 우":"") + " 이상"):"No Data";

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
