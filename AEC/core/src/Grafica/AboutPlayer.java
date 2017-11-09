package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import AttacksAndConquers.GameManager;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import mondo3D.Player;

public class AboutPlayer {

	public Stage i;

	public static Skin skin;
	public Label lblVita;
	public Label lblMunizioni;
	SpriteBatch batch;
	Viewport view;
	BitmapFont font;
	Table tableC;
	Table tableP;
	Table tableM;

	public AboutPlayer() {

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/SHOWG.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 20;
			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.!/%'()>?-: ";
			font = generator.generateFont(parameter);
			parameter.size = 10;
			generator.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(Gdx.files.internal("Font/SHOWG.ttf").file().getAbsolutePath());
			System.exit(1);
		}

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		batch = new SpriteBatch();
		view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
		i = new Stage(view, batch);

		tableC = new Table();
		tableC.setFillParent(true);

		tableP = new Table();
		tableP.setFillParent(true);

		tableM = new Table();
		tableM.setFillParent(true);

		LabelStyle labelStyle = new LabelStyle(font, null);
		
		Image Cuore = new Image(new Texture(Gdx.files.internal("Cuore.png")));
		Image Pallottole = new Image(new Texture(Gdx.files.internal("IconaArmi.png")));
		Image Mirino = new Image(new Texture(Gdx.files.internal("Mirino.png")));

		lblVita = new Label("Vita: ", labelStyle);
		lblVita.setPosition(Gdx.graphics.getWidth() - lblVita.getWidth() * 5,
				Gdx.graphics.getHeight() - lblVita.getHeight() - 10);
		
		tableC.setPosition(Gdx.graphics.getWidth()/2- lblVita.getWidth() * 5 -20,Gdx.graphics.getHeight()/2- 40/2);
		tableM.setPosition(0, 170);
		
		lblMunizioni = new Label("Munizioni: ", labelStyle);
		lblMunizioni.setPosition(Gdx.graphics.getWidth() - lblVita.getWidth() * 5,
				Gdx.graphics.getHeight() - lblMunizioni.getHeight() - 60);
		
		tableP.setPosition(Gdx.graphics.getWidth()/2- lblVita.getWidth() * 5 -35, Gdx.graphics.getHeight()/2- 65);
		
		lblVita.setText("Vita: " + DynamicElements.Giocatore.getVita() + "/" + GameManager.MaxVitaPlayer);
		lblMunizioni.setText("Munizioni: " + Player.Munizioni + "/" + GameManager.MaxMunizioniPlayer);

		tableC.add(Cuore);
		tableP.add(Pallottole);
		tableM.add(Mirino);

		//table.setPosition(250,250);
		//table.setSize(5, 5);
		
		i.addActor(lblVita);
		i.addActor(lblMunizioni);

		i.addActor(tableC);
		i.addActor(tableP);
		i.addActor(tableM);

	}

	public void ModificaLabel(int x) {

		lblVita.setText("Vita: " + String.valueOf(x) + "/" + GameManager.MaxVitaPlayer);
	}

	public void ModificaLabelM(int x) {
		lblMunizioni.setText("Munizioni: " + String.valueOf(x) + "/" + GameManager.MaxMunizioniPlayer);
	}

	public Stage getStage() {
		return i;
	}

}
