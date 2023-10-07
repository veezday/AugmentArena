package ru.veezeday.dev.ArenaEngine;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * IteratingSystem uses to do something anytime as possible.
 * StepIteratingSystem uses to do something each world step.
 * */
public abstract class StepIteratingSystem extends IteratingSystem {
    private boolean isStepForEachEntity;
    private static float accumulator = 0f;
    public static final float MAX_STEP_TIME = 1/45f;
    public LinkedBlockingQueue<Entity> queue;

    /**
     * @param isStepForEachEntity if true runs eachEntityStep() for each entity in family param.
     * Else runs step().
     * */
    public StepIteratingSystem(Family family, boolean isStepForEachEntity) {
        super(family);
        queue = new LinkedBlockingQueue<>();
        this.isStepForEachEntity = isStepForEachEntity;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if (accumulator >= MAX_STEP_TIME) {
            accumulator -= MAX_STEP_TIME;
            if (isStepForEachEntity) {
                for (Entity entity = queue.poll(); entity != null; entity = queue.poll()) {
                    eachEntityStep(entity);
                }
            } else {
                step();
            }
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        queue.add(entity);
    }

    /**
     * Helps to do something each world step.
     * */
    public abstract void step();
    /**
     * Helps to do something each world step for each entity in family.
     * */
    public abstract void eachEntityStep(Entity entity);
}
