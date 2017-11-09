package mondo3D;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import AttacksAndConquers.BoundingBox3D;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;
import Grafica.AboutPlayer;
import Grafica.DynamicElements;
import MultiPlayer.Client;
import MultiPlayer.Decodificatore;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.ProtocolString;
import UserGameInput.StringAnimation;

public class Player {

	public Model Modello; // Medello Dell'oggetto Statico
	public BoundingBox3D BBModel; // BoundingBox
	public ModelInstance Instance; // Istanza Del Modello

	public static AboutPlayer Ap;

	public AnimationController ControlloAnimazione;
	private static int Vita;

	public static int Munizioni;

	public static Client c;

	public static Decodificatore d;

	public Player() {
		
		GameManager.mp3Shot = Gdx.audio.newSound(Gdx.files.internal("Music/Shot.mp3"));
		GameManager.mp3NoGuns = Gdx.audio.newSound(Gdx.files.internal("Music/NoGuns.mp3"));
		
		Instance = new ModelInstance(Risorse.assets.get(Risorse.Player, Model.class));

		Instance.transform.setToTranslation(GameManager.InitialPlayer);

		BoundingBox b = new BoundingBox();
		Instance.calculateBoundingBox(b);

		Vector3 dim = new Vector3();
		b.getDimensions(dim);

		BBModel = new BoundingBox3D(-5f, 0f, 5f, dim.x - 31f, dim.y, dim.z - 10f);

		setVita(GameManager.MaxVitaPlayer);
		Munizioni = GameManager.MaxMunizioniPlayer;

		

		ControlloAnimazione = new AnimationController(Instance);
		ControlloAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);
		
		DynamicElements.Giocatore= this;
		Ap = new AboutPlayer();
		/* MULTIPLAYER */

		if (MultiPlayerSettings.MultiPlayer) {

			// c = new Client("192.168.137.1", 2107);
			
				c.start();
				d = new Decodificatore();
				c.d = d;
		
		
		}

	}

	public void Spara() {

		if (Munizioni != 0) {
			if(GameManager.EffectActive)
				GameManager.mp3Shot.play();
			
			Proiettile p;
			p = new Proiettile(GameManager.PositionPlayer, GameManager.DirectionForFire, "PL");
	
			DynamicElements.GestoreProiettile.add(p);
			Munizioni--;

			if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

				c.output.println(d.CodificaInformazioneProiettile(c.ID, GameManager.AttualDirectionPlayer));
				c.output.flush();
			}
		}
		else{
			if(GameManager.EffectActive)
				GameManager.mp3NoGuns.play();
		}

	}

	public void forseSonoMorto() {
		if (getVita() <= 0) {
			ControlloAnimazione.setAnimation(StringAnimation.MorteInPiedi);
			setVita(0);
			GameManager.StringGameOver = "Morte";
			GameManager.GameOver = true;
			for (Enemy e : DynamicElements.Nemici) {
				e.Morto = true;
			}
			
			if(MultiPlayerSettings.MultiPlayer)
			{
				MultiPlayerSettings.Termina = true;
				Player.c.output.println(Player.c.ID+"#"+ProtocolString.SonoMorto+"#");
				Player.c.output.flush();
				
			}
		}
	}

	public static void setVita(int vita) {
		Vita = vita;
	}

	public static void setMunizioni(int munizioni) {
		Munizioni = munizioni;
	}

	public int getVita() {
		return Vita;
	}
}
