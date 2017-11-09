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
import MultiPlayer.Client;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.Server;
import PutEditorOnFile.SaveLoadOnFile;
import mondo3D.World;

public class MultiPlayerScreen implements Screen {

	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 60;
	private static final int SERVER_BUTTON_Y = 200;
	private static final int CLIENT_BUTTON_Y = 200;

	private static final int EXIT_BUTTON_WIDTH = 150;
	private static final int EXIT_BUTTON_HEIGHT = 50;
	private static final int EXIT_BUTTON_Y = 50;

	private static final int UNDO_BUTTON_WIDTH = 80;
	private static final int UNDO_BUTTON_HEIGHT = 80;
	private static final int UNDO_BUTTON_Y = 50;

	final AttacksAndConquers attacksAndConquers;
	MainMenuScreen mainMenuScreen;
	
	ServerScreen serverScreen;
	ClientScreen clientScreen;

	Texture Sfondo;
	Texture ServerInactive;
	Texture ServerActive;
	Texture ClientInactive;
	Texture ClientActive;
	Texture ExitInactive;
	Texture ExitActive;
	Texture UndoInactive;
	Texture UndoActive;
	public static boolean HoCreatoIlServer = false;
	public static boolean HoCreatoIlClient = false;
	SaveLoadOnFile operazioniSulFile;

	public MultiPlayerScreen(final AttacksAndConquers attacksAndConquers, MainMenuScreen main) {
	
		mainMenuScreen = main;
		
		this.attacksAndConquers = attacksAndConquers;
		operazioniSulFile = new SaveLoadOnFile();
		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		clientScreen = new ClientScreen(attacksAndConquers,this);

		serverScreen = new ServerScreen(attacksAndConquers,this);
		
		ServerActive = Risorse.assets.get(Risorse.ServerActive);
		ServerInactive = Risorse.assets.get(Risorse.ServerInactive);
		ClientActive = Risorse.assets.get(Risorse.ClientActive);
		ClientInactive = Risorse.assets.get(Risorse.ClientInactive);
		ExitActive = Risorse.assets.get(Risorse.ExitActive);
		ExitInactive = Risorse.assets.get(Risorse.ExitInactive);
		UndoActive = Risorse.assets.get(Risorse.UndoActive);
		UndoInactive = Risorse.assets.get(Risorse.UndoInactive);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		attacksAndConquers.batch.begin();

		attacksAndConquers.scrolling.updateAndRender(delta, attacksAndConquers.batch);

		// Server button
		int x = BUTTON_WIDTH;
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < SERVER_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > SERVER_BUTTON_Y) {

			attacksAndConquers.batch.draw(ServerActive, x, SERVER_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {

				attacksAndConquers.dispose();
				attacksAndConquers.disegnoButtonServer = true;

				Gdx.input.setInputProcessor(attacksAndConquers.getStageS());
				attacksAndConquers.setScreen(serverScreen);
				/*Server s = new Server();
				s.start();
				HoCreatoIlServer = true;*/

			}

		} else {
			// attacksAndConquers.batch.draw(SinglePlayerButtonInactive, 2,
			// Gdx.graphics.getHeight()/3);
			attacksAndConquers.batch.draw(ServerInactive, x, SERVER_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Client button
		x = (int) (Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH-1.5*BUTTON_WIDTH);
		if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < CLIENT_BUTTON_Y + BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > CLIENT_BUTTON_Y) {

			attacksAndConquers.batch.draw(ClientActive, x, CLIENT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
			if (Gdx.input.isTouched() && !HoCreatoIlClient) {
				attacksAndConquers.dispose();
				attacksAndConquers.disegnoButtonClient=true;
				Gdx.input.setInputProcessor(attacksAndConquers.getStageC());
				attacksAndConquers.setScreen(clientScreen);
				/*attacksAndConquers.disegnoName = false;

				operazioniSulFile.CaricaLivelloDiDefault();

				attacksAndConquers.setScreen(new World(attacksAndConquers));

				HoCreatoIlClient = true;*/
			}

		} else {
			// attacksAndConquers.batch.draw(SinglePlayerButtonInactive, 2,
			// Gdx.graphics.getHeight()/3);
			attacksAndConquers.batch.draw(ClientInactive, x, SERVER_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		}

		// Undo button
		x = 20;
		if (Gdx.input.getX() < x + UNDO_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < UNDO_BUTTON_Y + UNDO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > UNDO_BUTTON_Y) {
			attacksAndConquers.batch.draw(UndoActive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				MultiPlayerSettings.MultiPlayer = false;
				attacksAndConquers.setScreen(mainMenuScreen);
			}
		} else {
			attacksAndConquers.batch.draw(UndoInactive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
		}

		// Exit buttom
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
	
	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

}
