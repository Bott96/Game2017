package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;

public class InfoMultiPlayerScreen implements Screen {

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 50;
	private static final int EXIT_BUTTON_WIDTH = 80;
	private static final int EXIT_BUTTON_HEIGHT = 80;

	final AttacksAndConquers attacksAndConquers;
	MainMenuScreen mainMenuScreen;

	boolean possoAndareAvanti = true;
	boolean possoAndareIndietro = true;
	Texture Uno;
	Texture Due;
	Texture Tre;
	Texture Quattro;
	Texture Cinque;
	Texture FrecciaAvantiI;
	Texture FrecciaAvantiA;
	Texture FrecciaIndietroA;
	Texture FrecciaIndietroI;
	Texture UndoI;
	Texture UndoA;

	int pagineCorrente;

	public InfoMultiPlayerScreen(final AttacksAndConquers attacksAndConquers, MainMenuScreen main) {

		this.attacksAndConquers = attacksAndConquers;
		mainMenuScreen = main;
		pagineCorrente = 1;

		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		Uno = Risorse.assets.get(Risorse.UnoMulti);
		Due = Risorse.assets.get(Risorse.DueSingleP);
		Tre = Risorse.assets.get(Risorse.TreSingleP);
		Quattro = Risorse.assets.get(Risorse.QuattroSingleP);
		Cinque = Risorse.assets.get(Risorse.CinqueSingleP);

		FrecciaAvantiA = Risorse.assets.get(Risorse.FrecciaAvantiActive);
		FrecciaAvantiI = Risorse.assets.get(Risorse.FrecciaAvantiInactive);
		FrecciaIndietroA = Risorse.assets.get(Risorse.FrecciaIndietroActive);
		FrecciaIndietroI = Risorse.assets.get(Risorse.FrecciaIndietroInactive);

		UndoA = Risorse.assets.get(Risorse.UndoInfoActive);
		UndoI = Risorse.assets.get(Risorse.UndoInfoInactive);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {

		attacksAndConquers.batch.begin();

		attacksAndConquers.scrolling.updateAndRender(delta, attacksAndConquers.batch);

		disegnaNuovaPagina(pagineCorrente);

		if (pagineCorrente < 5) {
			if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2 + BUTTON_WIDTH + 5
					&& Gdx.input.getX() >= Gdx.graphics.getWidth() / 2 + 5
					&& attacksAndConquers.height - Gdx.input.getY() < Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2
							+ 5 + BUTTON_HEIGHT
					&& attacksAndConquers.height - Gdx.input.getY() > Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2
							+ 5) {
				attacksAndConquers.batch.draw(FrecciaAvantiA, Gdx.graphics.getWidth() / 2 + 5,
						Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2 + 5, BUTTON_WIDTH, BUTTON_HEIGHT);

				// possoAndareAvanti = true;
				if (Gdx.input.isTouched() && possoAndareAvanti) {
					// attacksAndConquers.dispose();
					possoAndareAvanti = false;

					if (pagineCorrente < 5) {
						pagineCorrente += 1;
						disegnaNuovaPagina(pagineCorrente);
					}
				}

			} else {
				attacksAndConquers.batch.draw(FrecciaAvantiI, Gdx.graphics.getWidth() / 2 + 5,
						Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2 + 5, BUTTON_WIDTH, BUTTON_HEIGHT);
				possoAndareAvanti = true;
			}
		}

		if (pagineCorrente > 1) {
			if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2 - 10
					&& Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH - 10
					&& attacksAndConquers.height - Gdx.input.getY() < Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2
							+ 5 + BUTTON_HEIGHT
					&& attacksAndConquers.height - Gdx.input.getY() > Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2
							+ 5) {
				attacksAndConquers.batch.draw(FrecciaIndietroA, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH - 10,
						Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2 + 5, BUTTON_WIDTH, BUTTON_HEIGHT);

				if (Gdx.input.isTouched() && possoAndareIndietro) {

					possoAndareIndietro = false;
					if (pagineCorrente > 1) {
						pagineCorrente -= 1;
						disegnaNuovaPagina(pagineCorrente);
					}
				}

			} else {
				attacksAndConquers.batch.draw(FrecciaIndietroI, Gdx.graphics.getWidth() / 2 - BUTTON_WIDTH - 10,
						Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2 + 5, BUTTON_WIDTH, BUTTON_HEIGHT);
				possoAndareIndietro = true;
			}
		}

		if (Gdx.input.getX() < EXIT_BUTTON_WIDTH + 90 && Gdx.input.getX() > 10
				&& attacksAndConquers.height - Gdx.input.getY() < 90 + EXIT_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > 10) {
			attacksAndConquers.batch.draw(UndoA, 90, 10, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(mainMenuScreen);
			}

		} else {
			attacksAndConquers.batch.draw(UndoI, 90, 10, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}

		attacksAndConquers.batch.end();

	}

	public void disegnaNuovaPagina(int pagina) {
		switch (pagina) {
		case 1:
			attacksAndConquers.batch.flush();
			attacksAndConquers.batch.draw(Uno, Gdx.graphics.getWidth() / 2 - Uno.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2f);
			break;

		case 2:
			attacksAndConquers.batch.flush();
			attacksAndConquers.batch.draw(Due, Gdx.graphics.getWidth() / 2 - Uno.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2f);

			break;

		case 3:
			attacksAndConquers.batch.flush();
			attacksAndConquers.batch.draw(Tre, Gdx.graphics.getWidth() / 2 - Uno.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2f);

			break;

		case 4:
			attacksAndConquers.batch.flush();
			attacksAndConquers.batch.draw(Quattro, Gdx.graphics.getWidth() / 2 - Uno.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2f);

			break;

		case 5:
			attacksAndConquers.batch.flush();
			attacksAndConquers.batch.draw(Cinque, Gdx.graphics.getWidth() / 2 - Uno.getWidth() / 2,
					Gdx.graphics.getHeight() / 2 - Uno.getHeight() / 2f);

			break;

		default:
			break;
		}
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
