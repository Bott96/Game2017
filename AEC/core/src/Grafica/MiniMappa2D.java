package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.GameManager;
import MultiPlayer.MultiPlayerSettings;
import mondo3D.Compagno;
import mondo3D.Enemy;

public class MiniMappa2D {

	public Stage s; // stage
	public Skin skin;
	public SpriteBatch batch;
	public ShapeRenderer shapeRender;
	public final int GrandezzaMappa = 250;
	Viewport view;
	Table table;

	Image Radar;

	public MiniMappa2D() {

		batch = new SpriteBatch();
		shapeRender = new ShapeRenderer();
		view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
		s = new Stage(view, batch);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		table = new Table();
		table.setFillParent(true);
		Radar = new Image(new Texture(Gdx.files.internal("MiniMappa/Radar1.png")));
		Radar.setWidth(GrandezzaMappa);
		Radar.setHeight(GrandezzaMappa);

		table.left().bottom();
		table.setPosition(5, 5);
		table.add(Radar).width(GrandezzaMappa).height(GrandezzaMappa);
		s.addActor(table);

	}

	public Skin getSkin() {
		return skin;
	}

	public void DrawMappa() {

		// Texture texture = new Texture(new Pixmap(1, 1, Format.RGB888));

		batch.begin();
		shapeRender.begin(ShapeType.Filled);

		// shapeRender.setColor(Color.DARK_GRAY);

		// shapeRender.rect(5, 5, GrandezzaMappa, GrandezzaMappa);
		shapeRender.setColor(Color.RED);
		float x = 0;
		float z = 0;

		x = (((GrandezzaMappa / 2) * GameManager.PositionPlayer.x) / 750) + GrandezzaMappa / 2;

		z = (((GrandezzaMappa / 2) * GameManager.PositionPlayer.z) / 750) + GrandezzaMappa / 2;
		shapeRender.circle(x, z, 4);

		
		if(MultiPlayerSettings.MultiPlayer&& MultiPlayerSettings.PuoiInviare)
		{
			for (Compagno c : DynamicElements.Alleati) {
				
				Vector3 PoszioneAlleato = new Vector3();
				c.Instance.transform.getTranslation(PoszioneAlleato);
				x = (((GrandezzaMappa / 2) * PoszioneAlleato.x) / 750) + GrandezzaMappa / 2;

				z = (((GrandezzaMappa / 2) * PoszioneAlleato.z) / 750) + GrandezzaMappa / 2;
				shapeRender.circle(x, z, 4);
			}
		}
		shapeRender.setColor(Color.BLUE);
		Vector3 PositionEnemy = new Vector3();

		for (Enemy e : DynamicElements.Nemici) {

			e.Instance.transform.getTranslation(PositionEnemy);
			if (!e.Morto) {
				x = (((GrandezzaMappa / 2) * PositionEnemy.x) / 750) + GrandezzaMappa / 2;

				z = (((GrandezzaMappa / 2) * PositionEnemy.z) / 750) + GrandezzaMappa / 2;

				shapeRender.circle(x, z, 4);
			}
		}

		shapeRender.setColor(Color.YELLOW);

		Vector3 PosizioniOggettiStatici = new Vector3();
		for (OggettoStatico o : StaticElements.Oggetti) {

			o.Instance.transform.getTranslation(PosizioniOggettiStatici);

			x = (((GrandezzaMappa / 2) * PosizioniOggettiStatici.x) / 750) + GrandezzaMappa / 2;

			z = (((GrandezzaMappa / 2) * PosizioniOggettiStatici.z) / 750) + GrandezzaMappa / 2;

			if (o.oggetto == EnumTipoDiOggetto.Albero) {
				shapeRender.setColor(Color.GREEN);
				shapeRender.circle(x, z, 5);
			} else if (o.oggetto == EnumTipoDiOggetto.Casse) {

				shapeRender.setColor(Color.BROWN);
				shapeRender.rect(x, z, 6, 6);
			} else if (o.oggetto == EnumTipoDiOggetto.CarroArmato) {
				shapeRender.setColor(Color.FOREST);
				shapeRender.rect(x, z, 7, 8);
			} else if (o.oggetto == EnumTipoDiOggetto.Munizioni) {
				shapeRender.setColor(Color.TAN);
				shapeRender.rect(x, z, 6, 8);
			} else if (o.oggetto == EnumTipoDiOggetto.PrimoSoccorso) {
				shapeRender.setColor(Color.PINK);
				shapeRender.rect(x, z, 7, 5);
			} else if (o.oggetto == EnumTipoDiOggetto.Cespuglio) {
				shapeRender.setColor(25f, 112f, 54f, 0f);
				shapeRender.rect(x, z, 7, 5);
			}

		}

		shapeRender.end();
		batch.end();
	}

	public Stage getStage() {
		return s;
	}
}
