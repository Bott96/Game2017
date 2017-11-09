package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;
import PutEditorOnFile.SaveLoadOnFile;
import artificialIntelligence.ArtificialIntelligence;
import mondo3D.World;

public class DifficoltaScreen implements Screen {

	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 60;
	private static final int DIFFICOLTAF_BUTTON_Y = 290;
	private static final int DIFFICOLTAM_BUTTON_Y = 290;
	private static final int DIFFICOLTAD_BUTTON_Y = 290;

	private static final int EXIT_BUTTON_WIDTH = 150;
	private static final int EXIT_BUTTON_HEIGHT = 50;
	private static final int EXIT_BUTTON_Y = 50;

	private static final int UNDO_BUTTON_WIDTH = 80;
	private static final int UNDO_BUTTON_HEIGHT = 80;
	private static final int UNDO_BUTTON_Y = 50;

	Texture Sfondo;
	Texture ExitInactive;
	Texture ExitActive;
	Texture UndoInactive;
	Texture UndoActive;
	Texture FacileInactive;
	Texture FacileActive;
	Texture MedioInactive;
	Texture MedioActive;
	Texture DifficileInactive;
	Texture DifficileActive;

	SaveLoadOnFile OperazioniSulFile = new SaveLoadOnFile();
	final AttacksAndConquers attacksAndConquers;
	MainMenuScreen mainMenuScreen;
	private boolean touch;

	public DifficoltaScreen(final AttacksAndConquers attacksAndConquers, MainMenuScreen main, boolean touch) {

		this.attacksAndConquers = attacksAndConquers;
		this.touch=touch;
		mainMenuScreen=main;
		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		FacileActive = Risorse.assets.get(Risorse.FacileActive);
		FacileInactive = Risorse.assets.get(Risorse.FacileInactive);
		MedioActive = Risorse.assets.get(Risorse.MedioActive);
		MedioInactive = Risorse.assets.get(Risorse.MedioInactive);
		DifficileActive = Risorse.assets.get(Risorse.DifficileActive);
		DifficileInactive = Risorse.assets.get(Risorse.DifficileInactive);
		ExitActive = Risorse.assets.get(Risorse.ExitActive);
		ExitInactive = Risorse.assets.get(Risorse.ExitInactive);
		UndoActive = Risorse.assets.get(Risorse.UndoActive);
		UndoInactive = Risorse.assets.get(Risorse.UndoInactive);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		attacksAndConquers.batch.begin();

		attacksAndConquers.scrolling.updateAndRender(delta, attacksAndConquers.batch);

		// Facile button
		int x = 100;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < DIFFICOLTAF_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > DIFFICOLTAF_BUTTON_Y) {
			attacksAndConquers.batch.draw(FacileActive, x, DIFFICOLTAF_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				GameManager.DifficoltaDifficile = false;
				GameManager.DifficoltaFacile = true;
				GameManager.DifficoltaMedio = false;
				if(!touch)
					OperazioniSulFile.CaricaLivelloDiDefault();
				new ArtificialIntelligence();

				attacksAndConquers.disegnoName = false;
				attacksAndConquers.setScreen(new World(attacksAndConquers));
			}
		} else {
			attacksAndConquers.batch.draw(FacileInactive, x, DIFFICOLTAF_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// medio
		x = Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH / 2;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < DIFFICOLTAM_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > DIFFICOLTAM_BUTTON_Y) {
			attacksAndConquers.batch.draw(MedioActive, x, DIFFICOLTAM_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				
				if(!touch)
					OperazioniSulFile.CaricaLivelloDiDefault();
				
				GameManager.DifficoltaDifficile = false;
				GameManager.DifficoltaFacile = false;
				GameManager.DifficoltaMedio = true;
				new ArtificialIntelligence();

				attacksAndConquers.disegnoName = false;
				attacksAndConquers.setScreen(new World(attacksAndConquers));
			}
		} else {
			attacksAndConquers.batch.draw(MedioInactive, x, DIFFICOLTAM_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Difficile button
		x = Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH -BUTTON_WIDTH;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < DIFFICOLTAD_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > DIFFICOLTAD_BUTTON_Y) {
			attacksAndConquers.batch.draw(DifficileActive, x, DIFFICOLTAD_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				
				if(!touch)
					OperazioniSulFile.CaricaLivelloDiDefault();

				GameManager.DifficoltaDifficile = true;
				GameManager.DifficoltaFacile = false;
				GameManager.DifficoltaMedio = false;
				new ArtificialIntelligence();

				attacksAndConquers.disegnoName = false;
				attacksAndConquers.setScreen(new World(attacksAndConquers));
			}
		} else {
			attacksAndConquers.batch.draw(DifficileInactive, x, DIFFICOLTAD_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Undo button
		x = UNDO_BUTTON_WIDTH + 10;
		if (Gdx.input.getX() < x + UNDO_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < UNDO_BUTTON_Y + UNDO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > UNDO_BUTTON_Y) {
			attacksAndConquers.batch.draw(UndoActive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(new SinglePlayerScreen(attacksAndConquers,mainMenuScreen));
			}
		} else {
			attacksAndConquers.batch.draw(UndoInactive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
		}

		// Exit button
		if (Gdx.input.getX() < Gdx.graphics.getWidth() - 10
				&& Gdx.input.getX() > Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH - 10
				&& attacksAndConquers.height - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > EXIT_BUTTON_Y) {
			attacksAndConquers.batch.draw(ExitActive, Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH - 10, EXIT_BUTTON_Y,
					EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				Risorse.assets.dispose();
				Gdx.app.exit();
			}
		} else {
			attacksAndConquers.batch.draw(ExitInactive, Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH - 10, EXIT_BUTTON_Y,
					EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}

		attacksAndConquers.batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {

	}

}
