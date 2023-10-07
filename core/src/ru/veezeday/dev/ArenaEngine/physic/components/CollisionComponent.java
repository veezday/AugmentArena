package ru.veezeday.dev.ArenaEngine.physic.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Do something on contact
 */
public class CollisionComponent implements Component {
    public LinkedBlockingQueue<Entity> collidedQueue = new LinkedBlockingQueue<>();
}
