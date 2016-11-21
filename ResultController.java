package com.everychecking.controller;

import com.everychecking.database.Database;
import com.everychecking.model.data.Result;
import com.everychecking.model.data.ResultRectCell;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

// Result FXML �� ��Ʈ�ѷ�, ��� ��Ʈ�ѷ��� ����и� ���� Controller �� ��ӹ޴´�.
public class ResultController extends Controller {

	@FXML
	private JFXListView<Result> list;

	// FXML �� �ʱ�ȭ�� ���� �� �� ȣ��Ǵ� �޼����̴�.
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
