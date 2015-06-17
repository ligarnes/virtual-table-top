package net.alteiar.ui.stage;

import javafx.stage.Stage;
import net.alteiar.ui.view.FxmlViewController;

public class InternalStageController extends StageController {

	public InternalStageController(FxmlViewController controller) {
		super(controller);
	}

	public InternalStageController(Stage stage, FxmlViewController controller) {
		super(stage, controller);
	}

}
