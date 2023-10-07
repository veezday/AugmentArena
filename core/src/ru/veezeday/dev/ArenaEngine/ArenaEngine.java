package ru.veezeday.dev.ArenaEngine;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.veezeday.dev.ArenaEngine.objects.KeyboardController;
import ru.veezeday.dev.ArenaEngine.objects.LevelGenerator;
import ru.veezeday.dev.ArenaEngine.objects.systems.*;
import ru.veezeday.dev.ArenaEngine.physic.CollisionListener;
import ru.veezeday.dev.ArenaEngine.physic.systems.MovementSystem;
import ru.veezeday.dev.ArenaEngine.physic.systems.RemoveSystem;
import ru.veezeday.dev.ArenaEngine.physic.systems.WorldStepSystem;
import ru.veezeday.dev.ArenaEngine.render.systems.CameraMovementSystem;
import ru.veezeday.dev.ArenaEngine.render.systems.RenderDebugSystem;
import ru.veezeday.dev.ArenaEngine.render.systems.RenderSystem;
import ru.veezeday.dev.ArenaGame;
import ru.veezeday.dev.ArenaEngine.objects.EntityFactory;

public class ArenaEngine extends PooledEngine {
    public static final float STEP_TIME = 1/45f;
    private ArenaGame parent;
    private Stage stage;
    private KeyboardController controller;
    private final World world;
    private OrthographicCamera camera;
    private EntityFactory entityFactory;
    private LevelGenerator levelGenerator;
    private RenderSystem renderSystem;

    public ArenaEngine(ArenaGame parent, String level) {
        this.parent = parent;
        controller = new KeyboardController();
        renderSystem = new RenderSystem();
        stage = renderSystem.getStage();
        camera = renderSystem.getCamera();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new CollisionListener());
        entityFactory = EntityFactory.getInstance(this);
        levelGenerator = LevelGenerator.getInstance(entityFactory);
        addSystems();
        levelGenerator.generateLevel(level);
    }

    private void addSystems() {
        addSystem(renderSystem);
        addSystem(new WorldStepSystem(world));
        addSystem(new RenderDebugSystem(world, camera));
        addSystem(new PlayerControllerSystem(controller, camera));
        addSystem(new RemoveSystem(this, world));
        addSystem(new ControlSystem());
        addSystem(new MovementSystem());
        addSystem(new CameraMovementSystem());
        addSystem(new ItemSystem());
        addSystem(new CastingSystem(entityFactory));
        addSystem(new SpellSystem(entityFactory));
    }

    public void applyController() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.07f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        super.update(deltaTime);
    }

    public void resize(int width, int height) {
        Vector3 oldCamPos = new Vector3(camera.position);
        stage.getViewport().update(width, height, true);
        camera.position.set(oldCamPos);
    }

    public ArenaGame getParent() {
        return parent;
    }

    public World getWorld() {
        return world;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void dispose() {
        stage.dispose();
        world.dispose();

    }
}
