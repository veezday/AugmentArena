package ru.veezeday.dev.ArenaEngine.physic.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.MovementComponent;

/**Apply force to Body to direct him leads movement vector.
 * Movement vector demonstrates linear velocity that body must reach
 * by this system in one of the ways.
 * */
public class MovementSystem extends IntervalIteratingSystem {
    ComponentMapper<MovementComponent> movementM;
    ComponentMapper<BodyComponent> bodyM;
    MovementComponent movementC;
    BodyComponent bodyC;

    //TODO:
    // fix high deceleration problem
    // add smooth mode

    public MovementSystem() {
        super(Family.all(MovementComponent.class, BodyComponent.class).get(), ArenaEngine.STEP_TIME);
        movementM = ComponentMapper.getFor(MovementComponent.class);
        bodyM = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        movementC = movementM.get(entity);
        bodyC = bodyM.get(entity);
        Vector2 currentVelocity = new Vector2(bodyC.body.getLinearVelocity());
        Vector2 targetVelocity = movementC.targetVelocity;
        float currentAngle = bodyC.body.getAngularVelocity();
        float targetAngle = movementC.targetAngle;

        if (currentVelocity.x != targetVelocity.x) {
            switch (movementC.accelerationType) {
                case LINEAR:
                    currentVelocity.x = linear(currentVelocity.x, targetVelocity.x, movementC.acceleration, movementC.maxVelocitySpeed);
                    break;
                default:
                    System.out.println("Unknown acceleration type: "+movementC.accelerationType);
                    break;
            }
            bodyC.body.setLinearVelocity(currentVelocity);
        }
        if (currentVelocity.y != targetVelocity.y) {
            switch (movementC.accelerationType) {
                case LINEAR:
                    currentVelocity.y = linear(currentVelocity.y, targetVelocity.y, movementC.acceleration, movementC.maxVelocitySpeed);
                    break;
                default:
                    System.out.println("Unknown acceleration type: "+movementC.accelerationType);
                    break;
            }
            bodyC.body.setLinearVelocity(currentVelocity);
        }

        if (currentAngle != targetAngle) {
            switch (movementC.accelerationType) {
                case LINEAR:
                    currentAngle = linear(currentAngle, targetAngle, movementC.acceleration, movementC.maxAngleSpeed);
                    break;
                default:
                    System.out.println("Unknown acceleration type: "+movementC.accelerationType);
                    break;
            }
            bodyC.body.setAngularVelocity(currentAngle);
        }
    }

    private float linear(float current, float target, float acceleration, float max_speed) {
        if (target > current) {
            current += max_speed * acceleration;
            if (target < current) current = target;
        } else if (target < current) {
            current -= max_speed * acceleration;
            if (target > current) current = target;
        }
        return current;
    }
}
