package com.everychecking.controller;

import com.everychecking.database.Database;
import com.everychecking.model.data.Result;
import com.everychecking.model.data.ResultRectCell;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

// Result FXML 의 컨트롤러, 모든 컨트롤러의 공통분모를 모은 Controller 를 상속받는다.
public class ResultController extends Controller {

	@FXML
	private JFXListView<Result> list;

	// FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
	@FXML
	void initialize() {
		list.setCellFactory(new javafx.util.Callback<ListView<Result>, ListCell<Result>>() {
			@Override
			public ListCell<Result> call(ListView<Result> param) {
				return new ResultRectCell();
			}
		});
		
		list.getItems().add(new Result(null, null, null, null, null, null, null));
		Database.getResultList(list);
	}

}
