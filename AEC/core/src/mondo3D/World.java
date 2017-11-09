package mondo3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import AllScreen.MainMenuScreen;
import AllScreen.MultiPlayerScreen;
import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;
import Grafica.ScreenGameOver;
import Grafica.MenuPause;
import Grafica.MiniMappa2D;
import Grafica.OggettoStatico;
import Grafica.StaticElements;
import Grafica.ScreenWin;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.PosDirIDSparo;

public class World implements Screen {

	Environment environment;
	SpriteBatch batch;

	public PerspectiveCamera cam;
	public MultiPlayerScreen multiPlayerScreen;
	public ModelBatch modelBatch;
	public MenuPause Menu;
	public Player Personaggio;
	public MiniMappa2D miniMappa;
	ModelBatch shadowBatch;
	ScreenGameOver screenGameOver;
	ScreenWin screenWin;
	float tempo = 0f;
	boolean end = false;

	public final AttacksAndConquers attacksAndConquers;

	public World(final AttacksAndConquers AAC) {

		GameManager.mp3Music.setVolume(0.08f);

		screenWin = new ScreenWin();
		miniMappa = new MiniMappa2D();
		screenGameOver = new ScreenGameOver();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .1f, .1f, .1f, 255f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		// environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f,
		// .4f, .4f, 1f));

		Personaggio = new Player();
		DynamicElements.Giocatore = Personaggio;

		// DynamicElements.Giocatore.Instance.transform.scale(0.5f, 0.5f, 0.5f);

		attacksAndConquers = AAC;

		batch = new SpriteBatch();

		Texture xpos = Risorse.assets.get(Risorse.xpos);
		Texture xneg = Risorse.assets.get(Risorse.xneg);
		Texture ypos = Risorse.assets.get(Risorse.ypos);
		Texture yneg = Risorse.assets.get(Risorse.yneg);
		Texture zpos = Risorse.assets.get(Risorse.zpos);
		Texture zneg = Risorse.assets.get(Risorse.zneg);
		Sky.init();
		Sky.createSkyBox(xpos, xneg, ypos, yneg, zpos, zneg);

		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		cam.position.set(GameManager.InitialCamera);

		cam.near = 0.1f;
		cam.far = 700f;
		cam.direction.set(GameManager.InitialRCamera);

		cam.update();

		GameManager.AttualDirectionPlayer = new Vector3(cam.direction.x, 0, cam.direction.z);
		GameManager.DirectionForFire = new Vector3(cam.direction.x, 0, cam.direction.z);
		modelBatch = new ModelBatch();
		shadowBatch = new ModelBatch(new DepthShaderProvider());

		StaticElements.Oggetti.add(new OggettoStatico(EnumTipoDiOggetto.Terreno));

		Menu = new MenuPause(cam, GameManager.InitialCamera, GameManager.InitialRCamera, this);

		Gdx.input.setInputProcessor(Menu.getUserInput());

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// System.out.println("DT " + delta);
		DynamicElements.Giocatore.ControlloAnimazione.update(Gdx.graphics.getDeltaTime());
		batch.setProjectionMatrix(cam.combined);
		modelBatch.begin(cam);

		modelBatch.render(InstanzeDeiModelliUsati(), environment);
		modelBatch.render(InstanzeDeiProiettili(delta), environment);

		updateMultiPlayer(delta);
		modelBatch.render(InstanzeDeiNemici(), environment);
		Player.Ap.ModificaLabel(DynamicElements.Giocatore.getVita());
		Player.Ap.ModificaLabelM(Player.Munizioni);
		modelBatch.render(DynamicElements.Giocatore.Instance);

		CreaDisegnaProiettileDelNemico();
		DisegnaProiettileCompagno();

		update(1);
		modelBatch.render(Sky.modelInstance);

		modelBatch.end();
		if (!Menu.getUserInput().getCursoreNonAttivo()) {
			Menu.getUserInput().act();
			Menu.getUserInput().draw();
		} 
		else {
			
			
			Player.Ap.getStage().act();
			Player.Ap.getStage().draw();

			miniMappa.batch.setProjectionMatrix(miniMappa.getStage().getCamera().combined);
			miniMappa.getStage().act();
			miniMappa.getStage().draw();

			miniMappa.DrawMappa();
			
		}

		if (!MultiPlayerSettings.MultiPlayer & !MultiPlayerSettings.PuoiInviare) {
			if (GameManager.GameOver) {
				if (GameManager.StringGameOver.equals("Morte") || GameManager.StringGameOver.equals("Caduta")) {
					Menu.getUserInput().setBloccaTutto(false);
					GameManager.Win = false;
				}
				screenGameOver.getStage().act();
				screenGameOver.getStage().draw();
				end = true;

				tempo += delta;
				if (tempo >= 5) {
					dispose();
					Gdx.input.setCursorCatched(false);
					GameManager.GameOver = false;
					attacksAndConquers.disegnoName = true;
					 
					GameManager.StringGameOver = "";
					attacksAndConquers.setScreen(attacksAndConquers.getLoading().getMainMenuScreen());
				}

			} else if (!GameManager.GameOver && end) {
				dispose();
				Gdx.input.setCursorCatched(false);
				GameManager.GameOver = false;
				attacksAndConquers.disegnoName = true;
				Menu.getUserInput().setBloccaTutto(true);
				GameManager.StringGameOver = "";
				attacksAndConquers.setScreen(attacksAndConquers.getLoading().getMainMenuScreen());
			}

			if (GameManager.Win) {
				Menu.getUserInput().setBloccaTutto(false);
				screenWin.getStage().act();
				screenWin.getStage().draw();

				tempo += delta;
				if (tempo >= 5) {
					dispose();
					Gdx.input.setCursorCatched(false);
					GameManager.Win = false;
					Menu.getUserInput().setBloccaTutto(true);
					attacksAndConquers.disegnoName = true;
					attacksAndConquers.setScreen(attacksAndConquers.getLoading().getMainMenuScreen());
				}

			}
		} else {
			// Se é MultiPlayer
			if (Player.c.ID != 0 && MultiPlayerSettings.IniziaControlli) {

				if (GameManager.GameOver) {
					if (GameManager.StringGameOver.equals("Morte") || GameManager.StringGameOver.equals("Caduta")) {
						Menu.getUserInput().setBloccaTutto(false);
						GameManager.Win = false;
					}
					screenGameOver.getStage().act();
					screenGameOver.getStage().draw();
					end = true;

					tempo += delta;
					if (tempo >= 5) {
						dispose();
						Gdx.input.setCursorCatched(false);
						GameManager.GameOver = false;
						GameManager.StringGameOver = "";
						 
						attacksAndConquers.disegnoName = true;

						Player.c.output.close();
						try {
							Player.c.input.close();
							Player.c.MyClient.close();
							Player.c.MyClient = null;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						MultiPlayerSettings.Resetta();
						attacksAndConquers.setScreen(attacksAndConquers.getLoading().getMainMenuScreen());
					}
				} else if (GameManager.Win) {
					
					Menu.getUserInput().setBloccaTutto(false);
					//System.out.println("Gamewin-" + (GameManager.Win && !MultiPlayerSettings.PuoiInviare));
					MultiPlayerSettings.Termina = true;
					screenWin.getStage().act();
					screenWin.getStage().draw();

					tempo += delta;
					if (tempo >= 5) {
						dispose();
						Gdx.input.setCursorCatched(false);
						GameManager.Win = false;
						attacksAndConquers.disegnoName = true;
						 
						MultiPlayerSettings.Resetta();
						Player.c.output.close();
						try {
							Player.c.input.close();
							Player.c.MyClient.close();
							Player.c.MyClient = null;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}


						attacksAndConquers.setScreen(attacksAndConquers.getLoading().getMainMenuScreen());
					}

				}
			}
		}
	}

	public void setMultiPlayerScreen(MultiPlayerScreen multiPlayerScreen) {
		this.multiPlayerScreen = multiPlayerScreen;
	}

	public MultiPlayerScreen getMultiPlayerScreen() {
		return multiPlayerScreen;
	}

	public void update(float dt) {

		Sky.update(cam.position);
	}

	/* RENDER MULTIPLAYER */
	public void updateMultiPlayer(float dt) {

		if (MultiPlayerSettings.MultiPlayer) {

			modelBatch.render(InstanzeAlleati());
			for (Compagno c : DynamicElements.Alleati) {
				c.ControlloAnimazione.update(dt);
			}

			// System.out.println("Renderizzoi cazzodi amici");
		}
	}

	public ArrayList<ModelInstance> InstanzeAlleati() {
		ArrayList<ModelInstance> l = new ArrayList<ModelInstance>();

		for (Compagno a : DynamicElements.Alleati) {
			l.add(a.Instance);
		}

		return l;
	}

	public ArrayList<ModelInstance> InstanzeDeiModelliUsati() {
		ArrayList<ModelInstance> l = new ArrayList<ModelInstance>();

		for (int i = 0; i < StaticElements.Oggetti.size(); i++) {
			l.add(StaticElements.Oggetti.get(i).getInstance());

		}
		return l;

	}

	public ArrayList<ModelInstance> InstanzeDeiNemici() {

		ArrayList<ModelInstance> l = new ArrayList<ModelInstance>();
		boolean tuttiMorti = true;
		for (int i = 0; i < DynamicElements.Nemici.size(); i++) {
			tuttiMorti = tuttiMorti && DynamicElements.Nemici.get(i).Morto;
			l.add(DynamicElements.Nemici.get(i).Instance);
			DynamicElements.Nemici.get(i).Anc.update(Gdx.graphics.getDeltaTime());
		}
		
		if(MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.NemiciCaricati){
			if (tuttiMorti) {
				GameManager.Win = true;
				DynamicElements.Nemici.clear();
			}
		}
		else if(!MultiPlayerSettings.MultiPlayer){
			if (tuttiMorti) {
				GameManager.Win = true;
				DynamicElements.Nemici.clear();
			}
		}

		
		return l;
		
		

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

		System.out.println("pause");
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {

	}

	public void DisegnaProiettileCompagno() {

		Iterator<PosDirIDSparo> j = DynamicElements.ProiettileDaVisuallizzare.iterator();

		while (j.hasNext()) {
			PosDirIDSparo f = j.next();

			DynamicElements.GestoreProiettile.add(new Proiettile(f.Pos, f.Dir, f.Chi));

			j.remove();

		}

	}

	public void CreaDisegnaProiettileDelNemico() {
		for (Enemy e : DynamicElements.Nemici) {

			if (e.RichiestaDiProiettile) {

				DynamicElements.GestoreProiettile.add(new Proiettile(e.MiaPos, e.MiaDir.nor(), "EN"));

				// TODO VedereSe Qui va bene oppure deve farlo nel nemico

				e.RichiestaDiProiettile = false;
				// e.notify();
			}

		}

	}

	public ArrayList<ModelInstance> InstanzeDeiProiettili(float dt) {

		ArrayList<ModelInstance> l = new ArrayList<ModelInstance>();
		l.clear();

		Iterator<Proiettile> It = DynamicElements.GestoreProiettile.iterator();

		while (It.hasNext()) {
			Proiettile p = It.next();

			if (!p.Attivo) {
				It.remove();
			}

		}

		for (Proiettile p : DynamicElements.GestoreProiettile) {

			l.add(p.Instance);
			p.update(dt);

		}
		// System.out.println(l.size());

		return l;

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		//GameManager.mp3Shot.dispose();
		//GameManager.mp3NoGuns.dispose();
		// Menu.getUserInput().dispose();
	}

}
