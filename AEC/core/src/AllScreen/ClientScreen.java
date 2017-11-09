package AllScreen;

import java.util.regex.Pattern;

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
import MultiPlayer.Client;
import MultiPlayer.MultiPlayerSettings;
import PutEditorOnFile.SaveLoadOnFile;
import mondo3D.Player;
import mondo3D.World;

public class ClientScreen implements Screen {

	private static final int BUTTON_WIDTH = 270;
	private static final int BUTTON_HEIGHT = 50;

	private static final int STATO_BUTTON_WIDTH = 50;
	private static final int STATO_BUTTON_HEIGHT = 50;

	private static final int UNDO_BUTTON_WIDTH = 80;
	private static final int UNDO_BUTTON_HEIGHT = 80;
	private static final int UNDO_BUTTON_Y = 50;

	private static final String CONNETTI = "CONNETTI";

	final AttacksAndConquers attacksAndConquers;
	MultiPlayerScreen multiPlayerScreen;

	public static Skin skin;

	Label IndirizzoIP;
	Label NumeroPorta;

	Texture popUp;
	Texture StatoRosso;
	Texture StatoVerde;
	Texture UndoInactive;
	Texture UndoActive;

	TextField TextIndirizzoIp;
	TextField TextNumPorta;
	SaveLoadOnFile operazioniSulFile;

	boolean connetti = false;
	boolean disegnaPopUp = false;
	float timePopUp = 0f;

	public ClientScreen(final AttacksAndConquers attacksAndConquers, MultiPlayerScreen multi) {

		multiPlayerScreen = multi;
		operazioniSulFile = new SaveLoadOnFile();

		this.attacksAndConquers = attacksAndConquers;

		attacksAndConquers.scrolling.setSpeedFixed(true);
		attacksAndConquers.scrolling.setSpeed(ScrollingBackground.DEFAULT_SPEED);

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		skin.add("default", attacksAndConquers.getFont());
		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.YELLOW;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		TextIndirizzoIp = new TextField("", skin);
		TextNumPorta = new TextField("", skin);
		TextIndirizzoIp.setText("127.0.0.1");
		TextNumPorta.setText("2107");
		TextIndirizzoIp.setPosition(3 * STATO_BUTTON_WIDTH + 5,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
		TextIndirizzoIp.setColor(Color.ORANGE);
		TextIndirizzoIp.setWidth(BUTTON_WIDTH);
		TextIndirizzoIp.setHeight(40);

		TextNumPorta.setPosition(Gdx.graphics.getWidth() - 2 * BUTTON_WIDTH,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2.5f);
		TextNumPorta.setColor(Color.ORANGE);
		TextNumPorta.setWidth(BUTTON_WIDTH);
		TextNumPorta.setHeight(40);

		attacksAndConquers.getStageC().addActor(TextIndirizzoIp);
		attacksAndConquers.getStageC().addActor(TextNumPorta);

		TextIndirizzoIp.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if (key == '\n') {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		TextNumPorta.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if (key == '\n') {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		LabelStyle labelStyle = new LabelStyle(attacksAndConquers.getFont(), null);
		IndirizzoIP = new Label("Indirizzo IP", labelStyle);
		IndirizzoIP.setColor(Color.YELLOW);
		NumeroPorta = new Label("Numero Porta", labelStyle);
		NumeroPorta.setColor(Color.YELLOW);

		IndirizzoIP.setPosition(3 * STATO_BUTTON_WIDTH + 5, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);
		NumeroPorta.setPosition(Gdx.graphics.getWidth() - 2 * BUTTON_WIDTH,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 3);

		attacksAndConquers.getStageC().addActor(IndirizzoIP);
		attacksAndConquers.getStageC().addActor(NumeroPorta);

		popUp = Risorse.assets.get(Risorse.popUp);
		StatoRosso = Risorse.assets.get(Risorse.StatoRosso);
		StatoVerde = Risorse.assets.get(Risorse.StatoVerde);
		UndoActive = Risorse.assets.get(Risorse.UndoActive);
		UndoInactive = Risorse.assets.get(Risorse.UndoInactive);

		final TextButton ConnettoOrDisconnetti = new TextButton("CONNETTI", skin);

		if (!connetti)
			ConnettoOrDisconnetti.setText("CONNETTI");

		attacksAndConquers.getStageC().addActor(ConnettoOrDisconnetti);
		ConnettoOrDisconnetti.setPosition(Gdx.graphics.getWidth() / 2 - ConnettoOrDisconnetti.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 4);

		ConnettoOrDisconnetti.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);

				if (ConnettoOrDisconnetti.getText().toString().equals(CONNETTI)) {

					if (TextIndirizzoIp.getText().toString().equals("")
							|| TextNumPorta.getText().toString().equals("")) {
						disegnaPopUp = true;
					} else if (!ipValido(TextIndirizzoIp.getText()) || HaCaratteri(TextNumPorta.getText())) {

						disegnaPopUp = true;
					} else {
						connetti = true;

						Player.c = null;
						MultiPlayerSettings.MultiPlayer = true;
						MultiPlayerSettings.PuoiInviare = false;
						MultiPlayerSettings.IniziaControlli = false;
						Player.c = new Client(TextIndirizzoIp.getText(), Integer.valueOf(TextNumPorta.getText()));

						if (Player.c.MyClient.isConnected()) {
							attacksAndConquers.disegnoButtonClient = false;

							operazioniSulFile.CaricaLivelloDiDefault();

							attacksAndConquers.disegnoName = false;
							connetti = false;
							attacksAndConquers.setScreen(new World(attacksAndConquers));

						} else {
							disegnaPopUp = true;
						}

					}
				} else {
					connetti = false;
					ConnettoOrDisconnetti.setText("CONNETTI");
				}
			}

		});

		if (MultiPlayerSettings.Termina) {
			ConnettoOrDisconnetti.setText("CONNETTI");
		}

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

	private static final Pattern PATTERN = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	boolean ipValido(String s) {

		return PATTERN.matcher(s).matches();
		// AltrimeniSonoSoloNumeri

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
				// System.out.println("ci entro");
				attacksAndConquers.batch.draw(popUp, Gdx.graphics.getWidth() / 2 - popUp.getWidth() / 2,
						Gdx.graphics.getHeight() / 7);
			} else if (timePopUp >= 3) {
				disegnaPopUp = false;
				timePopUp = 0f;
			}
		}

		// STATO

		attacksAndConquers.batch.draw(StatoRosso, STATO_BUTTON_WIDTH,
				Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4, STATO_BUTTON_WIDTH, STATO_BUTTON_HEIGHT);

		int x = UNDO_BUTTON_WIDTH + 10;
		if (Gdx.input.getX() < x + UNDO_BUTTON_WIDTH && Gdx.input.getX() > x
				&& attacksAndConquers.height - Gdx.input.getY() < UNDO_BUTTON_Y + UNDO_BUTTON_HEIGHT
				&& attacksAndConquers.height - Gdx.input.getY() > UNDO_BUTTON_Y) {
			attacksAndConquers.batch.draw(UndoActive, x, UNDO_BUTTON_Y, UNDO_BUTTON_WIDTH, UNDO_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				attacksAndConquers.dispose();
				attacksAndConquers.disegnoButtonClient = false;
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
