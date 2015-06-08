package net.alteiar.view.combat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.alteiar.basictypes.CombatUnit;
import net.alteiar.basictypes.Faction;
import net.alteiar.basictypes.Unit;
import net.alteiar.combat.task.ChangeFaction;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.combat.CombatUnitDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.view.exception.FXMLException;
import net.alteiar.view.fxml.FxmlLoader;

public class CombatTrackerCharacterView implements Initializable {

	@FXML
	private ImageView currentTurn;
	@FXML
	private ImageView characterImage;
	@FXML
	private Label characterName;
	@FXML
	private TextField characterInit;
	@FXML
	private TextField characterHp;
	@FXML
	private TextField characterDamage;
	@FXML
	private ImageView characterFaction;

	private final long characterCombatId;

	public CombatTrackerCharacterView(Long characterId) {

		this.characterCombatId = characterId;

		CombatUnitDao dao = DaoFactorySingleton.getInstance().getCombatUnitDao();

		dao.addDataListener(new DataModificationAdapter(characterCombatId) {

			@Override
			protected void dataDeleted() {

				Platform.runLater(() -> revalidate());
			}

			@Override
			protected void dataChanged() {

				Platform.runLater(() -> revalidate());
			}
		});
	}

	public CombatUnit getCombatUnit() {

		CombatUnitDao dao = DaoFactorySingleton.getInstance().getCombatUnitDao();

		try {
			return dao.find(characterCombatId);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Unit getUnit(CombatUnit unit) {

		UnitDao dao = DaoFactorySingleton.getInstance().getUnitDao();

		try {
			return dao.find(unit.getUnitId());
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Node loadView() throws FXMLException {

		return FxmlLoader.getInstance().loadView("/view/combat/characterView.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		revalidate();
	}

	private void revalidate() {

		final CombatUnit characterCombat = getCombatUnit();

		if (characterCombat != null) {

			Unit character = getUnit(characterCombat);

			characterImage.setImage(new Image(character.getImage()));
			characterName.setText(character.getName());

			Integer init = characterCombat.getInitiative();

			if (init == null) {

				characterInit.setText("");
			} else {

				characterInit.setText(init.toString());
			}
			characterHp.setText(Integer.valueOf(character.getHealthPoint()).toString());
			characterDamage.setText(Integer.valueOf(character.getCurrentDamage()).toString());

			characterFaction.setImage(getFactionImage(characterCombat.getFaction()));

			characterFaction.setOnMouseClicked(event -> {
				changeFaction(characterCombat);
			});

		}
	}

	private void changeFaction(CombatUnit combatUnit) {

		ChangeFaction changeFaction = new ChangeFaction(combatUnit, getNext(combatUnit.getFaction()));

		PlatformContext.getInstance().getTaskEngine().enqueue(changeFaction);
	}

	private Faction getNext(Faction current) {

		Faction[] all = Faction.values();
		int idx = (current.ordinal() + 1) % all.length;

		return all[idx];
	}

	private Image getFactionImage(Faction faction) {

		Image found = null;

		switch (faction) {
		case ALLY:
			found = new Image(getClass().getResourceAsStream("/icons/ct_faction_friend.png"));
			break;
		case ENNEMY:
			found = new Image(getClass().getResourceAsStream("/icons/ct_faction_foe.png"));
			break;
		case NEUTRAL:
			found = new Image(getClass().getResourceAsStream("/icons/ct_faction_neutral.png"));
			break;
		default:
			found = new Image(getClass().getResourceAsStream("/icons/ct_faction_neutral.png"));
			break;
		}
		return found;
	}
}
