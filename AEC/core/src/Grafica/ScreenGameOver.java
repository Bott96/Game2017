package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import AttacksAndConquers.GameManager;
import UserGameInput.CadutaLibera;

public class ScreenGameOver {

	Stage s;
	Label GameOver;

	public ScreenGameOver() {

		BitmapFont font = new BitmapFont();

		s = new Stage();

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/SHOWG.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 70;
			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.!/%'()>?: ";
			font = generator.generateFont(parameter);
			parameter.size = 10;
			generator.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Gdx.files.internal("Font/SHOWG.ttf").file().getAbsolutePath());
			System.exit(1);
		}

		LabelStyle labelStyle = new LabelStyle(font, Color.ORANGE);
		GameOver = new Label("Game Over", labelStyle);

		GameOver.setPosition(Gdx.graphics.getWidth() / 2 - GameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		s.addActor(GameOver);
		
		if (GameManager.StringGameOver == "CadutaLibera") {
			CadutaLibera c = new CadutaLibera(GameManager.PositionPlayer, GameManager.DirectionCamera);
			c.start();
		}		
		
	}

	public Stage getStage() {
		return s;
	}

}
