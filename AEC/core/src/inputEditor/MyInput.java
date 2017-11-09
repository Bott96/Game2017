package inputEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import AttacksAndConquers.EnumTipoDiOggetto;
import Grafica.Menu;
import Grafica.OggettoStatico;
import Grafica.StaticElements;

public class MyInput extends Stage {
	/*
	 * Valgrid..
	 * 
	 */
	public MyInput() {
		super();
		// TODO Auto-generated constructor stub
	}

	PerspectiveCamera cam;
	Vector3 pos1;
	Vector3 rot1;
	Vector3 Perp;

	ModificatoreDiOggettiEditor MDOE; // Modificatore
	OggettoStatico OggettoDaModificare;
	boolean VoglioModificareOggetto = false;

	boolean Disattivata = false; // se il mio input è attivo o meno
	public boolean StoSelezionandoConRay = true; // se sto selezionado un
													// modello o lo
	// sto posizionando
	// se sto modificando il modello selezionato

	// devo passare all'input l'oggetto statico, in modo tale da poterlo ceare e
	// modificare.
	public int InteroDelMenuPassatoPerEnum;
	Menu MX;

	public MyInput(PerspectiveCamera Camera, Vector3 Pos, Vector3 Rot, Menu m) {

		super();
		MDOE = new ModificatoreDiOggettiEditor();
		MX = m;
		Gdx.input.setCursorPosition(0, 0);
		Gdx.input.setCursorCatched(Disattivata);

		this.cam = Camera;
		rot1 = Rot;

		this.pos1 = Pos;
		Perp = new Vector3(0, 0, 0);

		cam.position.set(Pos);
		cam.update();

	}

	private float rotSpeed = 0.8f;
	private int mouseX = 0;
	private int mouseY = 0;
	private int lastPosMouseX = 0;
	private int lastPosMouseY = 0;

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// System.out.println("asdasd");

		if (Disattivata) {

			int magX = Math.abs(mouseX - screenX);
			int magY = Math.abs(mouseY - screenY);

			if (mouseX > screenX) {
				cam.rotate(Vector3.Y, 1 * magX * rotSpeed);
				rot1.rotate(Vector3.Y, 1 * magX * rotSpeed);
				cam.update();
			}

			if (mouseX < screenX) {
				cam.rotate(Vector3.Y, -1 * magX * rotSpeed);
				rot1.rotate(Vector3.Y, -1 * magX * rotSpeed);
				cam.update();
			}

			if (mouseY < screenY) {
				if (cam.direction.y > -8.965) {
					cam.rotate(cam.direction.cpy().crs(Vector3.Y), -1 * magY * rotSpeed);
					rot1.rotate(cam.direction.cpy().crs(Vector3.Y), -1 * magY * rotSpeed);
				}
				cam.update();
			}

			if (mouseY > screenY) {

				if (cam.direction.y < 8.965) {
					cam.rotate(cam.direction.cpy().crs(Vector3.Y), 1 * magY * rotSpeed);
					rot1.rotate(cam.direction.cpy().crs(Vector3.Y), 1 * magY * rotSpeed);
				}
				cam.update();
			}

			mouseX = screenX;
			mouseY = screenY;

			// return false;
		} else
			super.mouseMoved(screenX, screenY);

