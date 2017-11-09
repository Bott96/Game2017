package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;

public class LoadingScreen extends ScreenAdapter {

	private static final float PROGRESS_BAR_WIDTH = Gdx.graphics.getWidth() / 2f;
	private static final float PROGRESS_BAR_HEIGHT = 10f;

	private BitmapFont font;
	private CharSequence str;
	private String avanzamento = "";
	private ShapeRenderer shapeRenderer;
	MainMenuScreen mainMenuScreen;
	final AttacksAndConquers attacksAndConquers;

	public LoadingScreen(final AttacksAndConquers attacksAndConquers) {

		this.attacksAndConquers = attacksAndConquers;
		
		shapeRenderer = new ShapeRenderer();
		Risorse.assets = new AssetManager();
		
		
	}

	@Override
	public void show() {
		super.show();
		
		font = attacksAndConquers.getFont();
		font.setColor(Color.ORANGE);
		str = "Loading...";

		Risorse.assets.load(Risorse.ExitInactive, Texture.class);
		Risorse.assets.load(Risorse.ExitActive, Texture.class);
		Risorse.assets.load(Risorse.UndoInactive, Texture.class);
		Risorse.assets.load(Risorse.UndoActive, Texture.class);
		Risorse.assets.load(Risorse.FacileInactive, Texture.class);
		Risorse.assets.load(Risorse.FacileActive, Texture.class);
		Risorse.assets.load(Risorse.MedioInactive, Texture.class);
		Risorse.assets.load(Risorse.MedioActive, Texture.class);
		Risorse.assets.load(Risorse.DifficileInactive, Texture.class);
		Risorse.assets.load(Risorse.DifficileActive, Texture.class);
		Risorse.assets.load(Risorse.SinglePlayerButtonActive, Texture.class);
		Risorse.assets.load(Risorse.SinglePlayerButtonInactive, Texture.class);
		Risorse.assets.load(Risorse.MultiPlayerButtonActive, Texture.class);
		Risorse.assets.load(Risorse.MultiPlayerButtonInactive, Texture.class);
		Risorse.assets.load(Risorse.MakeALevelInactive, Texture.class);
		Risorse.assets.load(Risorse.MakeALevelActive, Texture.class);
		Risorse.assets.load(Risorse.OpzioneActive, Texture.class);
		Risorse.assets.load(Risorse.OpzioneInactive, Texture.class);
		Risorse.assets.load(Risorse.InfoInactive, Texture.class);
		Risorse.assets.load(Risorse.InfoActive, Texture.class);
		Risorse.assets.load(Risorse.ServerInactive, Texture.class);
		Risorse.assets.load(Risorse.ServerActive, Texture.class);
		Risorse.assets.load(Risorse.ClientInactive, Texture.class);
		Risorse.assets.load(Risorse.ClientActive, Texture.class);
		Risorse.assets.load(Risorse.DefaultInactive, Texture.class);
		Risorse.assets.load(Risorse.DefaultActive, Texture.class);
		Risorse.assets.load(Risorse.CustomInactive, Texture.class);
		Risorse.assets.load(Risorse.CustomActive, Texture.class);
		Risorse.assets.load(Risorse.PlayActive, Texture.class);
		Risorse.assets.load(Risorse.PlayInactive, Texture.class);
		Risorse.assets.load(Risorse.MuteActive, Texture.class);
		Risorse.assets.load(Risorse.MuteInactive, Texture.class);
		Risorse.assets.load(Risorse.QuitActive, Texture.class);
		Risorse.assets.load(Risorse.QuitInactive, Texture.class);
		Risorse.assets.load(Risorse.Note, Texture.class);
		
		
		Risorse.assets.load(Risorse.Player, Model.class);
		Risorse.assets.load(Risorse.Casse, Model.class);
		Risorse.assets.load(Risorse.Cespuglio, Model.class);
		Risorse.assets.load(Risorse.Munizioni, Model.class);
		Risorse.assets.load(Risorse.PrimoSoccorso, Model.class);
		Risorse.assets.load(Risorse.SkyBox, Model.class);
		Risorse.assets.load(Risorse.Tank, Model.class);
		Risorse.assets.load(Risorse.Tree, Model.class);
		Risorse.assets.load(Risorse.SkyBox, Model.class);

		Risorse.assets.load(Risorse.xpos, Texture.class);
		Risorse.assets.load(Risorse.xneg, Texture.class);
		Risorse.assets.load(Risorse.ypos, Texture.class);
		Risorse.assets.load(Risorse.yneg, Texture.class);
		Risorse.assets.load(Risorse.zneg, Texture.class);
		Risorse.assets.load(Risorse.zpos, Texture.class);

		Risorse.assets.load(Risorse.Radar, Texture.class);
		Risorse.assets.load(Risorse.CarroMiniMappa, Texture.class);
		Risorse.assets.load(Risorse.CassaMiniMappa, Texture.class);
		Risorse.assets.load(Risorse.AlberoMiniMappa, Texture.class);
		
		Risorse.assets.load(Risorse.UnoSingleP, Texture.class);
		Risorse.assets.load(Risorse.DueSingleP, Texture.class);
		Risorse.assets.load(Risorse.TreSingleP, Texture.class);
		Risorse.assets.load(Risorse.QuattroSingleP, Texture.class);
		Risorse.assets.load(Risorse.CinqueSingleP, Texture.class);
		Risorse.assets.load(Risorse.UnoEditor, Texture.class);
		Risorse.assets.load(Risorse.DueEditor, Texture.class);
		Risorse.assets.load(Risorse.TreEditor, Texture.class);
		Risorse.assets.load(Risorse.QuattroEditor, Texture.class);
		Risorse.assets.load(Risorse.UnoMulti, Texture.class);
		Risorse.assets.load(Risorse.FrecciaAvantiActive, Texture.class);
		Risorse.assets.load(Risorse.FrecciaAvantiInactive, Texture.class);
		Risorse.assets.load(Risorse.FrecciaIndietroInactive, Texture.class);
		Risorse.assets.load(Risorse.FrecciaIndietroActive, Texture.class);
		Risorse.assets.load(Risorse.UndoInfoActive, Texture.class);
		Risorse.assets.load(Risorse.UndoInfoInactive, Texture.class);
		
		Risorse.assets.load(Risorse.popUp, Texture.class);
		Risorse.assets.load(Risorse.StatoRosso, Texture.class);
		Risorse.assets.load(Risorse.StatoVerde, Texture.class);
		
		Risorse.assets.load(Risorse.TextureTerreno, Texture.class);

	}

