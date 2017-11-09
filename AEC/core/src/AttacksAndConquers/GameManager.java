package AttacksAndConquers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

public class GameManager {
	public static boolean FullScreen = true;
	public static float DegreesRotation = 2.5f;
	public static float SpeedWalk = 150f;
	public static float SpeedRun = 300f;
	public static float GunSpeed =450f;
	public static float TimeToFire =1500f;
	
	public static int MaxVitaPlayer = 100;
	public static int MaxMunizioniPlayer = 360;

	public static int AggiungoVita = 10;
	public static int AggiungoMunizioni = 50;

	public static int MaxVitaEnemy = 100;

	public static int DannoProiettileEnemy = 9;
	public static int DannoProiettilePlayer = 11;

	public static boolean DifficoltaFacile = false;
	public static boolean DifficoltaMedio = false;
	public static boolean DifficoltaDifficile = false;

	public static boolean PrimaPersona = false;

	public static int NumeroNemiciDifficoltaFacile = 4;
	public static int NumeroNemiciDifficoltaMedio = 9;
	public static int NumeroNemiciDifficoltaDifficile = 12;
	

	public static boolean GameOver = false;
	public static boolean Win = false;
	
	public static String StringGameOver = new String();

	public static final Vector3 InitialCamera = new Vector3(0, 50, -45);
	public static final Vector3 InitialRCamera = new Vector3(0, -6, 12);
	public static final Vector3 InitialPlayer = new Vector3(0, 0, 0);

	public static Vector3 AttualCameraPosition = new Vector3();

	public static Vector3 AttualCameraPositionNoFirstPerson = new Vector3();
	public static Vector3 DirectionCamera = new Vector3();

	public static Vector3 AttualDirectionPlayer = new Vector3();
	public static Vector3 PositionPlayer = new Vector3();

	public static Vector3 DirectionForFire = new Vector3();

	public static Music mp3Music;
	public static Music mp3Run;
	
	public static Music mp3Walk;
	public static Sound mp3Shot;
	public static Sound mp3NoGuns;
	public static boolean MusicActive = true;
	public static boolean EffectActive = true;

}
