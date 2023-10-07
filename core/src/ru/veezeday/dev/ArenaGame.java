package ru.veezeday.dev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.veezeday.dev.userInterface.screens.LoadingScreen;
import ru.veezeday.dev.userInterface.screens.ArenaScreen;
import ru.veezeday.dev.userInterface.screens.MenuScreen;
import ru.veezeday.dev.userInterface.screens.PreferencesScreen;
import ru.veezeday.dev.userInterface.screens.ScreenType;

public class ArenaGame extends Game {
	private ArenaScreen arenaScreen;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private PreferencesScreen preferencesScreen;
	private AppPreferences appPreferences;
	public AppAssetManager assetManager = new AppAssetManager();

	@Override
	public void create () {
		appPreferences = new AppPreferences();
		changeScreen(ScreenType.LOADING);
	}

	public void changeScreen(ScreenType type) {
		 switch (type) {
			 case ARENA:
				 if (arenaScreen == null) arenaScreen = new ArenaScreen(this);
				 this.setScreen(arenaScreen);
				 break;
			 case MENU:
				 if (menuScreen == null) menuScreen = new MenuScreen(this);
				 this.setScreen(menuScreen);
				 break;
			 case LOADING:
				 if (loadingScreen == null) loadingScreen = new LoadingScreen(this);
				 this.setScreen(loadingScreen);
				 break;
			 case PREFERENCES:
				 if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				 this.setScreen(preferencesScreen);
				 break;
		 }
	}

	public AppPreferences getAppPreferences() {
		return appPreferences;
	}

	@Override
    public void dispose () {
		super.dispose();
        assetManager.dispose();
    }
}
