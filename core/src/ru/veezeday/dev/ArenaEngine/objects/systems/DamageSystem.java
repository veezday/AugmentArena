package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;

public class DamageSystem extends IntervalIteratingSystem {

    public DamageSystem() {
        super(Family.all().get(), ArenaEngine.STEP_TIME);
    }

    @Override
    protected void processEntity(Entity entity) {

    }
}
