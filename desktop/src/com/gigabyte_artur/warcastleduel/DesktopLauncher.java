package com.gigabyte_artur.warcastleduel;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.gigabyte_artur.warcastleduel.warcastle.WarcastleDuelApplication;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1024, 768);
		config.setForegroundFPS(60);
		config.setTitle("WarcastleDuel");
		new Lwjgl3Application(new WarcastleDuelApplication(), config);
	}
}
