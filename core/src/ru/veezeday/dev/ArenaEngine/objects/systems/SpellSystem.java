package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.EntityFactory;
import ru.veezeday.dev.ArenaEngine.objects.components.CastComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.SpellComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.CollisionComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.MovementComponent;
import ru.veezeday.dev.ArenaEngine.render.components.EffectComponent;

/** Controls launched spells */
// TODO add range limit
public class SpellSystem extends IntervalIteratingSystem {
    ComponentMapper<SpellComponent> spellMapper;
    ComponentMapper<CollisionComponent> collisionMapper;
    ComponentMapper<EffectComponent> effectMapper;
    ComponentMapper<CastComponent> castMapper;
    ComponentMapper<MovementComponent> movementMapper;
    ComponentMapper<BodyComponent> bodyMapper;
    EntityFactory factory;

    public SpellSystem(EntityFactory entityFactory) {
        super(Family.all(SpellComponent.class, CollisionComponent.class, EffectComponent.class).get(), ArenaEngine.STEP_TIME);
        spellMapper = ComponentMapper.getFor(SpellComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        effectMapper = ComponentMapper.getFor(EffectComponent.class);
        castMapper = ComponentMapper.getFor(CastComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        factory = entityFactory;
    }

    @Override
    protected void processEntity(Entity entity) {
        SpellComponent spellComponent = spellMapper.get(entity);
        CollisionComponent collisionComponent = collisionMapper.get(entity);
        EffectComponent effectComponent = effectMapper.get(entity);
        CastComponent castComponent = castMapper.get(spellComponent.castItem);

        // Remove spell entity on lifeTime end;
        spellComponent.lifeTime += this.getInterval();
        if (spellComponent.lifeTime >= spellComponent.delayOnDeath) {
            entity.flags = -1;
        }

        for (Entity collidedEntity = collisionComponent.collidedQueue.poll();
             collidedEntity != null;
             collidedEntity = collisionComponent.collidedQueue.poll()) {
            if (collidedEntity != spellComponent.ignoreEntity) {
                if (!castComponent.hasPiercing) {
                    effectComponent.removeAll();
                    effectComponent.shouldDeleteOnEffectDead = true;

                    BodyComponent bodyComponent = bodyMapper.get(entity);
                    bodyComponent.body.setActive(false);

                    MovementComponent movementComponent = movementMapper.get(entity);
                    movementComponent.targetVelocity.set(0,0);
                    movementComponent.targetAngle = 0;
                }
                ParticleEffectPool.PooledEffect effect = castComponent.effectOnContactPool.obtain();
                effect.scaleEffect(0.75f);
                effectComponent.addEffect(effect, 0, 0, 2);
            }
        }
    }
}
