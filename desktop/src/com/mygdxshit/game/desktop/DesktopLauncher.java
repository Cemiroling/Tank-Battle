package com.mygdxshit.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdxshit.game.TankBattle;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "TankBattle";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new TankBattle(), config);
	}
}
