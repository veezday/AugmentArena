package ru.veezeday.dev.ArenaEngine.physic.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.physics.box2d.World;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.StepIteratingSystem;

/**
* Generates world steps
*/
public class WorldStepSystem extends IntervalSystem {
    private World world;

    public WorldStepSystem(World world) {
        super(ArenaEngine.STEP_TIME);
        this.world = world;
    }

    @Override
    protected void updateInterval() {
        world.step(1/45f, 6, 2);
    }
}
