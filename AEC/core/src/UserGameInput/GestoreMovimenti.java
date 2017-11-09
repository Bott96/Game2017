package UserGameInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;
import Grafica.OggettoStatico;
import Grafica.StaticElements;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.ProtocolString;
import mondo3D.Player;

public class GestoreMovimenti {

	private PerspectiveCamera cam;

	public GestoreMovimenti(PerspectiveCamera c) {
		cam = c;

	}

	public void Avanti(float Speed, Quaternion a) {

		Vector3 vDir = new Vector3(cam.direction.cpy());
		vDir.nor();

		Vector3 posAttuale = new Vector3();

		DynamicElements.Giocatore.Instance.transform.getTranslation(posAttuale);

		Vector3 NuovaPos = new Vector3(posAttuale.cpy().add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));

		Vector3 OldPosBB = new Vector3(DynamicElements.Giocatore.BBModel.getCoord());

		DynamicElements.Giocatore.BBModel.setNewCoord(NuovaPos);

		boolean Collide = false;
		boolean PossoPrendere = false;
		OggettoStatico DaRimuovere = null;
		boolean Terreno = true;

		for (OggettoStatico o : StaticElements.Oggetti) {
			if (o.oggetto != EnumTipoDiOggetto.Terreno && DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				if (o.oggetto == EnumTipoDiOggetto.PrimoSoccorso || o.oggetto == EnumTipoDiOggetto.Munizioni) {
					PossoPrendere = true;
					DaRimuovere = o;
				}
				Collide = true;
				break;
			} else if (o.oggetto == EnumTipoDiOggetto.Terreno
					&& !DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				Terreno = false;
				break;
			}
		}

		if (Terreno) {

			if (!Collide) {

				// posAttuale.add(MantieniY(vDir.nor()).scl(Speed *
				// Gdx.graphics.getDeltaTime()));
				posAttuale = NuovaPos;
				GameManager.AttualCameraPositionNoFirstPerson = GameManager.AttualCameraPositionNoFirstPerson.cpy()
						.add(MantieniY(vDir.nor()).scl(Speed * Gdx.graphics.getDeltaTime()));

				// DynamicElements.Giocatore.Instance.transform.setToTranslation(posAttuale);

				DynamicElements.Giocatore.Instance.transform.set(posAttuale, a);

				cam.position.add(MantieniY(vDir.nor()).scl(Speed * Gdx.graphics.getDeltaTime()));

				cam.update();
			} else if (Collide && PossoPrendere) {

				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					int posDaMandare = 0;
					for (int i = 0; i < StaticElements.Oggetti.size(); i++) {
						if (StaticElements.Oggetti.get(i).equals(DaRimuovere)) {
							posDaMandare = i;
							break;
						}
					}

					String s = new String();
					s += Player.c.ID + "#" + ProtocolString.RaccogliOggetto + "#" + posDaMandare;
					Player.c.output.println(s);
					Player.c.output.flush();

				}

				StaticElements.Oggetti.remove(DaRimuovere);

				if (DaRimuovere.oggetto == EnumTipoDiOggetto.PrimoSoccorso) {
					if (DynamicElements.Giocatore.getVita() < GameManager.MaxVitaPlayer) {
						if (DynamicElements.Giocatore.getVita()
								+ GameManager.AggiungoVita < GameManager.MaxMunizioniPlayer) {
							int x = (DynamicElements.Giocatore.getVita() + GameManager.AggiungoVita);
							Player.setVita(x);

						} else {
							int x = GameManager.MaxVitaPlayer;
							Player.setVita(x);
						}
					}
				} else if (DaRimuovere.oggetto == EnumTipoDiOggetto.Munizioni) {
					if (Player.Munizioni < GameManager.MaxMunizioniPlayer) {
						if (Player.Munizioni + GameManager.AggiungoMunizioni < GameManager.MaxMunizioniPlayer) {
							int x = Player.Munizioni + GameManager.AggiungoMunizioni;
							Player.setMunizioni(x);

						} else {
							int x = GameManager.MaxMunizioniPlayer;
							Player.setMunizioni(x);
						}
					}
				}

			} else
				DynamicElements.Giocatore.BBModel.setNewCoord(OldPosBB);

