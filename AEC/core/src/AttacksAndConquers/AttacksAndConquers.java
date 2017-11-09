package AttacksAndConquers;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import AllScreen.LoadingScreen;
import AllScreen.MainMenuScreen;
import AllScreen.ScrollingBackground;

public class AttacksAndConquers extends Game {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public double width;
	public double height;

	public SpriteBatch batch;
	public ModelBatch modelBatch;
	private BitmapFont font;
	Stage s; //titolo
	Stage stageS; //server
	Stage stageC; //client
	Label Name;
	
	LoadingScreen loading;
	
	public boolean disegnoName = false;
	public boolean disegnoButtonServer = false;
	public boolean disegnoButtonClient = false;

	public ScrollingBackground scrolling;

	@Override
	public void create() {
		
		
		
		batch = new SpriteBatch();
		modelBatch = new ModelBatch();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/SHOWG.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 50;
			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.!/%'()>?: ";
			font = generator.generateFont(parameter);
			parameter.size = 20;
			// smallfont = generator.generateFont(parameter);
			generator.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Gdx.files.internal("Font/SHOWG.ttf").file().getAbsolutePath());
			System.exit(1);
		}

		s = new Stage();
		stageS = new Stage();
		stageC = new Stage();

		LabelStyle labelStyle = new LabelStyle(font, Color.YELLOW);
		Name = new Label("Attacks and Conquers", labelStyle);
		Name.setPosition(Gdx.graphics.getWidth() / 2 - Name.getWidth() / 2,
				Gdx.graphics.getHeight() - Name.getHeight() * 2);

		s.addActor(Name);

		scrolling = new ScrollingBackground();
		
		loading = new LoadingScreen(this);
		this.setScreen(loading);
	}

	public BitmapFont getFont() {
		return font;
	}
	
	public Stage getStageS() {
		return stageS;
	}
	
	public Stage getStageC() {
		return stageC;
	}
	
	
	
	public LoadingScreen getLoading() {
		return loading;
	}
	
	@Override
	public void render() {
		super.render();

		if (disegnoName) {
			s.act();
			s.draw();
		}
		
		if(disegnoButtonServer){
			stageS.act();
			stageS.draw();
		}
		
		if(disegnoButtonClient){
			stageC.act();
			stageC.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

}
