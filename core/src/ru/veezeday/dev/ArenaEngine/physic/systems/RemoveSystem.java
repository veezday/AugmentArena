package ru.veezeday.dev.ArenaEngine.physic.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;

/** Removes entities on -1 flag */
public class RemoveSystem extends IntervalIteratingSystem {
    private ArenaEngine engine;
    private World world;

    public RemoveSystem(ArenaEngine engine, World world) {
        super(Family.all().get(), ArenaEngine.STEP_TIME);
        this.engine = engine;
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity) {
        if (entity.flags == -1) {
            BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
            if (bodyComponent != null) {
                world.destroyBody(bodyComponent.body);
            }
            engine.removeEntity(entity);
        }
    }
}
