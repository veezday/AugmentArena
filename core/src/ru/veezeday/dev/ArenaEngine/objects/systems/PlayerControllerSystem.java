package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.components.ControlComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.PlayerComponent;
import ru.veezeday.dev.ArenaEngine.objects.KeyboardController;

/** Rewrites transfer signal from player controller to entity control system */
public class PlayerControllerSystem extends IntervalIteratingSystem {
    private ComponentMapper<PlayerComponent> playerM;
    private ComponentMapper<ControlComponent> controlM;
    private KeyboardController controller;
    private OrthographicCamera camera;

    @SuppressWarnings("uncheked")
    public PlayerControllerSystem(KeyboardController controller, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get(), ArenaEngine.STEP_TIME);
        this.controller = controller;
        this.camera = camera;
        playerM = ComponentMapper.getFor(PlayerComponent.class);
        controlM = ComponentMapper.getFor(ControlComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        ControlComponent controlC = controlM.get(entity);
        controlC.left = controller.left;
        controlC.right = controller.right;
        controlC.up = controller.up;
        controlC.down = controller.down;
        controlC.shift = controller.shift;
        controlC.space = controller.space;
        controlC.lmb = controller.lmb;
        controlC.rmb = controller.rmb;
        controlC.mmb = controller.mmb;
        // Convert mouse screen location to mouse world location
        Vector3 viewPoint = new Vector3(controller.mouseLocation.x,
                controller.mouseLocation.y, 0);
        camera.unproject(viewPoint);
        controlC.viewPoint.set(viewPoint.x, viewPoint.y);
    }
}
