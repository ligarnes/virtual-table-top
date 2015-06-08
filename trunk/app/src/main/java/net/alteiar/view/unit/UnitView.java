package net.alteiar.view.unit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import net.alteiar.basictypes.Unit;
import net.alteiar.combat.task.ChangeImage;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.observer.DataModificationAdapter;

public class UnitView implements Initializable {

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

	public Node loadView() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/unit/unitView.fxml"));
		fxmlLoader.setController(this);

		return fxmlLoader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		revalidate();
	}

	private void revalidate() {

		final Unit character = getUnit();

		if (character != null) {

			imageUnit.setImage(new Image(character.getImage()));
			imageUnit.setOnMouseClicked(event -> {
				chooseImage();
			});

			txtFieldName.setText(character.getName());

			txtHp.setText(Integer.valueOf(character.getHealthPoint()).toString());
			txtWounds.setText(Integer.valueOf(character.getCurrentDamage()).toString());
		}
	}

	private void chooseImage() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir un image");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			PlatformContext.getInstance().getTaskEngine()
					.enqueue(new ChangeImage(characterId, "file:" + file.getAbsolutePath()));
		}
	}
}
