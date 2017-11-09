package AllScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.Risorse;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.Server;

public class ServerScreen implements Screen {

	private static final int BUTTON_WIDTH = 270;
	private static final int BUTTON_HEIGHT = 50;

	private static final int STATO_BUTTON_WIDTH = 50;
	private static final int STATO_BUTTON_HEIGHT = 50;

	private static final int UNDO_BUTTON_WIDTH = 80;
	private static final int UNDO_BUTTON_HEIGHT = 80;
	private static final int UNDO_BUTTON_Y = 50;

	private static final String START = "START";

	final AttacksAndConquers attacksAndConquers;

	public static Skin skin;

	Label NumeroPorta;
	Label NumeroGiocatori;
	
	MultiPlayerScreen multiPlayerScreen;

	Texture popUp;
	Texture StatoRosso;
	Texture StatoVerde;
	Texture UndoInactive;
	Texture UndoActive;

	TextField TextPorta;
	TextField TextNumGiocatori;
	Server s;

	boolean start = false;
	boolean disegnaPopUp = false;
	float timePopUp = 0f;

	public ServerScreen(final AttacksAndConquers attacksAndConquers, MultiPlayerScreen multi) {
		multiPlayerScreen = multi;
		this.attacksAndConquers = attacksAndConquers;
		
		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.add("default", attacksAndConquers.getFont());
		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.YELLOW;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		TextPorta = new TextField("", skin);
		TextPorta.setText("2107");
		TextNumGiocatori = new TextField("2", skin);

		TextPorta.setPosition(4 * STATO_BUTTON_WIDTH + 5, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
		TextPorta.setColor(Color.ORANGE);
		TextPorta.setWidth(BUTTON_WIDTH);
		TextPorta.setHeight(40);

		TextNumGiocatori.setPosition(Gdx.graphics.getWidth() - 1.45f * BUTTON_WIDTH,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
		TextNumGiocatori.setColor(Color.ORANGE);
		TextNumGiocatori.setWidth(BUTTON_WIDTH);
		TextNumGiocatori.setHeight(40);

		attacksAndConquers.getStageS().addActor(TextPorta);
		attacksAndConquers.getStageS().addActor(TextNumGiocatori);

		TextPorta.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if (key == '\n') {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		TextNumGiocatori.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if (key == '\n') {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		LabelStyle labelStyle = new LabelStyle(attacksAndConquers.getFont(), null);
		NumeroPorta = new Label("Numero Porta", labelStyle);
		NumeroPorta.setColor(Color.YELLOW);
		NumeroGiocatori = new Label("Numero Giocatori", labelStyle);
		NumeroGiocatori.setColor(Color.YELLOW);

		NumeroPorta.setPosition(3 * STATO_BUTTON_WIDTH + 5, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);
		NumeroGiocatori.setPosition(Gdx.graphics.getWidth() - 2 * BUTTON_WIDTH,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);

		attacksAndConquers.getStageS().addActor(NumeroPorta);
		attacksAndConquers.getStageS().addActor(NumeroGiocatori);

		popUp = Risorse.assets.get(Risorse.popUp);
		StatoRosso = Risorse.assets.get(Risorse.StatoRosso);
		StatoVerde = Risorse.assets.get(Risorse.StatoVerde);
		UndoActive = Risorse.assets.get(Risorse.UndoActive);
		UndoInactive = Risorse.assets.get(Risorse.UndoInactive);

		final TextButton StartOrStop = new TextButton("START", skin);
		attacksAndConquers.getStageS().addActor(StartOrStop);
		StartOrStop.setPosition(Gdx.graphics.getWidth() / 2 - StartOrStop.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 4);
		
		StartOrStop.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);
				if (StartOrStop.getText().toString().equals(START)) {
					if(TextPorta.getText().toString().equals("") || TextNumGiocatori.getText().toString().equals("") || TextNumGiocatori.getText().toString().equals("0"))
					{
						disegnaPopUp = true;
						
					}
					else if (HaCaratteri(TextPorta.getText()) || HaCaratteri(TextNumGiocatori.getText())) {

						disegnaPopUp = true;
					} else {
						start = true;
						s = null;
						
						s = new Server(Integer.valueOf(TextPorta.getText()));
						s.start();
						MultiPlayerScreen.HoCreatoIlServer = true;
						StartOrStop.setText("STOP");
						MultiPlayerSettings.NumPlayer= Integer.valueOf(TextNumGiocatori.getText());
					}
				} else {
					s.ChiudiServer();
					start = false;
					
					StartOrStop.setText("START");
				}
			}
		});

	}

	@Override
	public void show() {

	}

	boolean HaCaratteri(String s) {

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return true;
			}
		}
		// AltrimeniSonoSoloNumeri
		return false;

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		attacksAndConquers.batch.begin();

		attacksAndConquers.scrolling.updateAndRender(delta, attacksAndConquers.batch);

		if (disegnaPopUp) {
			timePopUp += delta;
			if (timePopUp < 3) {
				attacksAndConquers.batch.draw(popUp, Gdx.graphics.getWidth() / 2 - popUp.getWidth() / 2,
						Gdx.graphics.getHeight() / 7);
			} else if (timePopUp >= 3) {
				disegnaPopUp = false;
				timePopUp = 0f;
			}
		}

		// STATO
		if (!start) {
			attacksAndConquers.batch.draw(StatoRosso, STATO_BUTTON_WIDTH,
					Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4, STATO_BUTTON_WIDTH, STATO_BUTTON_HEIGHT);
		} else {
			attacksAndConquers.batch.draw(StatoVerde, STATO_BUTTON_WIDTH,
					Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4, STATO_BUTTON_WIDTH, STATO_BUTTON_HEIGHT);
		}

		int x = UNDO_BUTTON_WIDTH + 10;
		if (Gdx.input.getX() < x + UNDO_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < UNDO_BUTTON_Y + UNDO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > UNDO_BUTTON_Y) {
			attacksAndConquers.batch.draw(UndoActive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.disegnoButtonServer = false;
				attacksAndConquers.setScreen(multiPlayerScreen);
			}
		} else {
			attacksAndConquers.batch.draw(UndoInactive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
		}

		attacksAndConquers.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
