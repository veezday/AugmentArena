package ru.veezeday.dev.ArenaEngine.physic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import ru.veezeday.dev.ArenaEngine.objects.components.EntityTypeComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.HealthComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.ItemComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.CollisionComponent;

public class CollisionListener implements ContactListener {
    ComponentMapper<CollisionComponent> collisionM;

    public CollisionListener() {
        collisionM = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getBody().getUserData();
        Object objectB = fixtureB.getBody().getUserData();

        if (objectA instanceof Entity && objectB instanceof Entity) {
            Entity entityA = (Entity) objectA;
            Entity entityB = (Entity) objectB;
            CollisionComponent collisionA = collisionM.get(entityA);
            CollisionComponent collisionB = collisionM.get(entityB);
            if (collisionA != null) collisionA.collidedQueue.add(entityB);
            if (collisionB != null) collisionB.collidedQueue.add(entityA);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
