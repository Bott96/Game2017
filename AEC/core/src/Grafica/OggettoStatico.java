package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import AttacksAndConquers.BoundingBox3D;
import AttacksAndConquers.EnumTipoDiOggetto;
import AttacksAndConquers.Risorse;

public class OggettoStatico {

	public Model Modello; // Medello Dell'oggetto Statico
	public BoundingBox3D BBModel; // BoundingBox
	public ModelInstance Instance; // Istanza Del Modello

	// Position
	float x = 0;
	float y = 0;
	float z = 0;
	public EnumTipoDiOggetto oggetto;

	public OggettoStatico(EnumTipoDiOggetto tipoDiOggetto) {

		ModelBuilder modelBuilder = new ModelBuilder();

		switch (tipoDiOggetto) {

		case Cespuglio:

			this.oggetto = EnumTipoDiOggetto.Cespuglio;

			Instance = new ModelInstance(Risorse.assets.get(Risorse.Cespuglio, Model.class));

			BoundingBox b7 = new BoundingBox();
			Instance.calculateBoundingBox(b7);

			Vector3 dim7 = new Vector3();
			b7.getDimensions(dim7);

			//System.out.println(dim7);
			BBModel = new BoundingBox3D(0f, 0f, 0f, dim7.x, dim7.y, dim7.z-18f);

			//BBModel = new BoundingBox3D(-5f, 0f, 5f, dim7.x-26f, dim7.y, dim7.z);

			
			break;

		case Albero:
			this.oggetto = EnumTipoDiOggetto.Albero;

			Instance = new ModelInstance(Risorse.assets.get(Risorse.Tree, Model.class));

			BoundingBox b8 = new BoundingBox();
			Instance.calculateBoundingBox(b8);

			Vector3 dim8 = new Vector3();
			b8.getDimensions(dim8);

		
			BBModel = new BoundingBox3D(-20f, 0, 35f, dim8.x - 120f, dim8.y - 90f, dim8.z - 160f);
			break;

		case CarroArmato:

			this.oggetto = EnumTipoDiOggetto.CarroArmato;

			Instance = new ModelInstance(Risorse.assets.get(Risorse.Tank, Model.class));

			BoundingBox b = new BoundingBox();
			Instance.calculateBoundingBox(b);

			Vector3 dim = new Vector3();
			b.getDimensions(dim);

			//System.out.println(dim);
			BBModel = new BoundingBox3D(-26f, 0, 50f, dim.x, dim.y - 30, dim.z - 90);

			break;
		case Casse:
			this.oggetto = EnumTipoDiOggetto.Casse;
			Instance = new ModelInstance(Risorse.assets.get(Risorse.Casse, Model.class));

			BoundingBox b4 = new BoundingBox();
			Instance.calculateBoundingBox(b4);
			Vector3 dim4 = new Vector3();
			b4.getDimensions(dim4);

			BBModel = new BoundingBox3D(-9.5f, 0, 6.5f, dim4.x, dim4.y, dim4.z);
			Instance.transform.setToTranslation(new Vector3(0,0,0));
			break;
		case Munizioni:

			this.oggetto = EnumTipoDiOggetto.Munizioni;
			Instance = new ModelInstance(Risorse.assets.get(Risorse.Munizioni, Model.class));

			BoundingBox b1 = new BoundingBox();
			Instance.calculateBoundingBox(b1);

			Vector3 dim1 = new Vector3();
			b1.getDimensions(dim1);

			//System.out.println(dim1);

			BBModel = new BoundingBox3D(-5.5f, 0, 3f, dim1.x, dim1.y, dim1.z);

			break;
		case PrimoSoccorso:
			this.oggetto = EnumTipoDiOggetto.PrimoSoccorso;

			Instance = new ModelInstance(Risorse.assets.get(Risorse.PrimoSoccorso, Model.class));

			BoundingBox b5 = new BoundingBox();
			Instance.calculateBoundingBox(b5);

			Vector3 dim5 = new Vector3();
			b5.getDimensions(dim5);

			BBModel = new BoundingBox3D(0, 2, 0, dim5.x, dim5.y, dim5.z);
			break;
		case Terreno:

			Vector3 Posizione = new Vector3(0, 0, 0);

			Modello = modelBuilder.createBox(1500f, 2f, 1500f, new Material(),
					Usage.Position | Usage.Normal | Usage.TextureCoordinates );

			this.oggetto = EnumTipoDiOggetto.Terreno;
			// System.out.println("OGGETTO: "+tipoDiOggetto);

			Instance = new ModelInstance(Modello);

			TextureAttribute t;
			
			t = TextureAttribute.createDiffuse(Risorse.assets.get(Risorse.TextureTerreno,Texture.class));

			Instance.materials.get(0).set(t);
			
			
			// Instance.transform.setToTranslation(Posizione);

			BBModel = new BoundingBox3D(-750f, -1f, -750f, 1500f, 2f, 1500f);

			// System.out.println(BBModel.toString());
			// BBModel.setNewCoord(-15f,-15f,-15f);
			// BBModel.SetDim(30f, 0, 30f);

			break;

		default:
			break;
		}

	}

	/* SET E GET */

	public Model getModello() {
		return Modello;
	}

	public void setModello(Model modello) {
		Modello = modello;
	}

	public BoundingBox3D getBBModel() {
		return BBModel;
	}

	public void setBBModel(BoundingBox3D bBModel) {
		BBModel = bBModel;
	}

	public ModelInstance getInstance() {
		return Instance;
	}

	public void setInstance(ModelInstance instance) {
		Instance = instance;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		String s = "" + this.oggetto;
		return s;
	}

}
