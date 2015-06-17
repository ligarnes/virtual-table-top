package net.alteiar.ui.view.unit;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.task.unit.ChangeUnit;
import net.alteiar.task.unit.ChangeUnitImage;
import net.alteiar.ui.view.FxmlViewController;
import net.alteiar.ui.view.controller.TextController;
import net.alteiar.ui.view.controller.TextPatternValidator;

public class UnitView extends FxmlViewController implements Initializable {

	@FXML
	private ImageView imageUnit;

	@FXML
	private TextField txtFieldName;

	@FXML
	private TextField txtAc;

	@FXML
	private TextField txtFlatFootedAc;

	@FXML
	private TextField txtTouchAc;

	@FXML
	private TextField txtCMD;

	@FXML
	private TextField txtHp;

	@FXML
	private TextField txtWounds;

	private final long characterId;

	public UnitView(Long characterId) {

		this.characterId = characterId;

		UnitDao dao = DaoFactorySingleton.getInstance().getUnitDao();

		dao.addDataListener(new DataModificationAdapter(characterId) {

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

	public Unit getUnit() {

		UnitDao dao = DaoFactorySingleton.getInstance().getUnitDao();

		try {
			return dao.find(characterId);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		revalidate();

		TextController controller = new TextController((observable, oldValue, newValue) -> {
			edit();
		});

		txtFieldName.textProperty().addListener(controller);

		TextPatternValidator editorListener = new TextPatternValidator(controller, "[0-9]*");

		txtHp.textProperty().addListener(editorListener);
		txtWounds.textProperty().addListener(editorListener);

		txtAc.textProperty().addListener(editorListener);
		txtTouchAc.textProperty().addListener(editorListener);
		txtFlatFootedAc.textProperty().addListener(editorListener);

		txtCMD.textProperty().addListener(editorListener);

		imageUnit.setOnMouseClicked(event -> {
			chooseImage();
		});
	}

	private void revalidate() {

		final Unit character = getUnit();

		if (character != null) {

			if (character.getImage() != null) {
				imageUnit.setImage(new Image(character.getImage()));
			}

			txtFieldName.setText(character.getName());

			txtAc.setText(String.valueOf(character.getArmorClass()));
			txtFlatFootedAc.setText(String.valueOf(character.getArmorClassFlatFooted()));
			txtTouchAc.setText(String.valueOf(character.getArmorClassTouch()));

			txtHp.setText(Integer.valueOf(character.getHealthPoint()).toString());
			txtWounds.setText(Integer.valueOf(character.getWounds()).toString());
		}
	}

	private void edit() {

		Unit unit = getUnit();
		unit.setName(txtFieldName.getText());
		unit.setArmorClass(Integer.valueOf(txtAc.getText()));
		unit.setArmorClassFlatFooted(Integer.valueOf(txtFlatFootedAc.getText()));
		unit.setArmorClassTouch(Integer.valueOf(txtTouchAc.getText()));

		unit.setHealthPoint(Integer.valueOf(txtHp.getText()));
		unit.setWounds(Integer.valueOf(txtWounds.getText()));

		PlatformContext.getInstance().getTaskEngine().enqueue(new ChangeUnit(unit));
	}

	private void chooseImage() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir un image");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			PlatformContext.getInstance().getTaskEngine()
					.enqueue(new ChangeUnitImage(characterId, "file:" + file.getAbsolutePath()));
		}
	}
}
