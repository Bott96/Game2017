package inputEditor;


import com.badlogic.gdx.math.Vector3;

import AttacksAndConquers.BoundingBox3D;
import Grafica.OggettoStatico;
import Grafica.StaticElements;

public class MyRay {
	
	BoundingBox3D Ray;
	Vector3 poszioneCamera;
	Vector3 direzioneCamera;
	
	OggettoStatico OggettoCheVoglioPosizionare;
	

	public MyRay(Vector3 posCam, Vector3 dir) {
	
		Ray = new BoundingBox3D(posCam.x,posCam.y,posCam.z,0,0,0);
		
		poszioneCamera= posCam;
		direzioneCamera = dir;
		
	}
	
	
	public OggettoStatico Fire()
	{
		Vector3 prova = new Vector3(0, 0, 0);
	
	
		prova =poszioneCamera.cpy().add(direzioneCamera.cpy());
		
		
		for(int i=0; i<800; i++)
		{
			prova.add(direzioneCamera.cpy().nor());
			Ray.setNewCoord(prova);
			for(OggettoStatico o :StaticElements.Oggetti)
			{
				//System.out.println(o);
				if(Ray.collide(o.getBBModel()))
				{
					//System.out.println("x "+ Ray.x);
					//System.out.println("y "+ Ray.y);
					//System.out.println("z "+ Ray.z);
					return o;
				}
			}
			
		
		}
		return null;
	}
	
	
	

	public Vector3 DovePosizionareOggetto()
	{
		Vector3 prova = new Vector3(0, 0, 0);
	
			
	
		prova =poszioneCamera.cpy().add(direzioneCamera.cpy());
		
		
		for(int i=0; i<800; i++)
		{
			prova.add(direzioneCamera.cpy().nor());
			Ray.setNewCoord(prova);
			
				//System.out.println(o);
				if(Ray.collide(StaticElements.Oggetti.get(0).BBModel))
				{
					//System.out.println("x "+ Ray.x);
					//System.out.println("y "+ Ray.y);
					//System.out.println("z "+ Ray.z);
					Vector3 VecPos = new Vector3();
					VecPos.x=Ray.x;
					VecPos.y=Ray.y;
					VecPos.z=Ray.z;
					return VecPos;
				}
				
			
			
		
		}
		return null;
	}
	
}
