package inputEditor;

import com.badlogic.gdx.math.Vector3;


import Grafica.OggettoStatico;
import Grafica.StaticElements;

public class ModificatoreDiOggettiEditor {

	OggettoStatico OggettoRicevutoDalMyInput;
	float ValoreDiSpostamento =2f;
	

	public ModificatoreDiOggettiEditor() {

		OggettoRicevutoDalMyInput = null;
	}

	public void setOggettoRicevutoDalMyInput(OggettoStatico o) {

		OggettoRicevutoDalMyInput = o;
	}

	// Rotazioni
	public void RuotaADestra() {

		OggettoRicevutoDalMyInput.getInstance().transform.rotate(new Vector3(0, 1, 0), 1.1f);
		System.out.println(OggettoRicevutoDalMyInput.oggetto);

	}

	public void RuotaASinistra() {

		OggettoRicevutoDalMyInput.getInstance().transform.rotate(new Vector3(0, -1, 0), 1.1f);
		
		OggettoRicevutoDalMyInput.getInstance().transform.scale(0.5f, 0.5f, 0.5f);

	}

	// Spostamento

	public void SpostaADestra() {

		Vector3 NuovaPM = new Vector3();
		Vector3 NuovaPBB = new Vector3(OggettoRicevutoDalMyInput.getBBModel().x ,OggettoRicevutoDalMyInput.getBBModel().y ,OggettoRicevutoDalMyInput.getBBModel().z );
		OggettoRicevutoDalMyInput.getInstance().transform.getTranslation(NuovaPM);
		
		NuovaPM.x-=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getInstance().transform.setToTranslation(NuovaPM);
		
		NuovaPBB.x-=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getBBModel().setNewCoord(NuovaPBB);
		
		
	}

	public void SpostaASinistra() {
		
		Vector3 NuovaPM = new Vector3();
		Vector3 NuovaPBB = new Vector3(OggettoRicevutoDalMyInput.getBBModel().x ,OggettoRicevutoDalMyInput.getBBModel().y ,OggettoRicevutoDalMyInput.getBBModel().z );
		OggettoRicevutoDalMyInput.getInstance().transform.getTranslation(NuovaPM);
		
		NuovaPM.x+=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getInstance().transform.setToTranslation(NuovaPM);
		
		NuovaPBB.x+=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getBBModel().setNewCoord(NuovaPBB);
		
		
	}

	public void SpostaAvanti() {
		
		Vector3 NuovaPM = new Vector3();
		Vector3 NuovaPBB = new Vector3(OggettoRicevutoDalMyInput.getBBModel().x ,OggettoRicevutoDalMyInput.getBBModel().y ,OggettoRicevutoDalMyInput.getBBModel().z );
		OggettoRicevutoDalMyInput.getInstance().transform.getTranslation(NuovaPM);
		
		NuovaPM.z+=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getInstance().transform.setToTranslation(NuovaPM);
		
		NuovaPBB.z+=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getBBModel().setNewCoord(NuovaPBB);
		
	}

	public void SpostaIndietro() {
		
		Vector3 NuovaPM = new Vector3();
		Vector3 NuovaPBB = new Vector3(OggettoRicevutoDalMyInput.getBBModel().x ,OggettoRicevutoDalMyInput.getBBModel().y ,OggettoRicevutoDalMyInput.getBBModel().z );
		OggettoRicevutoDalMyInput.getInstance().transform.getTranslation(NuovaPM);
		
		NuovaPM.z-=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getInstance().transform.setToTranslation(NuovaPM);
		
		NuovaPBB.z-=ValoreDiSpostamento;
		OggettoRicevutoDalMyInput.getBBModel().setNewCoord(NuovaPBB);
		
	}

	public void Elimina() {
		StaticElements.Oggetti.remove(OggettoRicevutoDalMyInput);
	}

	OggettoStatico getOggettoModificato() {
		return OggettoRicevutoDalMyInput;
	}

	public static Vector3 rotateVectorCC(Vector3 vec, Vector3 axis, double theta){
	    
		
		/*
		 * 
		 * z' = z*cos q - x*sin q
		   x' = z*sin q + x*cos q
		   y' = y
		 * 
		 * **/
		Vector3 Vrot= new Vector3();
		double x, y, z;
	    double u, v, w;
	    x=vec.x;y=vec.y;z=vec.z;
	    u=axis.x;v=axis.y;w=axis.z;
	    double xPrime =  z * Math.sin(theta) - x *Math.cos(theta);
	    double yPrime =y;
	    double zPrime = z * Math.cos(theta) - x *Math.sin(theta);
	    Vrot.x=(float) xPrime;
	    Vrot.y=(float) yPrime;
	    Vrot.z=(float) zPrime;
	    
	    
	    return Vrot;
	}

}

