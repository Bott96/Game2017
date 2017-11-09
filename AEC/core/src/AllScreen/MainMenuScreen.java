package AllScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.GameManager;
import AttacksAndConquers.Risorse;
import LevelEditor.Editor;
import MultiPlayer.MultiPlayerSettings;

public class MainMenuScreen implements Screen {

	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 60;
	private static final int SINGLE_BUTTON_Y = 345;// 340
	private static final int MULTI_BUTTON_Y = 275;// 270

	private static final int EDITOR_BUTTON_WIDTH = 200;
	private static final int EDITOR_BUTTON_HEIGHT = 45;
	private static final int EDITOR_BUTTON_Y = 210;// 205

	private static final int OPZ_BUTTON_WIDTH = 80;
	private static final int OPZ_BUTTON_HEIGHT = 80;
	private static final int OPZ_BUTTON_Y = 115;// 100

	private static final int EXIT_BUTTON_WIDTH = 150;
	private static final int EXIT_BUTTON_HEIGHT = 50;
	private static final int EXIT_BUTTON_Y = 50;

	private static final int INFO_BUTTON_WIDTH = 50;
	private static final int INFO_BUTTON_HEIGHT = 50;
	private static final int SINGLEI_BUTTON_Y = 350;
	private static final int MULTII_BUTTON_Y = 278;
	private static final int EDITORI_BUTTON_Y = 203;

	final AttacksAndConquers attacksAndConquers;

	MultiPlayerScreen multiPlayer;
	SinglePlayerScreen singlePlayer;

	Texture SinglePlayerButtonActive;
	Texture SinglePlayerButtonInactive;
	Texture MultiPlayerButtonActive;
	Texture MultiPlayerButtonInactive;
	Texture MakeALevelActive;
	Texture MakeALevelInactive;
	Texture ExitInactive;
	Texture ExitActive;
	Texture Sfondo;
	Texture OpzioneActive;
	Texture OpzioneInactive;
	Texture InfoInactive;
	Texture InfoActive;

