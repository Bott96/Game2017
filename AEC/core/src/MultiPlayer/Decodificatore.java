package MultiPlayer;

import java.util.Iterator;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import Grafica.DynamicElements;
import Grafica.StaticElements;
import mondo3D.Compagno;
import mondo3D.Enemy;
import mondo3D.Proiettile;

public class Decodificatore {

	public Decodificatore() {

	}

	public void creaNemici()
	{
		
		for(int i =0; i<MultiPlayerSettings.NumNem*MultiPlayerSettings.NumPlayer; i++)
		{
			DynamicElements.Nemici.add(new Enemy());
		}
	}

	
	public void DecodicaNemico(String s){
		
		Vector3 Pos = new Vector3();
		
		s = s.substring(s.indexOf("#")+1);
		
		
		Pos.x = Float.valueOf(s.substring(1, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 1);
		Pos.y = Float.valueOf(s.substring(0, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 1);
		Pos.z = Float.valueOf(s.substring(0, s.indexOf(")")));
		
		 Enemy e = new Enemy();
		 e.Instance.transform.setToTranslation(Pos);
		 e.BBModel.setNewCoord(Pos);
		 e.MiaPos = Pos;
		DynamicElements.Nemici.add(e);
		
		
	}
	
	
	public void creaAlleato(int id) {
		DynamicElements.Alleati.add(new Compagno(id));}

	public String CodificaInformazioneProiettile(int MioId, Vector3 Dir) {
		String s = new String();

		s += MioId + "#" + ProtocolString.Proiettile + "#" + Dir;

		return s;
	}

	
	public void DecodificaRimozioneOggetto(int pos)
	{
		
		StaticElements.Oggetti.remove(pos);
		
	}
	
	public void DecodificaProiettile(String S) {
		for (Compagno C : DynamicElements.Alleati) {

			if (C.ID == Integer.valueOf(S.substring(0, 1))) {

				S = S.substring(2);
				Vector3 Pos = new Vector3();
				C.Instance.transform.getTranslation(Pos);

				Vector3 Dir = new Vector3();

				S = S.substring(S.indexOf("#") + 1);
				Dir.x = Float.valueOf(S.substring(1, S.indexOf(",")));
				S = S.substring(S.indexOf(",") + 1);
				Dir.y = Float.valueOf(S.substring(0, S.indexOf(",")));
				S = S.substring(S.indexOf(",") + 1);
				Dir.z = Float.valueOf(S.substring(0, S.indexOf(")")));

				DynamicElements.ProiettileDaVisuallizzare.add(new PosDirIDSparo(Pos, Dir, "PL"));
				// System.out.println("Ho SparatoIlProiettile DimLista = " +
				// DynamicElements.GestoreProiettile.size());

			}

		}
	}

	public String CodificaInformazioneDaMandare(int MioId, String Mov) {
		String s = null;

		s = new String();

		Vector3 PosMod = new Vector3();
		DynamicElements.Giocatore.Instance.transform.getTranslation(PosMod);

		Quaternion q = new Quaternion();
		DynamicElements.Giocatore.Instance.transform.getRotation(q);

		s += String.valueOf(MioId) + "#" + PosMod + "#" + q + "#" + Mov;
		// System.out.println(s);

		return new String(s);

	}

	public void DecodificaInformazioniRicevute(String s) {
		int id = Integer.valueOf(s.substring(0, 1));
		//System.out.println(id);

		Vector3 Pos = new Vector3();

		Pos.x = Float.valueOf(s.substring(3, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 1);
		Pos.y = Float.valueOf(s.substring(0, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 1);
		Pos.z = Float.valueOf(s.substring(0, s.indexOf(")")));
		s = s.substring(s.indexOf("[") + 1);
		// Posok
		Quaternion q = new Quaternion();
		q.x = Float.valueOf(s.substring(0, s.indexOf("|")));
		s = s.substring(s.indexOf("|") + 1);
		q.y = Float.valueOf(s.substring(0, s.indexOf("|")));
		s = s.substring(s.indexOf("|") + 1);
		q.z = Float.valueOf(s.substring(0, s.indexOf("|")));
		s = s.substring(s.indexOf("|") + 1);
		q.w = Float.valueOf(s.substring(0, s.indexOf("]")));
		String Anim = s.substring(s.indexOf("#") + 1);
		//System.out.println(Anim);

		for (Compagno c : DynamicElements.Alleati) {

			if (c.ID == id) {

				if (Anim.equals("null")) {
					//System.out.println("AnimazioneNulla");
					c.Instance.transform.set(Pos, q);

				} else {
					if (MultiPlayerSettings.LastAnimation.equals("")) {
						c.ControlloAnimazione.setAnimation(Anim, -1);
						MultiPlayerSettings.LastAnimation = Anim;
					} else if (MultiPlayerSettings.LastAnimation.equals(Anim))
						c.Instance.transform.set(Pos, q);
					else {
						c.ControlloAnimazione.setAnimation(Anim, -1);
						MultiPlayerSettings.LastAnimation = Anim;
						c.BBModel.setNewCoord(Pos);
						c.Instance.transform.set(Pos, q);

					}
				}
			}
		}

		// System.out.println(q);
	}

	public void eliminaGiocatoreCheHaSmessoDiGiocare(String s)
	{
		int Id = Integer.valueOf(s.substring(0,1));
		
		Iterator<Compagno> i = DynamicElements.Alleati.iterator();
		
		while(i.hasNext())
		{
			Compagno c = i.next();
			
			if(c.ID == Id)
				i.remove();
			
		}
		
	}
	
}
