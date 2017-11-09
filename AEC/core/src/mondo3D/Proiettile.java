package mondo3D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import AttacksAndConquers.BoundingBox3D;
import AttacksAndConquers.GameManager;
import Grafica.DynamicElements;

public class Proiettile {

	public Model Modello; // Medello Dell'oggetto Statico
	public BoundingBox3D BBModel; // BoundingBox
	public ModelInstance Instance; // Istanza Del Modello
	ProjectileEquation Eq;

	public Vector3 Posizione;
	boolean Attivo = true;
	Vector3 DirezioneSparo;
	String ChiSono;
	Vector3 LastPosition;

	public Proiettile(Vector3 Pos, Vector3 Dir, String ChiSpara) {

		Eq = new ProjectileEquation(Pos, Dir);
		ChiSono = new String(ChiSpara);
		ModelBuilder modelBuilder = new ModelBuilder();

		DirezioneSparo = Dir;
		Posizione = AggiornaPosizione(Pos);

		Modello = modelBuilder.createSphere(1f, 1f, 1f, 13, 13,
				new Material(ColorAttribute.createDiffuse(Color.YELLOW)), Usage.Position | Usage.Normal);

		Instance = new ModelInstance(Modello);

		BoundingBox b = new BoundingBox();
		Instance.calculateBoundingBox(b);

		Vector3 dim = new Vector3();
		b.getDimensions(dim);

		BBModel = new BoundingBox3D(0.5f, 0.5f, 0.5f, 7f, 7f, 7f);

		// Posizione = new Vector3(AggiornaPosizione(Settings.PositionPlayer));

		// pE = new ProjectileEquation(Posizione);

		LastPosition = new Vector3(Posizione);

		Instance.transform.setToTranslation(Posizione);
		BBModel.setNewCoord(Posizione);
	}

	Vector3 AggiornaPosizione(Vector3 PosizioneAttuale) {
		return new Vector3(PosizioneAttuale.x, 27, PosizioneAttuale.z);
	}

	public void update(float dt) {

		if (ChiSono.equals("PL")) {

			boolean HoColpito = false;
			if (!HoColpito && Posizione.dst(LastPosition) < 100) {

				Instance.transform.setToTranslation(LastPosition.add(DirezioneSparo.cpy().scl(GameManager.GunSpeed * dt)));
				BBModel.setNewCoord(LastPosition);

				for (Enemy en : DynamicElements.Nemici) {

					if (HoColpito == false && this.BBModel.collide(en.BBModel)) {
						System.out.println("Collido Con " + en.getName());

						int sup = en.Vita;
						float x = (sup - GameManager.DannoProiettilePlayer) / 10;
						en.Vita -= GameManager.DannoProiettilePlayer;
						//en.setMb(10);
						HoColpito = true;
						Attivo = false;

					}
				}

			} else
				Attivo = false;

		} else {

			boolean HoColpito = false;
			if (HoColpito == false && Posizione.dst(LastPosition) < 100) {

				Instance.transform.setToTranslation(LastPosition.add(DirezioneSparo.cpy().scl(GameManager.GunSpeed * dt)));
				BBModel.setNewCoord(LastPosition);

				if (HoColpito == false && this.BBModel.collide(DynamicElements.Giocatore.BBModel)) {

					Player.setVita(DynamicElements.Giocatore.getVita() - GameManager.DannoProiettileEnemy);
					DynamicElements.Giocatore.forseSonoMorto();

					System.out.println(DynamicElements.Giocatore.getVita());
					Instance.transform.setToTranslation(new Vector3(900, -50, 900));
					BBModel.setNewCoord(new Vector3(900, -50, 900));
					HoColpito = true;
					Attivo = false;

				}

			} else
				Attivo = false;

		}

		// Instance.transform.setToTranslation(new Vector3(900, -50, 900));
		// BBModel.setNewCoord(new Vector3(900, -50, 900));

	}
}