			DynamicElements.Giocatore.Instance.transform.getTranslation(GameManager.PositionPlayer);
			GameManager.AttualDirectionPlayer = MantieniY(vDir);

		} else {

			GameManager.GameOver = true;
			GameManager.StringGameOver = "Caduta";
			CadutaLibera c = new CadutaLibera(NuovaPos, cam.direction.cpy());
			c.start();
		}
	}

	/** FINE AVANTI **/

	public void Destra(float Speed, Quaternion a) {

		Vector3 vDir = new Vector3(cam.direction.cpy().nor());
		vDir.rotate(new Vector3(0, -1, 0), 90);

		Vector3 posAttuale = new Vector3();

		// DynamicElements.Player.Instance.transform.scale(0.5f, 0.5f,
		// 0.5f);
		DynamicElements.Giocatore.Instance.transform.getTranslation(posAttuale);

		Vector3 NuovaPos = new Vector3(posAttuale.cpy().add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));

		Vector3 OldPosBB = new Vector3(DynamicElements.Giocatore.BBModel.getCoord());

		// Vector3 NuovaPosBB = new
		// Vector3(OldPosBB.add(NuovaPos.add(Settings.CentraBBPlayer)));

		DynamicElements.Giocatore.BBModel.setNewCoord(NuovaPos);

		boolean Collide = false;
		boolean PossoPrendere = false;
		OggettoStatico DaRimuovere = null;
		boolean Terreno = true;

		for (OggettoStatico o : StaticElements.Oggetti) {
			if (o.oggetto != EnumTipoDiOggetto.Terreno && DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				if (o.oggetto == EnumTipoDiOggetto.PrimoSoccorso || o.oggetto == EnumTipoDiOggetto.Munizioni) {
					PossoPrendere = true;
					DaRimuovere = o;
				}
				Collide = true;
				break;
			} else if (o.oggetto == EnumTipoDiOggetto.Terreno
					&& !DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				Terreno = false;
				break;
			}
		}

		if (Terreno) {
			if (!Collide) {

				posAttuale.add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				cam.position.set(cam.position.cpy().add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));
				GameManager.AttualCameraPositionNoFirstPerson = GameManager.AttualCameraPositionNoFirstPerson.cpy()
						.add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				DynamicElements.Giocatore.Instance.transform.set(posAttuale, a);

				// DynamicElements.Giocatore.Instance.transform.scale(0.5f,
				// 0.5f,
				// 0.5f);
				cam.update();
			} else if (Collide && PossoPrendere) {
				
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					int posDaMandare = 0;
					for (int i = 0; i < StaticElements.Oggetti.size(); i++) {
						if (StaticElements.Oggetti.get(i).equals(DaRimuovere)) {
							posDaMandare = i;
							break;
						}
					}

					String s = new String();
					s += Player.c.ID + "#" + ProtocolString.RaccogliOggetto + "#" + posDaMandare;
					Player.c.output.println(s);
					Player.c.output.flush();

				}
				
				StaticElements.Oggetti.remove(DaRimuovere);

				if (DaRimuovere.oggetto == EnumTipoDiOggetto.PrimoSoccorso) {
					if ((DynamicElements.Giocatore.getVita() < GameManager.MaxVitaPlayer)) {
						if ((DynamicElements.Giocatore.getVita()
								+ GameManager.AggiungoVita < GameManager.MaxMunizioniPlayer)) {
							int x = (DynamicElements.Giocatore.getVita() + GameManager.AggiungoVita);
							Player.setVita(x);

						} else {
							int x = GameManager.MaxVitaPlayer;
							Player.setVita(x);
						}
					}
				} else if (DaRimuovere.oggetto == EnumTipoDiOggetto.Munizioni) {
					if (Player.Munizioni < GameManager.MaxMunizioniPlayer) {
						if (Player.Munizioni + GameManager.AggiungoMunizioni < GameManager.MaxMunizioniPlayer) {
							int x = Player.Munizioni + GameManager.AggiungoMunizioni;
							Player.setMunizioni(x);

						} else {
							int x = GameManager.MaxMunizioniPlayer;
							Player.setMunizioni(x);
						}
					}
				}

			} else
				DynamicElements.Giocatore.BBModel.setNewCoord(OldPosBB);

			DynamicElements.Giocatore.Instance.transform.getTranslation(GameManager.PositionPlayer);

			GameManager.AttualDirectionPlayer = MantieniY(vDir);
		} else {
			GameManager.GameOver = true;
			GameManager.StringGameOver = "Caduta";
			CadutaLibera c = new CadutaLibera(NuovaPos, cam.direction.cpy());
			c.start();
		}
	}

	public void Sinistra(float Speed, Quaternion a) {

		Vector3 vDir = new Vector3(cam.direction.cpy().nor());
		vDir.rotate(new Vector3(0, 1, 0), 90);

		Vector3 posAttuale = new Vector3();

		// DynamicElements.Player.Instance.transform.scale(0.5f, 0.5f,
		// 0.5f);
		DynamicElements.Giocatore.Instance.transform.getTranslation(posAttuale);

		Vector3 NuovaPos = new Vector3(posAttuale.cpy().add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));

		Vector3 OldPosBB = new Vector3(DynamicElements.Giocatore.BBModel.getCoord());

		// Vector3 NuovaPosBB = new Vector3(OldPosBB.add(NuovaPos));

		DynamicElements.Giocatore.BBModel.setNewCoord(NuovaPos);

		boolean Collide = false;
		boolean PossoPrendere = false;
		OggettoStatico DaRimuovere = null;
		boolean Terreno = true;

		for (OggettoStatico o : StaticElements.Oggetti) {
			if (o.oggetto != EnumTipoDiOggetto.Terreno && DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				if (o.oggetto == EnumTipoDiOggetto.PrimoSoccorso || o.oggetto == EnumTipoDiOggetto.Munizioni) {
					PossoPrendere = true;
					DaRimuovere = o;
				}
				Collide = true;
				break;
			} else if (o.oggetto == EnumTipoDiOggetto.Terreno
					&& !DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				Terreno = false;
				break;
			}
		}

		if (Terreno) {
			if (!Collide) {

				posAttuale.add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				cam.position.set(cam.position.cpy().add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));
				GameManager.AttualCameraPositionNoFirstPerson = GameManager.AttualCameraPositionNoFirstPerson.cpy()
						.add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				DynamicElements.Giocatore.Instance.transform.set(posAttuale, a);

				// DynamicElements.Giocatore.Instance.transform.scale(0.5f,
				// 0.5f,
				// 0.5f);
				cam.update();
			} else if (Collide && PossoPrendere) {
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					int posDaMandare = 0;
					for (int i = 0; i < StaticElements.Oggetti.size(); i++) {
						if (StaticElements.Oggetti.get(i).equals(DaRimuovere)) {
							posDaMandare = i;
							break;
						}
					}

					String s = new String();
					s += Player.c.ID + "#" + ProtocolString.RaccogliOggetto + "#" + posDaMandare;
					Player.c.output.println(s);
					Player.c.output.flush();

				}
				
				StaticElements.Oggetti.remove(DaRimuovere);

				if (DaRimuovere.oggetto == EnumTipoDiOggetto.PrimoSoccorso) {
					if ((DynamicElements.Giocatore.getVita() < GameManager.MaxVitaPlayer)) {
						if ((DynamicElements.Giocatore.getVita()
								+ GameManager.AggiungoVita < GameManager.MaxMunizioniPlayer)) {
							int x = (DynamicElements.Giocatore.getVita() + GameManager.AggiungoVita);
							Player.setVita(x);

						} else {
							int x = GameManager.MaxVitaPlayer;
							Player.setVita(x);

						}
					}
				} else if (DaRimuovere.oggetto == EnumTipoDiOggetto.Munizioni) {
					if (Player.Munizioni < GameManager.MaxMunizioniPlayer) {
						if (Player.Munizioni + GameManager.AggiungoMunizioni < GameManager.MaxMunizioniPlayer) {
							int x = Player.Munizioni + GameManager.AggiungoMunizioni;
							Player.setMunizioni(x);

						} else {
							int x = GameManager.MaxMunizioniPlayer;
							Player.setMunizioni(x);

						}
					}
				}
			} else
				DynamicElements.Giocatore.BBModel.setNewCoord(OldPosBB);

			DynamicElements.Giocatore.Instance.transform.getTranslation(GameManager.PositionPlayer);

			GameManager.AttualDirectionPlayer = MantieniY(vDir);
		} else {
			GameManager.GameOver = true;
			GameManager.StringGameOver = "Caduta";
			CadutaLibera c = new CadutaLibera(NuovaPos, cam.direction.cpy());
			c.start();
		}
	}

	public void Indietro(float Speed, Quaternion a) {

		Vector3 vDir = new Vector3(cam.direction.cpy().nor());

		Vector3 posAttuale = new Vector3();

		// DynamicElements.Player.Instance.transform.scale(0.5f, 0.5f,
		// 0.5f);
		DynamicElements.Giocatore.Instance.transform.getTranslation(posAttuale);

		Vector3 NuovaPos = new Vector3(posAttuale.cpy().sub(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));

		Vector3 OldPosBB = new Vector3(DynamicElements.Giocatore.BBModel.getCoord());

		// Vector3 NuovaPosBB = new
		// Vector3(OldPosBB.add(NuovaPos.sub(Settings.CentraBBPlayer)));

		DynamicElements.Giocatore.BBModel.setNewCoord(NuovaPos);

		boolean Collide = false;
		boolean PossoPrendere = false;
		OggettoStatico DaRimuovere = null;
		boolean Terreno = true;

		for (OggettoStatico o : StaticElements.Oggetti) {
			if (o.oggetto != EnumTipoDiOggetto.Terreno && DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				if (o.oggetto == EnumTipoDiOggetto.PrimoSoccorso || o.oggetto == EnumTipoDiOggetto.Munizioni) {
					PossoPrendere = true;
					DaRimuovere = o;
				}
				Collide = true;
				break;
			} else if (o.oggetto == EnumTipoDiOggetto.Terreno
					&& !DynamicElements.Giocatore.BBModel.collide(o.BBModel)) {
				Terreno = false;
				break;
			}
		}

		if (Terreno) {
			if (!Collide) {

				posAttuale.sub(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				cam.position.set(cam.position.cpy().sub(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime())));
				GameManager.AttualCameraPositionNoFirstPerson = GameManager.AttualCameraPositionNoFirstPerson.cpy()
						.add(MantieniY(vDir).scl(Speed * Gdx.graphics.getDeltaTime()));

				DynamicElements.Giocatore.Instance.transform.set(posAttuale, a);

				// DynamicElements.Giocatore.Instance.transform.scale(0.5f,
				// 0.5f,
				// 0.5f);
				cam.update();
			} else if (Collide && PossoPrendere) {
				if (MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare) {
					int posDaMandare = 0;
					for (int i = 0; i < StaticElements.Oggetti.size(); i++) {
						if (StaticElements.Oggetti.get(i).equals(DaRimuovere)) {
							posDaMandare = i;
							break;
						}
					}

					String s = new String();
					s += Player.c.ID + "#" + ProtocolString.RaccogliOggetto + "#" + posDaMandare;
					Player.c.output.println(s);
					Player.c.output.flush();

				}
				
				StaticElements.Oggetti.remove(DaRimuovere);

				if (DaRimuovere.oggetto == EnumTipoDiOggetto.PrimoSoccorso) {
					if ((DynamicElements.Giocatore.getVita() < GameManager.MaxVitaPlayer)) {
						if ((DynamicElements.Giocatore.getVita()
								+ GameManager.AggiungoVita < GameManager.MaxMunizioniPlayer)) {
							int x = (DynamicElements.Giocatore.getVita() + GameManager.AggiungoVita);
							Player.setVita(x);

						} else {
							int x = GameManager.MaxVitaPlayer;
							Player.setVita(x);

						}
					}
				} else if (DaRimuovere.oggetto == EnumTipoDiOggetto.Munizioni) {
					if (Player.Munizioni < GameManager.MaxMunizioniPlayer) {
						if (Player.Munizioni + GameManager.AggiungoMunizioni < GameManager.MaxMunizioniPlayer) {
							int x = Player.Munizioni + GameManager.AggiungoMunizioni;
							Player.setMunizioni(x);

						} else {
							int x = GameManager.MaxMunizioniPlayer;
							Player.setMunizioni(x);

						}
					}
				}
			} else
				DynamicElements.Giocatore.BBModel.setNewCoord(OldPosBB);

			DynamicElements.Giocatore.Instance.transform.getTranslation(GameManager.PositionPlayer);

			GameManager.AttualDirectionPlayer = MantieniY(vDir);
		} else {
			GameManager.GameOver = true;
			GameManager.StringGameOver = "Caduta";
			CadutaLibera c = new CadutaLibera(NuovaPos, cam.direction.cpy());
			c.start();
			
			
		}
	}

	Vector3 MantieniY(Vector3 Avanti) {

		return new Vector3(Avanti.x, 0, Avanti.z);

	}
}
