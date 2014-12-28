package com.mianuddin.flappyB.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mianuddin.flappyB.flappyB;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Flappy B";
		cfg.useGL30 = false;
		cfg.width = 540;
		cfg.height = 960;
		cfg.resizable = false;

		new LwjglApplication(new flappyB(), cfg);
	}
}
