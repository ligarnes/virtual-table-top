package net.alteiar.view.combat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.alteiar.basictypes.CombatTracker;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.view.exception.FXMLException;
import net.alteiar.view.fxml.FxmlLoader;

public class CombatTrackerView implements Initializable {

	private final Long combatTrackerId;

	@FXML
	private VBox unitsView;

	public CombatTrackerView(CombatTracker combatTracker) {
		combatTrackerId = combatTracker.getId();
	}

	public Node loadView() throws FXMLException {

		Node currentView = FxmlLoader.getInstance().loadView("/view/combat/combatView.fxml", this);

		return new ScrollPane(currentView);
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

	private CombatTracker getCombatTracker() {

		CombatTracker combatTracker = null;
		try {
			combatTracker = DaoFactorySingleton.getInstance().getCombatTrackerDao().find(combatTrackerId);
		} catch (DataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return combatTracker;
	}

	private void revalidate() {

		unitsView.getChildren().clear();

		CombatTracker combatTracker = getCombatTracker();

		if (combatTracker != null) {

			for (Long characterId : combatTracker.getCombatUnitId()) {

				CombatTrackerCharacterView view = new CombatTrackerCharacterView(characterId);

				try {

					Node node = view.loadView();
					unitsView.getChildren().add(node);
				} catch (FXMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
