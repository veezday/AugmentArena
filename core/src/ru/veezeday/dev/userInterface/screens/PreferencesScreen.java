package ru.veezeday.dev.userInterface.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.veezeday.dev.ArenaGame;

public class PreferencesScreen implements Screen {
    private ArenaGame parent;
    private Stage stage;
    private Skin skin;
    private Label titleLabel;
    private Label musicVolumeLabel;
    private Label soundVolumeLabel;
    private Label musicOnOffLabel;
    private Label soundEffectsOnOffLabel;

    public PreferencesScreen(ArenaGame parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        skin = parent.assetManager.get(parent.assetManager.uiSkin);

        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicVolumeSlider.setValue(parent.getAppPreferences().getMusicVolume());
        musicVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getAppPreferences().setMusicVolume(musicVolumeSlider.getValue());
                return false;
            }
        });

        final Slider soundEffectVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundEffectVolumeSlider.setValue(parent.getAppPreferences().getSoundEffectsVolume());
        soundEffectVolumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getAppPreferences().setSoundEffectsVolume(soundEffectVolumeSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckBox = new CheckBox(null, skin);
        musicCheckBox.setChecked(parent.getAppPreferences().isMusicEnabled());
        musicCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getAppPreferences().setMusicEnabled(musicCheckBox.isChecked());
                return false;
            }
        });

        final CheckBox soundEffectsCheckBox = new CheckBox(null, skin);
        soundEffectsCheckBox.setChecked(parent.getAppPreferences().isSoundEffectsEnabled());
        soundEffectsCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getAppPreferences().setSoundEffectsEnabled(soundEffectsCheckBox.isChecked());
                return false;
            }
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.MENU);
            }
        });

        titleLabel = new Label("Preferences", skin);
        musicVolumeLabel = new Label("Music volume", skin);
        soundVolumeLabel = new Label("Sound effects volume", skin);
        musicOnOffLabel = new Label("Music", skin);
        soundEffectsOnOffLabel = new Label("Sound effects", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(10, 0, 0,10);
        table.add(musicVolumeLabel).left();
        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckBox);
        table.row().pad(10, 0, 0,10);
        table.add(soundVolumeLabel).left();
        table.add(soundEffectVolumeSlider);
        table.row().pad(10, 0, 0,10);
        table.add(soundEffectsOnOffLabel).left();
        table.add(soundEffectsCheckBox);
        table.row().pad(10, 0, 0,10);
        table.add(backButton).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
