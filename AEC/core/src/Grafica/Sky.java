package Grafica;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;

import AttacksAndConquers.Risorse;

public class Sky {
	 //static ModelLoader<?> modelLoader;
	private static Model model;
	
	public static ModelInstance modelInstance;
	
	private static boolean enabled;

	
	public static void init () {
		enabled = false;
		
		// Load managed model
		UBJsonReader jsonReader = new UBJsonReader();
		// Create a model loader passing in our json reader
		//G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		
		model = Risorse.assets.get(Risorse.SkyBox);
		
	
	}
	
	
	public static void createSkyBox (Texture xpos, Texture xneg, Texture ypos, Texture yneg, Texture zpos, Texture zneg) {
		modelInstance = new ModelInstance(model, "Skycube");
		
		// Set material textures
		modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(xpos));
		modelInstance.materials.get(1).set(TextureAttribute.createDiffuse(xneg));
		modelInstance.materials.get(2).set(TextureAttribute.createDiffuse(ypos));
		modelInstance.materials.get(3).set(TextureAttribute.createDiffuse(yneg));
		modelInstance.materials.get(5).set(TextureAttribute.createDiffuse(zpos));
		modelInstance.materials.get(4).set(TextureAttribute.createDiffuse(zneg));
		
		//Disable depth test
		modelInstance.materials.get(0).set(new DepthTestAttribute(0));
		modelInstance.materials.get(1).set(new DepthTestAttribute(0));
		modelInstance.materials.get(2).set(new DepthTestAttribute(0));
		modelInstance.materials.get(3).set(new DepthTestAttribute(0));
		modelInstance.materials.get(4).set(new DepthTestAttribute(0));
		modelInstance.materials.get(5).set(new DepthTestAttribute(0));
		
		enabled = true;
	}
	
	
	public static ModelInstance getModelinstance()
	{
		return modelInstance;
	}
	public static void createSkyBox (Texture skybox) {
		modelInstance = new ModelInstance(model, "Skybox");
		
		// Set material texutres and Disable depth test
		modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(skybox));
		modelInstance.materials.get(0).set(new DepthTestAttribute(0));
		
		enabled = true;
	}
	
	
	
	
	/*public static void createSkySphere () {
		throw new NotImplementedException();
	}
	*/
	
	private static final Vector3 tmp = new Vector3();
	private static final Quaternion q = new Quaternion();
	public static float yawRotation = 0f;
	public static float yawRotateSpeed = 0.01f;

	public static void update (Vector3 position) {
		tmp.set(position.x, position.y, position.z);
		modelInstance.transform.getRotation(q);
		yawRotation += yawRotateSpeed;
		q.setFromAxis(Vector3.Y, yawRotation);
		modelInstance.transform.set(q);
		modelInstance.transform.setTranslation(tmp);
	}

	public static void disable () {
		//TODO Make this a little bit nicer?
		modelInstance = null;
		enabled = false;
	}
	
	public static boolean isEnabled () {
		return enabled;
	}

}