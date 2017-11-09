package UserGameInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;
import Grafica.MenuPause;
import MultiPlayer.MultiPlayerSettings;
import mondo3D.Player;

public class UserInput extends Stage {

	PerspectiveCamera cam;
	Vector3 pos1;
	Vector3 rot1;
	Vector3 Perp;
	float AngoloRotazione = 0;

	static AnimationController ControllerAnimazione;
	public GestoreMovimenti CalcolaPosizioni;
	MenuPause Menu;
	Quaternion Ma4;

	// private float rotSpeed = 0.8f;
	private int mouseX = 0;
	// private int mouseY = 0;
	// private int lastPosMouseX = 0;
	// private int lastPosMouseY = 0;

	private boolean CursoreNonAttivo; // indica se vogliamo il menu
	private boolean BloccaTutto;
	
	static final float CAM_PATH_RADIUS = 5f;
	static final float CAM_HEIGHT = 2f;
	float camPathAngle = 0;

	public UserInput() {
		super();
	}

	public UserInput(PerspectiveCamera Camera, Vector3 Pos, Vector3 Rot, MenuPause M) {

		super();

		GameManager.mp3Walk = Gdx.audio.newMusic(Gdx.files.internal("Music/Walking - on.mp3"));
		GameManager.mp3Run = Gdx.audio.newMusic(Gdx.files.internal("Music/Running.mp3"));
		BloccaTutto=true;
		CursoreNonAttivo=true;
		Ma4 = new Quaternion();
		ControllerAnimazione = DynamicElements.Giocatore.ControlloAnimazione;
		CalcolaPosizioni = new GestoreMovimenti(Camera);

		// Gdx.input.setCursorPosition(0, 0);
		Gdx.input.setCursorCatched(CursoreNonAttivo);

		this.cam = Camera;
		rot1 = Rot;

		this.pos1 = Pos;

		Menu = M;

		cam.position.set(Pos);
		cam.update();

		Vector3 Po = new Vector3();
		DynamicElements.Giocatore.Instance.transform.getTranslation(Po);

		// System.out.println("posP I " + Po);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// System.out.println("asdasd");

		if (CursoreNonAttivo && BloccaTutto) {

			if (!GameManager.PrimaPersona) {

				Vector3 Pos = new Vector3();

				DynamicElements.Giocatore.Instance.transform.getTranslation(Pos);

				// int magX = Math.abs(mouseX - screenX);

				if (mouseX > screenX) {
					cam.rotateAround(Pos, new Vector3(0, 1, 0), GameManager.DegreesRotation);

					DynamicElements.Giocatore.Instance.transform.rotate(new Vector3(0, 1, 0), GameManager.DegreesRotation);

					if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
						
						String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, null);

						Player.c.output.println(s);
						Player.c.output.flush();
						// Player.c.d.DecodificaInformazioniRicevute(s);

					}

				}

				if (mouseX < screenX) {
					cam.rotateAround(Pos, new Vector3(0, -1, 0), GameManager.DegreesRotation);

					DynamicElements.Giocatore.Instance.transform.rotate(new Vector3(0, -1, 0),
							GameManager.DegreesRotation);

					if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

						String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, null);

						Player.c.output.println(s);
						Player.c.output.flush();
						// Player.c.d.DecodificaInformazioniRicevute(s);

					}

				}
				DynamicElements.Giocatore.Instance.transform.getRotation(Ma4);
				mouseX = screenX;
				cam.update();
				GameManager.AttualDirectionPlayer = MantieniY2(cam.direction.cpy().nor());
				GameManager.DirectionForFire = MantieniY2(cam.direction.cpy().nor());

			} else {

			}

			return true;

		} else {
			// System.out.println("Mi muovo qui");
			// super.mouseMoved(screenX, screenY);

		}
		return true;

	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.ESCAPE:
			// Gdx.graphics.setWindowedMode(0, 0);
			if (CursoreNonAttivo && BloccaTutto)
				CursoreNonAttivo = false;
			else
				CursoreNonAttivo = true;
			System.out.println("esc");

			Gdx.input.setCursorCatched(CursoreNonAttivo);

			break;

		default:
			break;
		}

		return true;

	}

	@Override
	public boolean keyUp(int keycode) {

		switch (keycode) {
		case Input.Keys.ESCAPE:
			// TODO RichiamoDelle Opzioni
			break;

		case Input.Keys.W:
			if (CursoreNonAttivo && BloccaTutto) {
				GameManager.mp3Walk.stop();
				GameManager.mp3Run.stop();
				ControllerAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.AttessaInPiedi);

					Player.c.output.println(s);
					Player.c.output.flush();
				}

			}
			break;

		case Input.Keys.S:
			if (CursoreNonAttivo && BloccaTutto) {
				GameManager.mp3Walk.stop();
				GameManager.mp3Run.stop();
				ControllerAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.AttessaInPiedi);

					Player.c.output.println(s);
					Player.c.output.flush();
				}
			}
			break;

		case Input.Keys.A:
			if (CursoreNonAttivo && BloccaTutto) {
				GameManager.mp3Walk.stop();
				GameManager.mp3Run.stop();
				ControllerAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.AttessaInPiedi);

					Player.c.output.println(s);
					Player.c.output.flush();
				}
			}
			break;

		case Input.Keys.D:
			if (CursoreNonAttivo && BloccaTutto) {
				GameManager.mp3Walk.stop();
				GameManager.mp3Run.stop();
				ControllerAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.AttessaInPiedi);

					Player.c.output.println(s);
					Player.c.output.flush();
				}
			}
			break;
		}
		return true;

	}

	@Override
	public boolean keyTyped(char character) {

		switch (character) {

		case 'w':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {
				if (GameManager.EffectActive) {
					GameManager.mp3Walk.play();
					GameManager.mp3Walk.setVolume(0.25f);
				}
				CalcolaPosizioni.Avanti(GameManager.SpeedWalk, Ma4);
				ControllerAnimazione.setAnimation(StringAnimation.CamminataAvanti, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CamminataAvanti);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
			}
			// AngoloRotazione=0;

			break;
		case 's':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {
				if (GameManager.EffectActive) {
					GameManager.mp3Walk.play();
					GameManager.mp3Walk.setVolume(0.25f);
				}
				CalcolaPosizioni.Indietro(GameManager.SpeedWalk, Ma4);

				ControllerAnimazione.setAnimation(StringAnimation.CamminataIndietro, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CamminataIndietro);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
				// AngoloRotazione=0;
			}
			break;

		case 'd':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {
				if (GameManager.EffectActive) {
					GameManager.mp3Walk.play();
					GameManager.mp3Walk.setVolume(0.25f);
				}
				CalcolaPosizioni.Destra(GameManager.SpeedWalk, Ma4);

				ControllerAnimazione.setAnimation(StringAnimation.CamminataDestra, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CamminataDestra);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
			}
			break;
		case 'a':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {
				if (GameManager.EffectActive) {
					GameManager.mp3Walk.play();
					GameManager.mp3Walk.setVolume(0.25f);
				}
				CalcolaPosizioni.Sinistra(GameManager.SpeedWalk, Ma4);

				ControllerAnimazione.setAnimation(StringAnimation.CamminataSinistra, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CamminataSinistra);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
				// AngoloRotazione=0;
			}
			break;
		case 'W':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {
				if (GameManager.EffectActive) {
					GameManager.mp3Run.setVolume(0.25f);
					GameManager.mp3Run.play();
				}
				CalcolaPosizioni.Avanti(GameManager.SpeedRun, Ma4);
				ControllerAnimazione.setAnimation(StringAnimation.CorsaAvanti, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CorsaAvanti);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
			}
			// AngoloRotazione=0;

			break;
		case 'S':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {

				if (GameManager.EffectActive) {
					GameManager.mp3Run.setVolume(0.25f);
					GameManager.mp3Run.play();
				}
				CalcolaPosizioni.Indietro(GameManager.SpeedRun, Ma4);

				ControllerAnimazione.setAnimation(StringAnimation.CorsaIndietro, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CorsaIndietro);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
				// AngoloRotazione=0;
			}
			break;

		case 'D':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {

				if (GameManager.EffectActive) {
					GameManager.mp3Run.setVolume(0.25f);
					GameManager.mp3Run.play();
				}
				ControllerAnimazione.setAnimation(StringAnimation.CorsaDestra, -1);
				CalcolaPosizioni.Destra(GameManager.SpeedRun, Ma4);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CorsaDestra);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}
			}
			break;
		case 'A':
			// VettoreDirezioneCamera
			if (CursoreNonAttivo && BloccaTutto) {

				if (GameManager.EffectActive) {
					GameManager.mp3Run.setVolume(0.25f);
					GameManager.mp3Run.play();
				}
				CalcolaPosizioni.Sinistra(GameManager.SpeedRun, Ma4);

				ControllerAnimazione.setAnimation(StringAnimation.CorsaSinistra, -1);
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {

					String s = Player.c.d.CodificaInformazioneDaMandare(Player.c.ID, StringAnimation.CorsaSinistra);

					Player.c.output.println(s);
					Player.c.output.flush();
					// Player.c.d.DecodificaInformazioniRicevute(s);

				}

				// AngoloRotazione=0;
			}
			break;

		default:
			break;
		}

		GameManager.AttualCameraPosition = cam.position;
		// System.out.println(Settings.AttualCameraPosition);
		return true;

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (CursoreNonAttivo && BloccaTutto) {

			DynamicElements.Giocatore.Spara();
			return true;
		} else

			return super.touchDown(screenX, screenY, pointer, button);
	}
	
	public void setCursoreNonAttivo(boolean cursoreNonAttivo) {
		CursoreNonAttivo = cursoreNonAttivo;
	}
	
	public boolean getCursoreNonAttivo(){
		return CursoreNonAttivo;
	}
	
	public void setBloccaTutto(boolean bloccaTutto) {
		BloccaTutto = bloccaTutto;
	}
	
	private Vector3 MantieniY2(Vector3 Avanti) {

		return new Vector3(Avanti.x, 0, Avanti.z);

	}

	public void pause() {

	}

}
