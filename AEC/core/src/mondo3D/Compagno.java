package mondo3D;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import AttacksAndConquers.BoundingBox3D;
import AttacksAndConquers.Risorse;
import AttacksAndConquers.GameManager;
import UserGameInput.StringAnimation;

public class Compagno {

	public Model Modello; // Medello Dell'oggetto Statico
	public BoundingBox3D BBModel; // BoundingBox
	public ModelInstance Instance; // Istanza Del Modello
	public int ID;

	public AnimationController ControlloAnimazione;

	public Compagno(int id) {

		ID = id;

		Instance = new ModelInstance(Risorse.assets.get(Risorse.Player, Model.class));

		Instance.transform.setToTranslation(GameManager.InitialPlayer);

		BoundingBox b = new BoundingBox();
		Instance.calculateBoundingBox(b);

		Vector3 dim = new Vector3();
		b.getDimensions(dim);

		BBModel = new BoundingBox3D(-5f, 0f, 5f, dim.x - 31f, dim.y, dim.z - 10f);

		ControlloAnimazione = new AnimationController(Instance);
		ControlloAnimazione.setAnimation(StringAnimation.AttessaInPiedi, -1);

	}

}
