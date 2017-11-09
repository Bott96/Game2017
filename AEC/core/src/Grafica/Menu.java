package Grafica;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import AllScreen.MainMenuScreen;
import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.EnumTipoDiOggetto;
import PutEditorOnFile.SaveLoadOnFile;
import inputEditor.MyInput;

public class Menu {

	public MyInput i; // stage

	public static Skin skin;
	public SpriteBatch batch;
	final Tree tree;

	private BitmapFont font;

	SaveLoadOnFile ScriviLeggiDaFile;

	public HashMap<Integer, Node> HNodi;

	final AttacksAndConquers attacksAndConquers;

	public Label lblStatoOperazioni;
	public Label lblTipoDiOggetto;
	
	MainMenuScreen mainMenuScreen;

	public Menu(PerspectiveCamera Camera, Vector3 Pos, Vector3 Rot, final AttacksAndConquers attacksAndConquers, MainMenuScreen main) {

		try {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/SHOWG.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 20;
			parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.!/%'()>?: ";
			font = generator.generateFont(parameter);
			parameter.size = 10;
			// smallfont = generator.generateFont(parameter);
			generator.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(Gdx.files.internal("Font/SHOWG.ttf").file().getAbsolutePath());
			System.exit(1);
		}

		this.attacksAndConquers = attacksAndConquers;

		batch = new SpriteBatch();
		i = new MyInput(Camera, Pos, Rot, this);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		HNodi = new HashMap<Integer, Node>();
		ScriviLeggiDaFile = new SaveLoadOnFile();

		skin.add("default", font);

		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.ORANGE;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		Table table = new Table();

		float x = (float) (-Gdx.graphics.getWidth() / 2.36); // -120
		float y = (float) (Gdx.graphics.getHeight() / 4); // +80
		table.setPosition(x, y);
		table.setFillParent(true);

		i.addActor(table);

		tree = new Tree(skin);

		final Node Oggetti = new Node(new TextButton("Oggetti", skin));
		final Node Munizioni = new Node(new TextButton("Munizioni", skin));
		final Node Vita = new Node(new TextButton("Vita", skin));
		final Node CarroArmato = new Node(new TextButton("Carro Armato", skin));
		final Node Cassa = new Node(new TextButton("Cassa", skin));
		final Node Cespuglio = new Node(new TextButton("Cespuglio", skin));
		final Node Albero = new Node(new TextButton("Albero", skin));

		final TextButton Load = new TextButton("Carica Livello", skin);
		final TextButton Save = new TextButton("Salva Livello", skin);
		final TextButton Ripulisci = new TextButton("Ripulisci", skin);

		final TextField TextFildNome = new TextField("", skin);
		
		LabelStyle labelStyle = new LabelStyle(font, null);
		lblStatoOperazioni = new Label("Carica, salva o scegli Modello", labelStyle);
		lblTipoDiOggetto = new Label("----", labelStyle);

		tree.add(Oggetti);
		Oggetti.add(Albero);
		Oggetti.add(Cespuglio);
		Oggetti.add(CarroArmato);
		Oggetti.add(Cassa);
		Oggetti.add(Munizioni);
		Oggetti.add(Vita);

		//System.out.println(tree.getYSpacing());
		table.add(tree);
		
		
		Save.setSize(160, 30);
		Save.setPosition(170, 20);

		TextFildNome.setPosition(10, 20);
		TextFildNome.setColor(Color.ORANGE);

		Load.setSize(160, 30);
		Load.setPosition(10, 60);

		Ripulisci.setSize(180, 30);
		Ripulisci.setPosition(Gdx.graphics.getWidth() - Ripulisci.getWidth() - 10, 10);
		
		
		lblStatoOperazioni.setPosition(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 1.09f);
		lblStatoOperazioni.setColor(Color.ORANGE);

		lblTipoDiOggetto.setPosition(Gdx.graphics.getWidth() - Ripulisci.getWidth() - 25,
				Gdx.graphics.getHeight() - 100);
		lblTipoDiOggetto.setColor(Color.ORANGE);

		i.addActor(Load);
		i.addActor(TextFildNome); // i like a stage
		i.addActor(Save);
		i.addActor(Ripulisci);
		i.addActor(lblStatoOperazioni);
		i.addActor(lblTipoDiOggetto);

		HNodi.put(123, Oggetti);
		HNodi.put(5, Albero);
		HNodi.put(6, Cespuglio);
		HNodi.put(7, CarroArmato);
		HNodi.put(8, Cassa);
		HNodi.put(9, Munizioni);
		HNodi.put(10, Vita);

		Ripulisci.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				super.touchUp(event, x, y, pointer, button);
				textButtonStyle.down=skin.newDrawable("default", Color.BLACK);
				StaticElements.Oggetti.clear();
				StaticElements.Oggetti.add(new OggettoStatico(EnumTipoDiOggetto.Terreno));

			}

		});
		Load.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);
				textButtonStyle.down=skin.newDrawable("default", Color.BLACK);
				Gdx.graphics.setWindowedMode(0, 0);
				ScriviLeggiDaFile.CaricaDaFile();
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			}

		});

		Save.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);
				textButtonStyle.down=skin.newDrawable("default", Color.BLACK);
				ScriviLeggiDaFile.SalvaSulFile(TextFildNome.getText());
				//System.out.println("SonoUscito");

			}
		});

		tree.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {

				for (int q = 5; q <= HNodi.size() + 3; q++) {
					if (tree.getNodeAt(y) == HNodi.get(q)) {
						// System.out.println("NUemro pul "+ q);
						// HNodi.get(i).toString();
						// i.oggettoPassatoDalMenu= new
						// OggettoStatico(OggettoCheVoglioPassare.getTipoDatoI(q));
						i.InteroDelMenuPassatoPerEnum = q;
						i.StoSelezionandoConRay = false;

					}

				}

			}

		});

		TextButton Undo = new TextButton("Undo", skin);
		// button.setOrigin(-Gdx.graphics.getWidth()/5,
		// Gdx.graphics.getHeight()/4);
		// table.add(button);
		Undo.setSize(80, 30);
		Undo.setPosition(12, 100);
		i.addActor(Undo);
		
		mainMenuScreen = main;
		
		Undo.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				super.touchUp(event, x, y, pointer, button);
				textButtonStyle.down=skin.newDrawable("default", Color.BLACK);
				StaticElements.Oggetti.clear();
				attacksAndConquers.disegnoName = true;
				attacksAndConquers.setScreen(mainMenuScreen);

			}
		});
		
	}

	public void clicked(Node n, float x, float y) {
		//System.out.println(n.toString());
	}

	public Skin getSkin() {
		return skin;
	}

	public MyInput getMyInput() {
		return i;
	}

}
