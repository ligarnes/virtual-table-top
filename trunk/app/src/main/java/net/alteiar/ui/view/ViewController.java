package net.alteiar.ui.view;

import javafx.scene.Parent;
import net.alteiar.ui.stage.StageController;

public abstract class ViewController {

	private StageController stage;

	public ViewController() {
	}

	public final StageController getStage() {

		return stage;
	}

	public final void setStage(StageController stage) {

		this.stage = stage;
	}

	public abstract Parent loadView();
}
