package com.everychecking.model.data;

import javafx.scene.control.ListCell;

public class UserRectCell extends ListCell<User> {
	@Override
	public void updateItem(User item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty)
			this.setText(item.getName() + " / " + item.getAge());
	}
}