	private void draw() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		attacksAndConquers.batch.begin();

		font.draw(attacksAndConquers.batch, str, Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 3);
		font.draw(attacksAndConquers.batch, avanzamento, (Gdx.graphics.getWidth() / 1.55f + PROGRESS_BAR_WIDTH / 4f),
				Gdx.graphics.getHeight() / 3.5f);

		attacksAndConquers.batch.end();
		renderProgressBar();

	}

	private void renderProgressBar() {

		float progress = Risorse.assets.getProgress();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // disegno il
															// contorno della
															// barra
		shapeRenderer.setColor(Color.ORANGE);
		// shapeRenderer.rect((Gdx.graphics.getWidth()/1.5f
		// -PROGRESS_BAR_WIDTH/5f)/2f,
		// (Gdx.graphics.getHeight())/4f, PROGRESS_BAR_WIDTH,
		// PROGRESS_BAR_HEIGHT);
		shapeRenderer.rect((Gdx.graphics.getWidth() / 1.75f - PROGRESS_BAR_WIDTH / 5f) / 2f,
				(Gdx.graphics.getHeight()) / 4f, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);

		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // lo ridisegno un po
															// spostato e più
															// grande per
															// rendere il
															// contorno più
															// grosso
		shapeRenderer.setColor(Color.ORANGE);
		shapeRenderer.rect(((Gdx.graphics.getWidth() / 1.75f - PROGRESS_BAR_WIDTH / 5f) / 2f) - 1,
				((Gdx.graphics.getHeight()) / 4f) - 1, PROGRESS_BAR_WIDTH + 2, PROGRESS_BAR_HEIGHT + 2);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // disegno la
		// progress bar

		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.rect((Gdx.graphics.getWidth() / 1.75f - PROGRESS_BAR_WIDTH / 5f) / 2f,
				((Gdx.graphics.getHeight()) / 4f), PROGRESS_BAR_WIDTH * progress, PROGRESS_BAR_HEIGHT);
		avanzamento = "";
		avanzamento = ((int) (progress * 100)) + "%";
		shapeRenderer.end();
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		draw();
		if (Risorse.assets.update()) {
			this.dispose();
			mainMenuScreen = new MainMenuScreen(attacksAndConquers);
			attacksAndConquers.setScreen(mainMenuScreen);
		}

		// attacksAndConquers.batch.end();

	}

	@Override
	public void dispose() {

		shapeRenderer.dispose();
		Gdx.input.setInputProcessor(null);

	}
	
	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

}
