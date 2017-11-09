package Grafica;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import MultiPlayer.PosDirIDSparo;
import mondo3D.Compagno;
import mondo3D.Enemy;
import mondo3D.Player;
import mondo3D.Proiettile;

public class DynamicElements {

	public static ArrayList<Enemy> Nemici = new ArrayList<Enemy>(); // modelInstance,
																	// bb3d,modello

	public static ArrayList<Proiettile> GestoreProiettile = new ArrayList<Proiettile>(); // modelInstance,
																							// bb3d,modello
																																		// bb3d,modello
	public static ArrayList<Compagno> Alleati = new ArrayList<Compagno>(); 
	public static ArrayList<PosDirIDSparo> ProiettileDaVisuallizzare = new ArrayList<PosDirIDSparo>();
	
	public static Player Giocatore;
	// 21/70/15 bbm Player

	// public static ArrayList<>

}
