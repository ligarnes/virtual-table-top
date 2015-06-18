package net.alteiar.ui.stage;

import java.util.HashMap;
import java.util.Map;

public class StageManager {

	private static StageManager INSTANCE = new StageManager();

	public static StageManager getInstance() {

		return INSTANCE;
	}

	private final Map<String, StageController> stages;

	public StageManager() {

		stages = new HashMap<String, StageController>();
	}

	public boolean stageExist(String viewName) {

		return stages.get(viewName) != null;
	}

	public void showStage(String viewName) {

		StageController stage = stages.get(viewName);

		if (stage.getStage().isShowing()) {

			stage.getStage().toFront();
		} else {

			stage.getStage().show();
		}
	}

	public synchronized void removeStage(String viewName) {

	}

	public void addStage(final String viewName, StageController stage) {

		stage.initialize();
		stage.getStage().setOnCloseRequest(evt -> {
			removeStage(viewName);
		});

		this.stages.put(viewName, stage);
	}
}
