package ru.veezeday.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class AppPreferences {
    private static final String MUSIC_VOLUME = "music.volume";
    private static final String MUSIC_ENABLED = "music.enabled";
    private static final String SOUND_VOLUME = "sound.volume";
    private static final String SOUND_ENABLED = "sound.enabled";
    private static final String PREFS_NAME = "arena.preferences";
    private static final String LOADING_SCREEN_TIMEOUT = "loading.screen.timeout";
    public LwjglApplicationConfiguration config;

    public AppPreferences() {
        config = new LwjglApplicationConfiguration();
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(SOUND_ENABLED);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundEffectsVolume() {
        return getPrefs().getFloat(SOUND_VOLUME, 0.5f);
    }

    public void setSoundEffectsVolume(float volume) {
        getPrefs().putFloat(SOUND_VOLUME, volume);
        getPrefs().flush();
    }

    public float getLoadingScreenTimeout() {
        float screenTimeout = getPrefs().getFloat(LOADING_SCREEN_TIMEOUT, 1.0f);
        if (screenTimeout < 0) screenTimeout = 0;
        return screenTimeout;
    }

    public void setLoadingScreenTimeout(float timeout) {
        getPrefs().putFloat(LOADING_SCREEN_TIMEOUT, timeout);
        getPrefs().flush();
    }
}
