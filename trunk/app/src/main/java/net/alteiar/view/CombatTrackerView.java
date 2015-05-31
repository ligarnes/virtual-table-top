package net.alteiar.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.alteiar.combattracker.CombatTracker;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataModificationAdapter;

public class CombatTrackerView implements Initializable {

	private final Long combatTrackerId;

	@FXML
	private VBox unitsView;

	public CombatTrackerView(CombatTracker combatTracker) {
		combatTrackerId = combatTracker.getId();
	}

	public Node loadView() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/view/combatView.fxml"));
		fxmlLoader.setController(this);

		Node currentView = fxmlLoader.load();

		ScrollPane scroll = new ScrollPane(currentView);

		return scroll;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		DaoFactorySingleton.getInstance().getCombatTrackerDao()
				.addDataListener(new DataModificationAdapter(combatTrackerId) {

					@Override
					protected void dataDeleted() {
					}

					@Override
					protected void dataChanged() {
						Platform.runLater(() -> revalidate());
					}
				});
		revalidate();

	}

	private void revalidate() {

		unitsView.getChildren().clear();

		CombatTracker combatTracker = null;

		try {
			combatTracker = DaoFactorySingleton.getInstance()
					.getCombatTrackerDao().find(combatTrackerId);
		} catch (DataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (combatTracker != null) {

			for (Long characterId : combatTracker.getCombatUnitId()) {

				CombatTrackerCharacterView view = new CombatTrackerCharacterView(
						characterId);

				try {

					Node node = view.loadView();
					unitsView.getChildren().add(node);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
