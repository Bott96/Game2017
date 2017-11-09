package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;

public class OpzioniScreen implements Screen {

	final AttacksAndConquers attacksAndConquers;
	MainMenuScreen mainMenuScreen;

	Texture PlayA;
	Texture PlayI;
	Texture MuteI;
	Texture MuteA;
	Texture QuitA;
	Texture QuitI;
	Texture Note;
	
	boolean touchDown = false;
	boolean touchUp = false;

	public OpzioniScreen(final AttacksAndConquers attacksAndConquers, MainMenuScreen main) {

		this.attacksAndConquers = attacksAndConquers;
		mainMenuScreen = main;

		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		PlayA = Risorse.assets.get(Risorse.PlayActive);
		PlayI = Risorse.assets.get(Risorse.PlayInactive);
		MuteI = Risorse.assets.get(Risorse.MuteInactive);
		MuteA = Risorse.assets.get(Risorse.MuteActive);
		QuitA = Risorse.assets.get(Risorse.QuitActive);
		QuitI = Risorse.assets.get(Risorse.QuitInactive);
		Note = Risorse.assets.get(Risorse.Note);
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

		if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - PlayA.getWidth() 
				&& Gdx.input.getX() < Gdx.graphics.getWidth() / 2 - PlayA.getWidth()  + 200
				&& attacksAndConquers.height - Gdx.input.getY() > Gdx.graphics.getHeight() / 1.5f
				&& attacksAndConquers.height - Gdx.input.getY() < Gdx.graphics.getHeight() / 1.5f +50) {

			if (GameManager.MusicActive) {
				attacksAndConquers.batch.draw(MuteA, Gdx.graphics.getWidth() / 2 - PlayA.getWidth() ,
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			} else {
				attacksAndConquers.batch.draw(PlayA, Gdx.graphics.getWidth() / 2 - PlayA.getWidth() ,
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			}

			if (Gdx.input.isTouched() && !touchDown) {
				touchDown = true;
				close();
			}

		} else {
			if (GameManager.MusicActive) {
				attacksAndConquers.batch.draw(MuteI, Gdx.graphics.getWidth() / 2 - PlayA.getWidth(),
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			} else {
				attacksAndConquers.batch.draw(PlayI, Gdx.graphics.getWidth() / 2 - PlayA.getWidth(),
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			}
		}

		if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - QuitA.getWidth() 
				&& Gdx.input.getX() < Gdx.graphics.getWidth() / 2 - QuitA.getWidth()  + 200
				&& attacksAndConquers.height - Gdx.input.getY() > (Gdx.graphics.getHeight() / 1.5f) - 70
				&& attacksAndConquers.height - Gdx.input.getY() < (Gdx.graphics.getHeight() / 1.5f) - 70 + 52) {

			attacksAndConquers.batch.draw(QuitA, Gdx.graphics.getWidth() / 2 - QuitA.getWidth() ,
					(Gdx.graphics.getHeight() / 1.5f) - 70, 200, 52);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.setScreen(mainMenuScreen);
			}
		} else {
			attacksAndConquers.batch.draw(QuitI, Gdx.graphics.getWidth() / 2 - QuitA.getWidth() ,
					(Gdx.graphics.getHeight() / 1.5f) - 70, 200, 52);
		}

		attacksAndConquers.batch.draw(Note, Gdx.graphics.getWidth() / 2 - PlayA.getWidth()  + 210,
				Gdx.graphics.getHeight() / 1.5f, 150, 50);
		
		attacksAndConquers.batch.end();
	}
	
	public void close(){
		if(touchDown){
			if (GameManager.MusicActive) {
				GameManager.MusicActive = false;
				GameManager.mp3Music.pause();
				attacksAndConquers.batch.draw(PlayA, Gdx.graphics.getWidth() / 2 - PlayA.getWidth() ,
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			} else {
				GameManager.MusicActive = true;
				GameManager.mp3Music.play();
				attacksAndConquers.batch.draw(MuteA, Gdx.graphics.getWidth() / 2 - PlayA.getWidth() ,
						Gdx.graphics.getHeight() / 1.5f, 200, 50);
			}
			touchDown= false;
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
