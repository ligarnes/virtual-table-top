package net.alteiar.ui.view.controller;

import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class TextController implements ChangeListener<String> {

	private static final long DEFAULT_UPDATE_FREQUENCY = 1000L;

	private final ChangeListener<String> listener;
	private final long updateFrequency;

	private ObservableValue<? extends String> observable;
	private String oldValue;
	private String newValue;

	private Timer timer;

	public TextController(ChangeListener<String> listener) {
		this(listener, DEFAULT_UPDATE_FREQUENCY);
	}

	public TextController(ChangeListener<String> listener, long updateFrequency) {
		this.listener = listener;

		this.updateFrequency = updateFrequency;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

		this.newValue = newValue;

		if (timer == null) {

			timer = new Timer(true);
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					notifyChange();
				}
			}, updateFrequency);
		}
	}

	private void notifyChange() {

		listener.changed(observable, oldValue, newValue);
		timer.cancel();
		timer = null;
	}
}
