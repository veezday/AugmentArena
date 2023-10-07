package ru.veezeday.dev.userInterface.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ru.veezeday.dev.ArenaGame;

public class LoadingScreen implements Screen {
    private float timeout; //freeze screen after loading
    private ArenaGame parent;
    private Stage stage;
    private Skin uiSkin;
    private Table table;
    private TextureAtlas textureAtlas;
    private Label label;
    private Label loadStageLabel;
    private ProgressBar progressBar;
    private int currLoadStage = 0;
    private String currLoadStageString;
    private static final int INTERFACE = 0,
            TEXTURES = 1,
            EFFECTS = 2,
            SOUNDS = 3,
            MUSICS = 4,
            FINISH = 5;

    public LoadingScreen(ArenaGame parent) {
        timeout = parent.getAppPreferences().getLoadingScreenTimeout();
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        stage.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        System.out.println("Start loading assets...");
        parent.assetManager.queueAddSkin();
        parent.assetManager.finishLoading();
        uiSkin = parent.assetManager.get(parent.assetManager.uiSkin);
        parent.assetManager.queueAddImages();
        parent.assetManager.finishLoading();
        parent.assetManager.queueAddEffects();

        table = new Table();
        table.setFillParent(true);
        table.setWidth(stage.getWidth());
        stage.addActor(table);
        label = new Label("Augment Arena", uiSkin);
        label.setAlignment(Align.center);
        loadStageLabel = new Label("Loading assets...", uiSkin);
        loadStageLabel.setAlignment(Align.center);
        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, uiSkin);

        table.add(label).fillX().uniformX();
        table.row().pad(40, 0, 10, 0);
        table.add(progressBar).fillX().uniformX().width(stage.getWidth()-40);
        table.row().pad(10, 0, 10, 0);
        table.add(loadStageLabel).fillX().uniformX();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        progressBar.setValue(1.0f/FINISH * currLoadStage);
        switch (currLoadStage) {
            case INTERFACE:
                currLoadStageString = "Loading interface...";
                System.out.println(currLoadStageString);
                loadStageLabel.setText(currLoadStageString);
                break;
            case TEXTURES:
                currLoadStageString = "Loading textures...";
                System.out.println(currLoadStageString);
                loadStageLabel.setText(currLoadStageString);
                break;
            case EFFECTS:
                currLoadStageString = "Loading effects...";
                System.out.println(currLoadStageString);
                loadStageLabel.setText(currLoadStageString);
                break;
            case SOUNDS:
                currLoadStageString = "Loading sounds...";
                System.out.println(currLoadStageString);
                loadStageLabel.setText(currLoadStageString);
                break;
            case MUSICS:
                currLoadStageString = "Loading musics...";
                System.out.println(currLoadStageString);
                loadStageLabel.setText(currLoadStageString);
                break;
            case FINISH:
                currLoadStageString = "Loading finished!";
                loadStageLabel.setText(currLoadStageString);
                break;
        }
        if (currLoadStage != FINISH) { // not last stage
            if (parent.assetManager.update()) {
                currLoadStage++; // wait till something loads
            }
        } else {
            timeout -= delta;
            if (timeout < 0) {
                System.out.println(currLoadStageString);
                parent.changeScreen(ScreenType.MENU);
                timeout = 1.0f;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        table.getCell(progressBar).width(width-40);
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
