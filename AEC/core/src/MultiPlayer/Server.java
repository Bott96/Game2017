package MultiPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {

	ConcurrentLinkedQueue<Gestore> ListaGiocatori = new ConcurrentLinkedQueue<Gestore>();

	ServerSocket MioServer;
	int Porta;
	static boolean Inizializzazione = true;
	int NumeriGiocatori = 1;
	

	public Server(int Porta) {
		ListaGiocatori.clear();
		this.Porta = Porta;

	}

	@Override
	public void run() {
		super.run();

		try {
			MioServer = new ServerSocket(Porta);

		} catch (IOException e) {
			e.printStackTrace();
		}

		while (Inizializzazione && ListaGiocatori.size() != MultiPlayerSettings.NumPlayer) {

			System.out.println("AspettoGiocatori");
			Socket nuovoGiocatore;
			try {
				nuovoGiocatore = MioServer.accept();

				System.out.println("Giocatore Accettato");
				Gestore c = new Gestore(nuovoGiocatore, this);
				ListaGiocatori.add(c);
				c.start();

				// System.out.println("ConnessioneAvvenuta con n client " +
				// ListaGiocatori.size());

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		System.out.println("Inizializzazione Finita");

		sendId(); // Manda id a tutti i client
		System.out.println("Mando");

		ComuniaCreazionePlayer();

		boolean PossoCominciare = false;
		boolean TuttiTrue = true;
		while (!PossoCominciare) {
			TuttiTrue = true;

			for (Gestore gestore : ListaGiocatori) {
				if (!gestore.SonoPronto)
					TuttiTrue = false;

			}

			if (TuttiTrue)
				PossoCominciare = true;
		}

		OrdinaAvvioGioco();

	}

	public void eliminaClientDalSerever(Gestore g) {
		Iterator<Gestore> itg = ListaGiocatori.iterator();

		while (itg.hasNext()) {
			Gestore gestoredaiteratore = itg.next();

			if (gestoredaiteratore.equals(g)) {
				itg.remove();
				System.out.println("RimuovoDallaListaDiGiocatori");
			}
		}

		if (ListaGiocatori.size() == 0) {
			try {
				MioServer.close();
				System.out.println("Chiusura Automatica del server");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MioServer = null;
		}

	}

	public void BroadcastInformation(String s) {
		for (Gestore gestore : ListaGiocatori) {
			if (gestore.client != null && gestore.client.isConnected() ) {
				gestore.output.println(s);
				gestore.output.flush();
			}
		}

	}

	public void ChiudiServer() {

		try {
			if ( MioServer != null && !MioServer.isClosed() ){
				MioServer.close();
				System.out.println("Stato del mio server" + MioServer.isClosed());
			}else
			System.out.println("Server gia Ciuso");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendId() {
		System.out.println("Ci arrivo");

		for (Gestore g : ListaGiocatori) {

			g.output.println(NumeriGiocatori);
			g.output.flush();
			NumeriGiocatori++;

		}

	}

	public boolean TuttiHannoCaricatoLaMappa() {
		int c = 0;

		for (Gestore gestore : ListaGiocatori) {
			try {
				if (gestore.input.readLine() == ProtocolString.MappaCaricata) {
					c++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return c == ListaGiocatori.size() - 1;

	}

	public void OrdinaAvvioGioco() {

		for (Gestore g : ListaGiocatori) {

			g.output.println(ProtocolString.AvviaGioco);
			g.output.flush();

		}

	}

	public void ComuniaCreazionePlayer() {

		for (Gestore gestore : ListaGiocatori) {
			gestore.output.println(ListaGiocatori.size());
			gestore.output.flush();
		}

	}

}
