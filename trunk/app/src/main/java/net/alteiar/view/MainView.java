package net.alteiar.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import net.alteiar.basictypes.CombatTracker;
import net.alteiar.view.combat.CombatTrackerView;
import net.alteiar.view.exception.FXMLException;
import net.alteiar.view.unit.UnitSelectionView;

public class MainView implements Initializable {

	@FXML
	private Button btnOpenUnits;

	@FXML
	private Button btnOpenCombatTracker;

	public MainView() {
	}

	public Node loadView() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
		fxmlLoader.setController(this);

		Node currentView = fxmlLoader.load();

		ScrollPane scroll = new ScrollPane(currentView);

		return scroll;
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
		// stage.initStyle(StageStyle.UNDECORATED);
		UnitSelectionView unitSelectionView = new UnitSelectionView(campaignId);

		try {
			stage.setScene(new Scene((Parent) unitSelectionView.loadView()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Fill stage with content
		stage.show();

	}

	private void openTracker() {

		Stage stage = new Stage();
		stage.setTitle("Combat Tracker");

		CombatTracker cbTracker = new CombatTracker();
		cbTracker.setId(0L);
		CombatTrackerView tracker = new CombatTrackerView(cbTracker);

		try {
			stage.setScene(new Scene((Parent) tracker.loadView()));
		} catch (FXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Fill stage with content
		stage.show();
	}

	private void revalidate() {

	}

}
