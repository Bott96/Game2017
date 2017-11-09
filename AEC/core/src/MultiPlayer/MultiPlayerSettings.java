package MultiPlayer;

public class MultiPlayerSettings {

	public static int NumPlayer = 2;

	public static int NumNem = 2;

	public static boolean MultiPlayer = false;

	public static boolean DevoCaricareLaMappa = false;

	public static boolean Renderizza = false;

	public static boolean PuoiInviare = false;

	public static String LastAnimation = new String("");

	public static boolean Termina = false;
	
	public static boolean IniziaControlli = false;
	public static boolean NemiciCaricati = false;
	
	
	public static void Resetta()
	{
		
		
		 DevoCaricareLaMappa = false;
		 Renderizza = false;
		 PuoiInviare = false;
		 LastAnimation = new String("");
		 Termina = false;
		 NemiciCaricati = false;
		 IniziaControlli = false;
	}

}
