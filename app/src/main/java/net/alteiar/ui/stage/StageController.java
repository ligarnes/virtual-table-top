package net.alteiar.ui.stage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.alteiar.ui.exception.FXMLException;
import net.alteiar.ui.view.FxmlViewController;
import net.alteiar.ui.view.ViewController;

public class StageController {

	private final Stage stage;

	private ViewController viewController;

	public StageController(ViewController controller) {

		this(new Stage(), controller);
	}

	public StageController(Stage stage, ViewController controller) {

		this.stage = stage;
		viewController = controller;
	}

	public Stage getStage() {

		return stage;
	}

	public void replaceRoot(FxmlViewController viewController) {

		this.viewController = viewController;

		try {
			stage.getScene().setRoot(viewController.loadView());
			viewController.setStage(this);
		} catch (FXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() {

		// stage.initStyle(StageStyle.UNDECORATED);

		try {
			stage.setScene(new Scene(viewController.loadView()));
			viewController.setStage(this);
		} catch (FXMLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
