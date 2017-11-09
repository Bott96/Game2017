package LevelEditor;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import AllScreen.MainMenuScreen;
import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.Risorse;
import Grafica.Menu;
import Grafica.Mirino;
import Grafica.OggettoStatico;
import Grafica.StaticElements;
import mondo3D.Sky;

public class Editor implements Screen {

	public Menu M;
	public PerspectiveCamera cam;

	public Mirino ViewFinder;

	SpriteBatch batch;
	public ModelBatch modelBatch;
	public Environment environment;
	float X = 40f, Y = 120f, Z = 10f, r = 0, r1 = 0;
	float MX = 40f, MY = 5f, MZ = 0f;
	MainMenuScreen mainMenuScreen;
	
	public Editor(AttacksAndConquers AAC, MainMenuScreen main) {


		batch = new SpriteBatch();

		Texture xpos = Risorse.assets.get(Risorse.xpos);
		Texture xneg = Risorse.assets.get(Risorse.xneg);
		Texture ypos = Risorse.assets.get(Risorse.ypos);
		Texture yneg = Risorse.assets.get(Risorse.yneg);
		Texture zpos = Risorse.assets.get(Risorse.zpos);
		Texture zneg = Risorse.assets.get(Risorse.zneg);
		Sky.init();
		Sky.createSkyBox(xpos, xneg, ypos, yneg, zpos, zneg);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0f, 0f, 0f, 255f));
		// environment.add(new DirectionalLight().set(Color.BLUE, new
		// Vector3(-100f, -100f, -100f)));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(X, Y, Z);

		Vector3 pos = new Vector3(X, Y, X);
		Vector3 Rot = new Vector3(0, 0, 12);

		// System.out.println(cam.position.x);

		// cam.rotateAround(pos, new Vector3(0,1,0), 50); // potrebbe essere
		// utile nel gioco
		// System.out.println(cam.position.x);

		cam.near = 0.1f;
		cam.far = 1700f;
		cam.direction.set(Rot);

		cam.update();

		modelBatch = new ModelBatch();
		
		mainMenuScreen=main;
		
		ViewFinder = new Mirino(cam, pos, Rot);
		M = new Menu(cam, pos, Rot, AAC,mainMenuScreen);
		StaticElements.Oggetti.add(new OggettoStatico(EnumTipoDiOggetto.Terreno)); //
		
		
		
		Gdx.input.setInputProcessor(M.getMyInput());

	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		modelBatch.begin(cam);

		modelBatch.render(InstanzeDeiModelliUsati(), environment);

		// modelBatch.render(GameElements.Oggetti.get(1).getInstance());
		update(1);
		modelBatch.render(Sky.modelInstance);

		modelBatch.end();

		ViewFinder.getMyInput().act();
		ViewFinder.getMyInput().draw();

		M.getMyInput().act();
		M.getMyInput().draw();

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		ViewFinder.getMyInput().dispose();
		ViewFinder.getSkin().dispose();
		M.getMyInput().dispose();
		M.getSkin().dispose();
	}

	public void update(float dt) {

		Sky.update(cam.position);
	}

	@Override
	public void resize(int width, int height) {

		// TODO Auto-generated method stub

		M.getMyInput().getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public ArrayList<ModelInstance> InstanzeDeiModelliUsati() {
		ArrayList<ModelInstance> l = new ArrayList<ModelInstance>();

		for (int i = 0; i < StaticElements.Oggetti.size(); i++)
			l.add(StaticElements.Oggetti.get(i).getInstance());

		return l;

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}