package UserGameInput;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import Grafica.DynamicElements;
import MultiPlayer.MultiPlayerSettings;
import MultiPlayer.ProtocolString;
import mondo3D.Player;

public class CadutaLibera extends Thread {

	Vector3 Posizione;
	Vector3 Dir;
	float Somma;

	public CadutaLibera(Vector3 Pos, Vector3 Dr) {
		Somma =0f;
		Posizione = new Vector3(Pos);
		Dir = new Vector3(Dr);

	}

	@Override
	public void run() {
		super.run();
		
	try {
		
		
		while(Somma < 5) {
			DynamicElements.Giocatore.Instance.transform.setToTranslation(
					Posizione.add(new Vector3(0, Dir.y, 0).scl(9.81f * Gdx.graphics.getDeltaTime())));
			Somma += Gdx.graphics.getDeltaTime();
			sleep(10);
		}
		
		if(MultiPlayerSettings.MultiPlayer && MultiPlayerSettings.PuoiInviare)
		{
			//ComunicaUscitaPlayer
			//MultiPlayerSettings.Resetta();
			Player.c.output.println(Player.c.ID+"#"+ProtocolString.HoSmessoDiGiocare+"#");
			Player.c.output.flush();
			/*Player.c.output.close();
			try {
				Player.c.input.close();
				Player.c.MyClient.close();
				Player.c.MyClient = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		}
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

}
