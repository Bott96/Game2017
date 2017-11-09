package MultiPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector3;

import Grafica.DynamicElements;
import mondo3D.Enemy;

public class Client extends Thread {

	public Socket MyClient;

	public PrintWriter output;
	public BufferedReader input;
	public Decodificatore d;
	public boolean SonoConnesso = true;

	public int ID = 0;

	public Client(String IpAddres, int port) {

		d = new Decodificatore();

		MyClient = new Socket();
		InetSocketAddress inet = new InetSocketAddress(IpAddres, port);
		try {
			MyClient.connect(inet, 500);
			if (MyClient.isConnected()) {
				output = new PrintWriter(this.MyClient.getOutputStream());
				input = new BufferedReader(new InputStreamReader(this.MyClient.getInputStream()));
			} else {
				return;
			}
		} catch (UnknownHostException e) {
		} catch (IOException e) {

		}

	}

	@Override
	public void run() {
		super.run();
		boolean IdRicevuto = false;
		if (MyClient == null) {
			SonoConnesso = false;
			return;
		}
		while (!IdRicevuto) {

			try {
				// ascolto
				String dalServer = input.readLine();// accumula fin quando
													// arriva
													// ad avere una riga comleta

				System.out.println(dalServer);

				ID = Integer.valueOf(dalServer);

				IdRicevuto = true;

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		try {
			String NumeroGiocatorieEComando = input.readLine();

			int NumGiocatori = Integer.valueOf(NumeroGiocatorieEComando.substring(0));

			for (int i = 1; i <= NumGiocatori; i++) {
				if (i != ID) {
					d.creaAlleato(i);
				}
			}
			/* Prova Per i Nemici */

			if (ID == 1) {
				d.creaNemici();

				String x = new String();
				x += ID + "#" + DynamicElements.Nemici.size();

				output.println(x);
				output.flush();

				for (Enemy i : DynamicElements.Nemici) {

					String CodNemico = new String();
					Vector3 Po = new Vector3();
					i.Instance.transform.getTranslation(Po);
					i.BBModel.setNewCoord(Po);
					CodNemico += ID + "#" + Po;

					output.println(CodNemico);
					output.flush();

				}
			} else {

				// StiamoInAttesaDiQuantiNemiciDevoRicevere
				String QuantiNemici = new String();

				System.out.println("Sono in attesa??");
				QuantiNemici = input.readLine();

				System.out.println("Abbiamo Nemici =  a  " + QuantiNemici.indexOf("#") + 1);
				int numero = Integer.valueOf(QuantiNemici.substring(QuantiNemici.indexOf("#") + 1));

				for (int i = 0; i < numero; i++) {

					String CodificaNemico = new String();

					CodificaNemico = input.readLine();

					d.DecodicaNemico(CodificaNemico);

				}
			}

			System.out.println("HO FINITO DI CARICARE I NEMICI");

			MultiPlayerSettings.NemiciCaricati= true;
			output.println(ProtocolString.FineCondivisioneNemici);
			output.flush();
			String PossoAvviare;
			if (ID == 1) {
				System.out.println("AspettoChepossoAvviare");
				PossoAvviare = input.readLine();
				/* ScartiLeStringheChe Hai Mandato */
				int numero = Integer.valueOf(PossoAvviare.substring(PossoAvviare.indexOf("#") + 1));
				for (int i = 0; i < numero; i++)
					PossoAvviare = input.readLine();
			}

			PossoAvviare = input.readLine();

			System.out.println("__" + PossoAvviare);

			if (PossoAvviare.equals(ProtocolString.AvviaGioco)) {

				MultiPlayerSettings.PuoiInviare = true;
				for (Enemy e : DynamicElements.Nemici) {
					e.start();
					System.out.println("NemiciAvviati");
				}

				MultiPlayerSettings.IniziaControlli = true;
			}

			while (!MultiPlayerSettings.Termina) {

				String s = input.readLine();

				if (s.charAt(0) != ID) { // Se è un evento che NON hai creato tu

					String Con = new String(s.substring(s.indexOf("#") + 1, s.lastIndexOf("#")));

					if (Con.equals(ProtocolString.Proiettile)) {
						d.DecodificaProiettile(s);
					} else if (Con.equals(ProtocolString.RaccogliOggetto)) {
						int pos = Integer.valueOf(s.substring(s.lastIndexOf("#") + 1));
						// System.out.println("Posizione numero da rimuovere " +
						// pos);
						d.DecodificaRimozioneOggetto(pos);
					} else if (Con.equals(ProtocolString.HoSmessoDiGiocare)) {
						MultiPlayerSettings.Termina = true;

						System.out.println("metto Termina a ture");

						d.eliminaGiocatoreCheHaSmessoDiGiocare(s);
						break;
					} else if (Con.equals(ProtocolString.SonoMorto)) {
						d.eliminaGiocatoreCheHaSmessoDiGiocare(s);

					} else {
						d.DecodificaInformazioniRicevute(s);
					}

				}

			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

}
