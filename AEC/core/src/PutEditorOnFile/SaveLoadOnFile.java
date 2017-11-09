package PutEditorOnFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import AttacksAndConquers.EnumTipoDiOggetto;
import Grafica.OggettoStatico;
import Grafica.StaticElements;

public class SaveLoadOnFile {

	String SingoloOggetto;
	PrintWriter writer;
	EnumTipoDiOggetto enu;
	boolean EsisteFile;

	public SaveLoadOnFile() {
		SingoloOggetto = new String();

	}

	public void SalvaSulFile(String NomeDelFile) {

		try {
			File logFile = new File(Gdx.files.getLocalStoragePath() + "/Livelli/" + NomeDelFile + ".txt");
			writer = new PrintWriter(new FileWriter(logFile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (OggettoStatico o : StaticElements.Oggetti) {
			SingoloOggetto = CodificaOggetto(o);

			try {
				// System.out.println(logFile.getCanonicalPath());

				writer.println(CodificaOggetto(o));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			// Close the writer regardless of what
			// happens...
			writer.close();
		} catch (Exception e) {
		}

	}

	public void CaricaLivelloDiDefault() {
		File file = new File(Gdx.files.getLocalStoragePath() + "/Livelli/Default.txt");
		try {

			StaticElements.Oggetti.clear();
			Scanner s = new Scanner(file);
			while (s.hasNextLine()) {
				String qwe = new String(s.nextLine());
				DecodificaFile(qwe);
				// System.out.println(qwe);

			}

			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CaricaDaFile() {

		JFileChooser jf = new JFileChooser(Gdx.files.getLocalStoragePath() + "/Livelli");
		// jf.setLocation(Gdx.input.getX(),Gdx.input.getY());

		jf.setFileSelectionMode(JFileChooser.FILES_ONLY); // set the
															// selection
															// (files/directorys)
		jf.setFileFilter(new FileNameExtensionFilter("*.txt;", "txt"));// set
																		// the
																		// file
																		// extensions
																		// (jpg,gif)
		// jf.showSaveDialog(null);
		int response = jf.showOpenDialog(jf);// shows the open-dialog
												// and get the response

		if (response == JFileChooser.APPROVE_OPTION) { // only when the
														// user click on
														// open, do
														// this:
			File file = jf.getSelectedFile(); // get the choosen file
			if (file.exists())
				EsisteFile = true;
			else
				EsisteFile = false;

			try {

				StaticElements.Oggetti.clear();
				Scanner s = new Scanner(file);
				while (s.hasNextLine()) {
					String qwe = new String(s.nextLine());
					DecodificaFile(qwe);
					// System.out.println(qwe);

				}

				s.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public boolean getEsisteFile() {
		return EsisteFile;
	}

	public File getFileDaCondividere() {
		File file = null;

		JFileChooser jf = new JFileChooser(Gdx.files.getLocalStoragePath() + "/Livelli");
		// jf.setLocation(Gdx.input.getX(),Gdx.input.getY());
		System.out.println("Qui ci arrivo");
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY); // set the
															// selection
															// (files/directorys)
		jf.setFileFilter(new FileNameExtensionFilter("*.txt;", "txt"));
		// jf.showSaveDialog(null);
		int response = jf.showOpenDialog(jf);// shows the open-dialog
												// and get the response

		if (response == JFileChooser.APPROVE_OPTION) { // only when the
														// user click on
														// open, do
														// this:
			file = jf.getSelectedFile(); // get the choosen file
			if (file.exists())
				EsisteFile = true;
			else
				EsisteFile = false;

		}

		return file;

	}

	private String CodificaOggetto(OggettoStatico o1) {

		String s = new String();
		Vector3 NP = new Vector3();
		o1.Instance.transform.getTranslation(NP);
		// System.out.println(getIDaEnum(o1.oggetto));

		s += String.valueOf(getIDaEnum(o1.oggetto));
		s += '#';
		s += o1.BBModel.x;
		s += '/';
		s += o1.BBModel.y;
		s += '/';
		s += o1.BBModel.z;
		s += '/';
		s += o1.BBModel.Base;
		s += '/';
		s += o1.BBModel.Altezza;
		s += '/';
		s += o1.BBModel.Prof;
		s += '#';
		s += NP;
		s += '#';

		return s;
	}

	int getIDaEnum(EnumTipoDiOggetto i) {
		switch (i) {
		case CarroArmato:
			return 7;
		case Cespuglio:
			return 6;
		case Albero:
			return 5;
		case Casse:
			return 8;
		case Munizioni:
			return 9;
		case PrimoSoccorso:
			return 10;
		case Terreno:
			return 4;
		default:
			return (Integer) null;

		}

	}

	EnumTipoDiOggetto getTipoDatoI(int i) {
		switch (i) {
		case 7:
			return EnumTipoDiOggetto.CarroArmato;
		case 4:
			return EnumTipoDiOggetto.Terreno;
		case 6:
			return EnumTipoDiOggetto.Cespuglio;
		case 5:
			return EnumTipoDiOggetto.Albero;
		case 8:
			return EnumTipoDiOggetto.Casse;
		case 9:
			return EnumTipoDiOggetto.Munizioni;
		case 10:
			return EnumTipoDiOggetto.PrimoSoccorso;
		default:
			return null;

		}
	}

	private void DecodificaFile(String s) {
		/* Estrai Tipo Di Oggetto */

		int x = Integer.valueOf(s.substring(0, s.indexOf('#')));

		OggettoStatico ox = new OggettoStatico(getTipoDatoI(x));

		s = s.substring(s.indexOf('#') + 1);
		// System.out.println(Float.valueOf(s.substring(0, s.indexOf('/') -
		// 1)));
		ox.BBModel.x = Float.valueOf(s.substring(0, s.indexOf('/') - 1));
		s = s.substring(s.indexOf('/') + 1);
		// System.out.println(s);
		ox.BBModel.y = Float.valueOf(s.substring(0, s.indexOf('/') - 1));
		s = s.substring(s.indexOf('/') + 1);
		ox.BBModel.z = Float.valueOf(s.substring(0, s.indexOf('/') - 1));
		s = s.substring(s.indexOf('/') + 1);
		ox.BBModel.Base = Float.valueOf(s.substring(0, s.indexOf('/') - 1));
		s = s.substring(s.indexOf('/') + 1);
		ox.BBModel.Altezza = Float.valueOf(s.substring(0, s.indexOf('/') - 1));
		s = s.substring(s.indexOf('/') + 1);
		ox.BBModel.Prof = Float.valueOf(s.substring(0, s.indexOf('#') - 1));
		s = s.substring(s.indexOf('#'));

		float px = Float.valueOf(s.substring(2, s.indexOf(',') - 1));
		s = s.substring(s.indexOf(',') + 1);
		float py = Float.valueOf(s.substring(0, s.indexOf(',') - 1));
		s = s.substring(s.indexOf(',') + 1);
		float pz = Float.valueOf(s.substring(0, s.indexOf(')') - 1));
		s = s.substring(s.indexOf(',') + 1);

		ox.getInstance().transform.setToTranslation(px, py, pz);

		// ox.getInstance().transform.scale(0.5f, 0.5f, 0.5f);

		StaticElements.Oggetti.add(ox);

	}

}