	public MainMenuScreen(final AttacksAndConquers attacksAndConquers) {

		attacksAndConquers.disegnoName = true;

		this.attacksAndConquers = attacksAndConquers;

		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		SinglePlayerButtonActive = Risorse.assets.get(Risorse.SinglePlayerButtonActive);
		SinglePlayerButtonInactive = Risorse.assets.get(Risorse.SinglePlayerButtonInactive);
		MultiPlayerButtonActive = Risorse.assets.get(Risorse.MultiPlayerButtonActive);
		MultiPlayerButtonInactive = Risorse.assets.get(Risorse.MultiPlayerButtonInactive);
		MakeALevelActive = Risorse.assets.get(Risorse.MakeALevelActive);
		MakeALevelInactive = Risorse.assets.get(Risorse.MakeALevelInactive);
		ExitActive = Risorse.assets.get(Risorse.ExitActive);
		ExitInactive = Risorse.assets.get(Risorse.ExitInactive);
		OpzioneInactive = Risorse.assets.get(Risorse.OpzioneInactive);
		OpzioneActive = Risorse.assets.get(Risorse.OpzioneActive);
		InfoInactive = Risorse.assets.get(Risorse.InfoInactive);
		InfoActive = Risorse.assets.get(Risorse.InfoActive);
		

		multiPlayer = new MultiPlayerScreen(attacksAndConquers, this);
		singlePlayer = new SinglePlayerScreen(attacksAndConquers, this);

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

		Gdx.input.setInputProcessor(null);
		
		// SinglePlayer button
		int x = 5;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < SINGLE_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > SINGLE_BUTTON_Y) {
			attacksAndConquers.batch.draw(SinglePlayerButtonActive, x, SINGLE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				GameManager.mp3Walk = Gdx.audio.newMusic(Gdx.files.internal("Music/Walking - on.mp3"));
				GameManager.mp3Run = Gdx.audio.newMusic(Gdx.files.internal("Music/Running.mp3"));
				attacksAndConquers.setScreen(singlePlayer);
			}
		} else {
			attacksAndConquers.batch.draw(SinglePlayerButtonInactive, x, SINGLE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Info
		int y = BUTTON_WIDTH + 6;
		if (Gdx.input.getX() < y + INFO_BUTTON_WIDTH && Gdx.input.getX() > y
				&& attacksAndConquers.height - Gdx.input.getY() < SINGLEI_BUTTON_Y + INFO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > SINGLEI_BUTTON_Y) {
			attacksAndConquers.batch.draw(InfoActive, y, SINGLEI_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(new InfoSinglePlayerScreen(attacksAndConquers, this));
			}
		} else {

			attacksAndConquers.batch.draw(InfoInactive, y, SINGLEI_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);
		}

		// MultiPlayer button
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < MULTI_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > MULTI_BUTTON_Y) {
			attacksAndConquers.batch.draw(MultiPlayerButtonActive, x, MULTI_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				MultiPlayerSettings.MultiPlayer = true;
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(multiPlayer);
			}
		} else {
			attacksAndConquers.batch.draw(MultiPlayerButtonInactive, x, MULTI_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Info
		y = BUTTON_WIDTH + 6;
		if (Gdx.input.getX() < y + INFO_BUTTON_WIDTH && Gdx.input.getX() > y
				&& attacksAndConquers.height - Gdx.input.getY() < MULTII_BUTTON_Y + INFO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > MULTII_BUTTON_Y) {

			attacksAndConquers.batch.draw(InfoActive, y, MULTII_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(new InfoMultiPlayerScreen(attacksAndConquers, this));
			}

		} else {

			attacksAndConquers.batch.draw(InfoInactive, y, MULTII_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);
		}

		// Make a level button
		if (Gdx.input.getX() < x + EDITOR_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < EDITOR_BUTTON_Y + EDITOR_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > EDITOR_BUTTON_Y) {
			attacksAndConquers.batch.draw(MakeALevelActive, x, EDITOR_BUTTON_Y, EDITOR_BUTTON_WIDTH,
					EDITOR_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.disegnoName = false;
				attacksAndConquers.setScreen(new Editor(attacksAndConquers, this));
			}
		} else {
			attacksAndConquers.batch.draw(MakeALevelInactive, x, EDITOR_BUTTON_Y, EDITOR_BUTTON_WIDTH,
					EDITOR_BUTTON_HEIGHT);
		}

		// Info
		y = EDITOR_BUTTON_WIDTH + 6;
		if (Gdx.input.getX() < y + INFO_BUTTON_WIDTH && Gdx.input.getX() > y
				&& attacksAndConquers.height - Gdx.input.getY() < EDITORI_BUTTON_Y + INFO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > EDITORI_BUTTON_Y) {
			attacksAndConquers.batch.draw(InfoActive, y, EDITORI_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(new InfoEditorScreen(attacksAndConquers, this));
			}
		} else {
			// attacksAndConquers.batch.draw(SinglePlayerButtonInactive, 2,
			// Gdx.graphics.getHeight()/3);
			attacksAndConquers.batch.draw(InfoInactive, y, EDITORI_BUTTON_Y, INFO_BUTTON_WIDTH, INFO_BUTTON_HEIGHT);
		}

		// Opzione button
		if (Gdx.input.getX() < x + OPZ_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < OPZ_BUTTON_Y + OPZ_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > OPZ_BUTTON_Y) {
			attacksAndConquers.batch.draw(OpzioneActive, x, OPZ_BUTTON_Y, OPZ_BUTTON_WIDTH, OPZ_BUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(new OpzioniScreen(attacksAndConquers, this));
			}
		} else {
			attacksAndConquers.batch.draw(OpzioneInactive, x, OPZ_BUTTON_Y, OPZ_BUTTON_WIDTH, OPZ_BUTTON_HEIGHT);
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

		// Gdx.input.setInputProcessor(null);
	}

	public void setMultiPlayer(MultiPlayerScreen multiPlayer) {
		this.multiPlayer = multiPlayer;
	}
}
