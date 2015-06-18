package net.alteiar.ui.view.controller;

import javafx.beans.value.ChangeListener;

public class TextPatternValidator extends TextValidator {

	private final String pattern;

	public TextPatternValidator(ChangeListener<String> listener, String pattern) {

		super(listener);
		this.pattern = pattern;
	}

	@Override
	public boolean accept(String newValue) {

		return newValue.matches(pattern);
	}

}
