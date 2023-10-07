package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.components.InventotyComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.ItemComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.CastComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.CollisionComponent;

public class ItemSystem extends IntervalIteratingSystem {
    ComponentMapper<CastComponent> castM;
    ComponentMapper<CollisionComponent> collisionM;
    ComponentMapper<ItemComponent> itemM;
    ComponentMapper<InventotyComponent> inventotyM;
    ComponentMapper<BodyComponent> bodyM;

    public ItemSystem() {
        super(Family.all(ItemComponent.class, CollisionComponent.class).get(), ArenaEngine.STEP_TIME);
        collisionM = ComponentMapper.getFor(CollisionComponent.class);
        itemM = ComponentMapper.getFor(ItemComponent.class);
        inventotyM = ComponentMapper.getFor(InventotyComponent.class);
        castM = ComponentMapper.getFor(CastComponent.class);
        bodyM = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        CollisionComponent collisionC = collisionM.get(entity);
        ItemComponent itemC = itemM.get(entity);
        CastComponent castC = castM.get(entity);

        if (castC != null && itemC.owner == null) {
            for (Entity collidedEntity = collisionC.collidedQueue.poll();
                 collidedEntity != null;
                 collidedEntity = collisionC.collidedQueue.poll()) {

                InventotyComponent inventoryC = inventotyM.get(collidedEntity);
                // If collided entity has equipment then pick up if slot is empty
                if (inventoryC != null) {
                    boolean picked = false;
                    switch (castC.type) {
                        case ATTACK:
                            if (inventoryC.attackCast == null) {
                                inventoryC.attackCast = entity;
                                picked = true;
                            }
                            break;
                        case HELP:
                            if (inventoryC.helpCast == null) {
                                inventoryC.helpCast = entity;
                                picked = true;
                            }
                            break;
                        case MOVE:
                            if (inventoryC.moveCast == null) {
                                inventoryC.moveCast = entity;
                                picked = true;
                            }
                            break;
                        case DEFENCE:
                            if (inventoryC.defenceCast == null) {
                                inventoryC.defenceCast = entity;
                                picked = true;
                            }
                            break;
                    }

                    if (picked) {
                        BodyComponent bodyC = bodyM.get(entity);
                        bodyC.body.setActive(false);
                        bodyC.isHidden = true;
                        itemC.owner = collidedEntity;
                        collisionC.collidedQueue.clear();
                    }
                }
            }
        }


    }
}
