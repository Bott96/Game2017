package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Win {
	
	Stage s;
	Label Win;
	
	public Win() {
		
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
		Win = new Label("You won", labelStyle);
		Win.setPosition(Gdx.graphics.getWidth() / 2 - Win.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		s.addActor(Win);
	}

	public Stage getStage() {
		return s;
	}

}
