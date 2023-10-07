package ru.veezeday.dev.userInterface.screens;

import com.badlogic.gdx.Screen;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaGame;

public class ArenaScreen implements Screen {
    private ArenaEngine engine;

    public ArenaScreen(ArenaGame parent) {
        engine = new ArenaEngine(parent, "plains");
    }

    @Override
    public void show() {
        engine.applyController();
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        engine.resize(width, height);
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
        engine.dispose();
    }
}
