package Grafica;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import AllScreen.MainMenuScreen;
import AttacksAndConquers.GameManager;
import MultiPlayer.Client;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.ProtocolString;
import UserGameInput.UserInput;
import mondo3D.Enemy;
import mondo3D.Player;
import mondo3D.World;

public class MenuPause {

	public UserInput i; // stage
	public final World W;
	public static Skin skin;
	public SpriteBatch batch;

	public MenuPause(PerspectiveCamera Camera, Vector3 Pos, Vector3 Rot, final World W) {

		BitmapFont font = W.attacksAndConquers.getFont();
		this.W=W;
		

		batch = new SpriteBatch();
		i = new UserInput(Camera, Pos, Rot, this);

		skin = new Skin(Gdx.files.internal("uiskin.json"));

		skin.add("default", font);

		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.fontColor = Color.ORANGE;
		textButtonStyle.down = skin.newDrawable("default", Color.YELLOW);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		
		final TextButton MuteSong = new TextButton("Mute Song", skin);
		final TextButton MuteEffect = new TextButton("Mute Effect", skin);
		TextButton Reasume = new TextButton("Reasume", skin);
		TextButton Quit = new TextButton("Quit", skin);
		TextButton Exit = new TextButton("Exit", skin);

		float OffSet = 70f;

		MuteSong.setPosition(Gdx.graphics.getWidth() / 2f - MuteSong.getWidth() / 2,
				(Gdx.graphics.getHeight() / 1.5f - OffSet) + MuteSong.getHeight() - OffSet);
		
		MuteEffect.setPosition(Gdx.graphics.getWidth() / 2f - MuteEffect.getWidth() / 2,
				(Gdx.graphics.getHeight() / 1.5f - OffSet) + MuteEffect.getHeight() - OffSet*2);

		Reasume.setPosition(Gdx.graphics.getWidth() / 2f - MuteSong.getWidth() / 2f,
				(Gdx.graphics.getHeight() / 1.5f - OffSet) + MuteSong.getHeight() - OffSet*3);

		Quit.setPosition(Gdx.graphics.getWidth() / 2f - MuteSong.getWidth() / 2f,
				(Gdx.graphics.getHeight() / 1.5f - OffSet) + Reasume.getHeight() - OffSet * 4);

		Exit.setPosition(Gdx.graphics.getWidth() / 2f - MuteSong.getWidth() / 2f,
				(Gdx.graphics.getHeight() / 1.5f - OffSet) + Quit.getHeight() - OffSet *5);

		MuteSong.setColor(Color.ORANGE);
		MuteEffect.setColor(Color.ORANGE);
		Reasume.setColor(Color.ORANGE);
		Quit.setColor(Color.ORANGE);
		Exit.setColor(Color.ORANGE);

		i.addActor(MuteSong);
		i.addActor(MuteEffect);
		i.addActor(Reasume);
		i.addActor(Quit);
		i.addActor(Exit);


		MuteSong.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(GameManager.MusicActive){
					GameManager.MusicActive=false;
					GameManager.mp3Music.pause();
					MuteSong.setText("Play Song");
				}
				else{
					GameManager.MusicActive=true;
					GameManager.mp3Music.play();
					MuteSong.setText("Mute Song");
				}
				
			}
		});
		
		MuteEffect.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(GameManager.EffectActive){
					GameManager.EffectActive=false;
					MuteEffect.setText("Play Effect");
				}
				else{
					GameManager.EffectActive=true;
					MuteEffect.setText("Mute Effect");
				}
				
			}
		});

		Reasume.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				getUserInput().keyDown(Input.Keys.ESCAPE);
			}
		});

		Quit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				W.attacksAndConquers.dispose();
				GameManager.mp3Shot.dispose();
				GameManager.mp3NoGuns.dispose();
				i.dispose();
				StaticElements.Oggetti.clear();
				for (Enemy e : DynamicElements.Nemici) {
					
					e.Vita=0;
					
				}
				DynamicElements.Nemici.clear();
				DynamicElements.Giocatore.Instance.transform.setToTranslation(GameManager.InitialPlayer);
				if(MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare)
				{
					//ComunicaUscitaPlayer
					MultiPlayerSettings.Resetta();
					Player.c.output.println(Player.c.ID+"#"+ProtocolString.HoSmessoDiGiocare+"#");
					Player.c.output.flush();
					Player.c.output.close();
					try {
						Player.c.input.close();
						Player.c.MyClient.close();
						Player.c.MyClient = null;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				DynamicElements.Alleati.clear();
				DynamicElements.Nemici.clear();
				MultiPlayerSettings.MultiPlayer = false;
				MultiPlayerSettings.PuoiInviare = false;
				W.attacksAndConquers.disegnoName = true;
				W.attacksAndConquers.setScreen(W.attacksAndConquers.getLoading().getMainMenuScreen());
			}
		});

		Exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				W.attacksAndConquers.dispose();
				
				if(MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare)
				{
					MultiPlayerSettings.Resetta();
					Player.c.output.println(Player.c.ID+"#"+ProtocolString.HoSmessoDiGiocare+"#");
					Player.c.output.flush();
					Player.c.output.close();
					try {
						Player.c.input.close();
						Player.c.MyClient.close();
						Player.c.MyClient = null;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// Risorse.assets.dispose();
				Gdx.app.exit();
			}
		});

	}

	public UserInput getUserInput() {
		return i;
	}
}
