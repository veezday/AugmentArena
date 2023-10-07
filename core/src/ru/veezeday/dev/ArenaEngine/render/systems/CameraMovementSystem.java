package ru.veezeday.dev.ArenaEngine.render.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.components.CameraComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.MovementComponent;

/** Note only one entity should have isFollowing = true, otherwise camera will blink between entities every STEP_TIME */
public class CameraMovementSystem extends IntervalIteratingSystem {
    private ComponentMapper<CameraComponent> cameraM;
    private ComponentMapper<BodyComponent> bodyM;
    private ComponentMapper<MovementComponent> movementM;

    public CameraMovementSystem() {
        super(Family.all(CameraComponent.class, BodyComponent.class, MovementComponent.class).get(), ArenaEngine.STEP_TIME);
        cameraM = ComponentMapper.getFor(CameraComponent.class);
        bodyM = ComponentMapper.getFor(BodyComponent.class);
        movementM = ComponentMapper.getFor(MovementComponent.class);
    }


    @Override
    protected void processEntity(Entity entity) {
        CameraComponent cameraC = cameraM.get(entity);
        BodyComponent bodyC = bodyM.get(entity);
        MovementComponent movementC = movementM.get(entity);

        if (cameraC.isFollowing && cameraC.followingBody != null) {
            movementC.targetVelocity.set(cameraC.followingBody.body.getPosition().sub(bodyC.body.getPosition()));
            cameraC.camera.position.set(bodyC.body.getPosition(), cameraC.camera.position.z);
        }
    }
}
