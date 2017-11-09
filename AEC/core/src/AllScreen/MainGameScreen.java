package AllScreen;

import com.badlogic.gdx.Screen;

import AttacksAndConquers.AttacksAndConquers;

public class MainGameScreen implements Screen {

	AttacksAndConquers attacksAndConquers;

	public MainGameScreen(AttacksAndConquers attacksAndConquers) {

		this.attacksAndConquers = attacksAndConquers;

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		// Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
