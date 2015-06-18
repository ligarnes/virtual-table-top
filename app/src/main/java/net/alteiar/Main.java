package net.alteiar;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.alteiar.basictypes.CombatTracker;
import net.alteiar.basictypes.Faction;
import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.task.combat.AddUnitToCombat;
import net.alteiar.ui.stage.StageController;
import net.alteiar.ui.stage.StageManager;
import net.alteiar.ui.view.MainView;

public class Main extends Application {

	public static void main(String[] args) throws IOException {

		Application.launch();
	}

	@Override
	public void init() {

		PlatformContext.getInstance().initialize();

		DaoFactorySingleton.getInstance().setRootDir("./datas");
		DaoFactorySingleton.getInstance().initialize();
	}

	@Override
	public void stop() {

		PlatformContext.getInstance().shutdown();
	}

	private void initData() throws DataException, InterruptedException {
		Unit character = new Unit();
		character.setName("abc");
		character.setWounds(5);
		character.setHealthPoint(12);
		character.setArmorClass(18);
		character
				.setImage("file:C:\\Users\\ligarnes\\Desktop\\cartes\\0946cc77d943ac01fe09a330975d3e6d.media.100x155.jpg");

		Unit c2 = new Unit();
		c2.setName("gobelin");
		c2.setWounds(5);
		c2.setHealthPoint(12);
		c2.setArmorClass(18);
		c2.setImage("file:C:\\Users\\ligarnes\\Desktop\\cartes\\0946cc77d943ac01fe09a330975d3e6d.media.100x155.jpg");

		CombatTracker combatTracker = new CombatTracker();

		DaoFactorySingleton.getInstance().getCombatTrackerDao().insert(combatTracker);

		AddUnitToCombat addAction1 = new AddUnitToCombat(combatTracker, character, 12, Faction.NEUTRAL);

		AddUnitToCombat addAction2 = new AddUnitToCombat(combatTracker, c2, 10, Faction.ALLY);

		PlatformContext.getInstance().getTaskEngine().enqueue(addAction1);
		Thread.sleep(2000);
		PlatformContext.getInstance().getTaskEngine().enqueue(addAction2);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/**
		 * MainView mainView = new MainView(); Scene scene = new
		 * Scene(mainView.loadView());
		 * 
		 * primaryStage.setScene(scene);
		 */
		primaryStage.setTitle("Combat Tracker");
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(200);
		primaryStage.setOnCloseRequest(we -> Platform.exit());

		StageManager.getInstance().addStage("main", new StageController(primaryStage, new MainView()));

		StageManager.getInstance().showStage("main");
	}
}
