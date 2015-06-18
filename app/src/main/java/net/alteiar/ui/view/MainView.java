package net.alteiar.ui.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.alteiar.basictypes.CombatTracker;
import net.alteiar.ui.stage.InternalStageController;
import net.alteiar.ui.stage.StageController;
import net.alteiar.ui.view.combat.CombatTrackerView;
import net.alteiar.ui.view.unit.UnitListView;

public class MainView extends FxmlViewController implements Initializable {

	@FXML
	private Button btnOpenUnits;

	@FXML
	private Button btnOpenCombatTracker;

	public MainView() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// btnAddUnit.addEventHandler(MouseEvent.CLICK, arg1);

		btnOpenCombatTracker.setOnMouseClicked(event -> {
			openTracker();
		});

		btnOpenUnits.setOnMouseClicked(event -> {
			openUnits();
		});

		// addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler(target,
		// action,
		// eventPropertyName, listenerMethodName));
		revalidate();
	}

	private void openUnits() {

		Stage stage = new Stage();
		stage.setTitle("Character");

		long campaignId = 0L;

		StageController stageController = new InternalStageController(stage, new UnitListView(campaignId));
		stageController.show();
	}

	private void openTracker() {

		Stage stage = new Stage();
		stage.setTitle("Combat Tracker");

		CombatTracker cbTracker = new CombatTracker();
		cbTracker.setId(0L);

		StageController stageController = new InternalStageController(stage, new CombatTrackerView(cbTracker));
		stageController.show();
	}

	private void revalidate() {

	}

}