		return true;

	}

	@Override
	public boolean keyDown(int keycode) {

		switch (keycode) {
		case Input.Keys.ESCAPE:
			VoglioModificareOggetto = false;

			if (Disattivata) {
				Disattivata = false;

				StoSelezionandoConRay = true;

				lastPosMouseX = Gdx.input.getX();
				lastPosMouseY = Gdx.input.getY();

				Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

				Gdx.input.setCursorCatched(Disattivata);

			} else {

				Disattivata = true;

				Gdx.input.setCursorCatched(Disattivata);
				Gdx.input.setCursorPosition(lastPosMouseX, lastPosMouseY);

			}

			break;

		case Input.Keys.DEL:

			if (VoglioModificareOggetto) {
				MDOE.Elimina();
			}
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Disattivata)
			super.keyUp(keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {

		if (Disattivata) {

			if (VoglioModificareOggetto) {
				switch (character) {
				// *ABILITA MODIFICA*/
				case 'm': // Abilitiamo La Modifica
					StoSelezionandoConRay = true;
					if (VoglioModificareOggetto) {
						VoglioModificareOggetto = false;
						MX.lblStatoOperazioni
								.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");

					} else {

						VoglioModificareOggetto = true;

						MX.lblStatoOperazioni.setText("Seleziona e modifica Modello");
					}
					break;

				case 'M': // Abilitiamo La Modifica

					if (VoglioModificareOggetto) {
						VoglioModificareOggetto = false;
						MX.lblStatoOperazioni
								.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");

					} else {

						VoglioModificareOggetto = true;

						MX.lblStatoOperazioni.setText("Seleziona e modifica Modello");
					}
					break;
				/* FINE ABILITA MODIFICA */
				case 'w': // ci stiamo muovendo a Avanti

					MDOE.SpostaAvanti();
					break;

				case 'W': // ci stiamo muovendo a Avanti
					MDOE.SpostaAvanti();
					break;

				/****** ROTAZIONI *****/
				case 'q': // ci stiamo muovendo a Avanti
					MDOE.RuotaASinistra();
					break;

				case 'Q': // ci stiamo muovendo a Avanti
					MDOE.RuotaASinistra();

					break;

				case 'e': // ci stiamo muovendo a Avanti
					MDOE.RuotaADestra();
					break;

				case 'E': // ci stiamo muovendo a Avanti
					MDOE.RuotaADestra();
					break;

				/** FINE ROTAZIONI ****/

				case 's': // ci stiamo muovendo a indietro

					MDOE.SpostaIndietro();

					break;

				case 'S': // ci stiamo muovendo a indietro
					MDOE.SpostaIndietro();
					break;

				case 'a':
					MDOE.SpostaASinistra();

					break;

				case 'A':
					MDOE.SpostaASinistra();
					break;

				case 'd':

					MDOE.SpostaADestra();

					break;

				case 'D':

					MDOE.SpostaADestra();
					break;

				default:
					break;

				}
			} else {

				MX.lblStatoOperazioni.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");
				switch (character) {

				case 'm': // Abilitiamo La Modifica

					StoSelezionandoConRay = true;
					if (VoglioModificareOggetto) {
						VoglioModificareOggetto = false;
						MX.lblStatoOperazioni
								.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");

					} else {

						VoglioModificareOggetto = true;

						MX.lblStatoOperazioni.setText("Seleziona e modifica Modello");
					}
					break;

				case 'M': // Abilitiamo La Modifica

					StoSelezionandoConRay = true;
					if (VoglioModificareOggetto) {
						VoglioModificareOggetto = false;
						MX.lblStatoOperazioni
								.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");

					} else {

						VoglioModificareOggetto = true;

						MX.lblStatoOperazioni.setText("Seleziona e modifica Modello");
					}
					break;

				case 'w': // ci stiamo muovendo a Avanti

					pos1.add(rot1);
					// pos1.scl(movSpeed);

					break;

				case 'W': // ci stiamo muovendo a Avanti

					pos1.add(rot1);
					// pos1.z -= movSpeed;
					// pos1.x-=movSpeed;
					break;

				case 's': // ci stiamo muovendo a indietro

					pos1.sub(rot1);
					// pos1.z += movSpeed;
					// pos1.x+=movSpeed;

					break;

				case 'S': // ci stiamo muovendo a indietro

					pos1.sub(rot1);
					// pos1.z += movSpeed;
					// pos1.x+=movSpeed;
					break;

				case 'a': // ci stiamo muovendo a indietro

					break;

				case 'A': // ci stiamo muovendo a indietro

					break;

				case 'd': // ci stiamo muovendo a indietro

					// pos1.z += movSpeed;
					// pos1.x+=movSpeed;

					break;
				case 'D': // ci stiamo muovendo a indietro

					// pos1.z += movSpeed;
					// pos1.x+=movSpeed;
					break;

				default:
					break;

				}

				cam.position.set(pos1);
				
				cam.update();
			}
		} else {
			super.keyTyped(character);
			MX.lblStatoOperazioni.setText("Carica, salva o scegli Modello");

		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		// M.transform.setTranslation(cam.unproject(new
		// Vector3(screenX,screenY,0)));
		if (Disattivata) {

			if (StoSelezionandoConRay) {
				MyRay Ray = new MyRay(cam.position.cpy(), cam.direction.cpy());

				OggettoStatico o = Ray.Fire();
				if (o != null) {
					// devo poter muovere il
					// mio Oggetto

					// System.out.println(o.toString());
					MX.lblTipoDiOggetto.setText(o.toString());
					if (VoglioModificareOggetto) {
						// System.out.println("ad");

						OggettoDaModificare = o;
						MDOE.setOggettoRicevutoDalMyInput(o);

					}

				} else
					MX.lblTipoDiOggetto.setText("Nullo");
					//System.out.println("null"); // cerco di selzionare qualcosa
				// che non c'è

			} else // vuol dire che sto aspettando un input dal menu quiadi la
					// variabile passata dal menù è popolata
			{

				// System.out.println("asd");
				MyRay Ray = new MyRay(cam.position.cpy(), cam.direction.cpy());

				Vector3 Pos = Ray.DovePosizionareOggetto();

				if (Pos != null) {
					MX.lblStatoOperazioni
							.setText("Spostati nel mondo, Inserisci un modello. Controlla quelli esistenti");

					OggettoStatico asd = new OggettoStatico(getTipoDatoI(InteroDelMenuPassatoPerEnum));

					asd.getInstance().transform.setToTranslation(Pos.x, Pos.y + 2f, Pos.z + asd.getBBModel().Prof);

					float xprv = 0, yprv = 0, zprv = 0;
					xprv = Pos.x;
					yprv = Pos.y;
					zprv = Pos.z;

					// -(asd.getBBModel().Prof/2)
					// System.out.println(asd.getBBModel().Base+" — " +
					// asd.getBBModel().Altezza+" —-" +asd.getBBModel().Prof);
					asd.getBBModel().setNewCoord(asd.getBBModel().x + xprv, asd.getBBModel().y + yprv + 2f,
							asd.getBBModel().z + zprv);

					// Centriamo il modello, per dare un effetto grafico
					// migliore
					// alla selezione
					// asd.getInstance().transform.setToTranslation(Pos.x,
					// Pos.y,
					// Pos.z-50f);
					// asd.getBBModel().setNewCoord(asd.getBBModel().x+ xprv,
					// asd.getBBModel().y+yprv, asd.getBBModel().z+zprv - 100f
					// );
					StaticElements.Oggetti.add(asd);

				} else {
					MX.lblStatoOperazioni.setText("Non Puoi inserire dove stai selezionando");

				}

			}

		} else {
			super.touchDown(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);

	}

	@Override
	public boolean scrolled(int amount) {
		return super.scrolled(amount);

	}

	EnumTipoDiOggetto getTipoDatoI(int i) {
		switch (i) {
		case 7:
			return EnumTipoDiOggetto.CarroArmato;
		case 6:
			return EnumTipoDiOggetto.Cespuglio;
		case 5:
			return EnumTipoDiOggetto.Albero;
		case 8:
			return EnumTipoDiOggetto.Casse;
		case 9:
			return EnumTipoDiOggetto.Munizioni;
		case 10:
			return EnumTipoDiOggetto.PrimoSoccorso;
		default:
			return null;

		}

	}

}
