package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.GameManager;

public class ScrollingBackground {

	public static final int DEFAULT_SPEED = 15;
	public static final int ACCELERATION = 40;
	public static final int GOAL_REACH_ACCELERATION = 150;

	Texture xpos;
	Texture xneg;
	Texture zpos;
	Texture zneg;

	float x1, x2;
	int speed;// In pixels / second
	int goalSpeed;
	float imageScale;
	boolean speedFixed;

	Label Name;

	public ScrollingBackground() {

		GameManager.mp3Music = Gdx.audio.newMusic(Gdx.files.internal("Music/Song.mp3"));

		GameManager.mp3Music.play();
		
		
		xpos = new Texture("SkyBox/xpos.png");
		xneg = new Texture("SkyBox/xneg.png");
		zpos = new Texture("SkyBox/zpos.png");
		zneg = new Texture("SkyBox/zneg.png");

		x1 = 0;
		x2 = xpos.getWidth();
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		imageScale = Gdx.graphics.getWidth();
		speedFixed = true;
	}

	public void updateAndRender(float deltaTime, SpriteBatch batch) {
		// Speed adjustment to reach goal

		if (speed < goalSpeed) {
			speed += GOAL_REACH_ACCELERATION * deltaTime;
			if (speed > goalSpeed)
				speed = goalSpeed;
		} else if (speed > goalSpeed) {
			speed -= GOAL_REACH_ACCELERATION * deltaTime;
			if (speed < goalSpeed)
				speed = goalSpeed;
		}

		if (!speedFixed)
			speed += ACCELERATION * deltaTime;

		x1 -= speed * deltaTime;
		x2 -= speed * deltaTime;

		// if image reached the bottom of screen and is not visible, put it back
		// on top
		if (x1 + xneg.getWidth() * imageScale <= 0)
			x1 = x2 + xneg.getWidth() * imageScale;

		// Render
		batch.draw(xneg, x1, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(zpos, x1 + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(xpos, x1 + 2 * Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(zneg, x1 + 3 * Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// s.act();
		// s.draw();
	}

	public void setSpeed(int goalSpeed) {
		this.goalSpeed = goalSpeed;
	}

	public void setSpeedFixed(boolean speedFixed) {
		this.speedFixed = speedFixed;
	}

}