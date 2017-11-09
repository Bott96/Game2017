package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;
import PutEditorOnFile.SaveLoadOnFile;

public class SinglePlayerScreen implements Screen {

	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 60;
	private static final int DEFAULT_BUTTON_Y = 200;
	private static final int CUSTOM_BUTTON_Y = 200;

	private static final int EXIT_BUTTON_WIDTH = 100;
	private static final int EXIT_BUTTON_HEIGHT = 50;
	private static final int EXIT_BUTTON_Y = 50;

	private static final int UNDO_BUTTON_WIDTH = 80;
	private static final int UNDO_BUTTON_HEIGHT = 80;
	private static final int UNDO_BUTTON_Y = 50;

	final AttacksAndConquers attacksAndConquers;
	MainMenuScreen mainMenuScreen;

	SaveLoadOnFile OperazioniSulFile = new SaveLoadOnFile();

	private boolean touchCustom = false;

	Texture Sfondo;
	Texture DefaultInactive;
	Texture DefaultActive;
	Texture CustomInactive;
	Texture CustomActive;
	Texture ExitInactive;
	Texture ExitActive;
	Texture UndoInactive;
	Texture UndoActive;

	public SinglePlayerScreen(final AttacksAndConquers attacksAndConquers, MainMenuScreen main) {

		this.attacksAndConquers = attacksAndConquers;
		mainMenuScreen = main;

		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		DefaultActive = Risorse.assets.get(Risorse.DefaultActive);
		DefaultInactive = Risorse.assets.get(Risorse.DefaultInactive);
		CustomActive = Risorse.assets.get(Risorse.CustomActive);
		CustomInactive = Risorse.assets.get(Risorse.CustomInactive);
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

		// Default button
		int x = 100;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < DEFAULT_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > DEFAULT_BUTTON_Y) {
			attacksAndConquers.batch.draw(DefaultActive, x, DEFAULT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				touchCustom = false;
				attacksAndConquers.setScreen(new DifficoltaScreen(attacksAndConquers, mainMenuScreen, touchCustom));
			}
		} else {
			attacksAndConquers.batch.draw(DefaultInactive, x, DEFAULT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Custom button
		x = Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH * 5;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < CUSTOM_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > CUSTOM_BUTTON_Y) {
			attacksAndConquers.batch.draw(CustomActive, x, CUSTOM_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();

				Gdx.graphics.setWindowedMode(0, 0);
				OperazioniSulFile.CaricaDaFile();
				if (OperazioniSulFile.getEsisteFile()) {
					touchCustom = true;
					attacksAndConquers.setScreen(new DifficoltaScreen(attacksAndConquers, mainMenuScreen, touchCustom));
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				} else
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

				// attacksAndConquers.setScreen(new
				// CustomScreen(attacksAndConquers));
			}
		} else {
			attacksAndConquers.batch.draw(CustomInactive, x, CUSTOM_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Undo button
		x = 20;
		if (Gdx.input.getX() < x + UNDO_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < UNDO_BUTTON_Y + UNDO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > UNDO_BUTTON_Y) {
			attacksAndConquers.batch.draw(UndoActive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(mainMenuScreen);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
