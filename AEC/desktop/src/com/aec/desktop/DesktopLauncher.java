package com.aec.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import AttacksAndConquers.AttacksAndConquers;
import AttacksAndConquers.GameManager;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		config.width = 800;
		config.height = 800;

		config.fullscreen = GameManager.FullScreen;
		config.foregroundFPS = 60;
		
		new LwjglApplication(new AttacksAndConquers(), config);

	}
}
