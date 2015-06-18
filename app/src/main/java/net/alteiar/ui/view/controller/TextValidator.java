package net.alteiar.ui.view.controller;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class TextValidator implements ChangeListener<String> {

	private final ChangeListener<String> listener;

	private String previousValue;

	public TextValidator(ChangeListener<String> listener) {

		this.listener = listener;
	}

	@Override
	public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {

		if (newValue.equals(this.previousValue)) {

			this.previousValue = null;
			return;
		}

		if (accept(newValue)) {

			listener.changed(obs, oldValue, newValue);
		} else {

			this.previousValue = oldValue;
			((StringProperty) obs).setValue(oldValue);
		}
	}

	public abstract boolean accept(String newValue);

}
