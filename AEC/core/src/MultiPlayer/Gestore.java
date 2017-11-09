package MultiPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Gestore extends Thread {

	public Socket client = null;

	public boolean SonoPronto = false;

	PrintWriter output;
	BufferedReader input;
	Server s;

	public Gestore(Socket client, Server s) {

		this.client = client;
		this.s = s;

		try {
			output = new PrintWriter(this.client.getOutputStream());
			input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		boolean Reset= false;
		while (true && !Reset) {

			try {
				String DaClient = input.readLine();
				
				if (!SonoPronto && DaClient.equals(ProtocolString.FineCondivisioneNemici)) {
					SonoPronto = true;

				} else
				{
					if(DaClient == null)
					{
						break;
					}
					s.BroadcastInformation(DaClient);
				}
			} catch (IOException e) {
				System.out.println("Sono in questa Eccezione");
				client = null;
				Reset= true;
				
				//e.printStackTrace();
			}

		}
		System.out.println("La esegui duoi l'eccezine");
		s.eliminaClientDalSerever(this);
	
		try {
			
			input.close();
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
