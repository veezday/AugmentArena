package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.veezeday.dev.ArenaEngine.objects.EntityFactory;
import ru.veezeday.dev.ArenaEngine.objects.components.ControlComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.InventotyComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.CastComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;

public class CastingSystem extends IteratingSystem {
    ComponentMapper<CastComponent> castM;
    ComponentMapper<InventotyComponent> inventoryM;
    ComponentMapper<ControlComponent> controlM;
    ComponentMapper<BodyComponent> bodyM;
    EntityFactory factory;

    public CastingSystem(EntityFactory factory) {
        super(Family.all(InventotyComponent.class, ControlComponent.class, BodyComponent.class).get());
        castM = ComponentMapper.getFor(CastComponent.class);
        inventoryM = ComponentMapper.getFor(InventotyComponent.class);
        controlM = ComponentMapper.getFor(ControlComponent.class);
        bodyM = ComponentMapper.getFor(BodyComponent.class);
        this.factory = factory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InventotyComponent inventoryC = inventoryM.get(entity);
        ControlComponent controlC = controlM.get(entity);
        BodyComponent bodyC = bodyM.get(entity);

        if (inventoryC.attackCast != null) {
            CastComponent castC = castM.get(inventoryC.attackCast);
            if (castC.isActive) {
                Vector2 to = new Vector2(controlC.viewPoint);
                to.sub(bodyC.body.getPosition());
                useSpell(entity, inventoryC.attackCast, bodyC.body.getPosition(), to, deltaTime);
            }
            else castC.hasCast = false;
        }

        if (inventoryC.helpCast != null) {
            CastComponent castC = castM.get(inventoryC.helpCast);
            if (castC.isActive) {
                Vector2 to = new Vector2(controlC.viewPoint);
                to.sub(bodyC.body.getPosition());
                useSpell(entity, inventoryC.helpCast, bodyC.body.getPosition(), to, deltaTime);
            }
            else castC.hasCast = false;
        }

        if (inventoryC.defenceCast != null) {
            CastComponent castC = castM.get(inventoryC.defenceCast);
            if (castC.isActive) {
                Vector2 to = new Vector2(controlC.viewPoint);
                to.sub(bodyC.body.getPosition());
                useSpell(entity, inventoryC.defenceCast, bodyC.body.getPosition(), to, deltaTime);
            }
            else castC.hasCast = false;
        }

        if (inventoryC.moveCast != null) {
            CastComponent castC = castM.get(inventoryC.moveCast);
            if (castC.isActive) {
                Vector2 to = new Vector2(controlC.viewPoint);
                to.sub(bodyC.body.getPosition());
                useSpell(entity, inventoryC.moveCast, bodyC.body.getPosition(), to, deltaTime);
            }
            else castC.hasCast = false;
        }
    }

    private void useSpell(Entity entity, Entity castItem, Vector2 from, Vector2 to, float deltaTime) {
        CastComponent castC = castM.get(castItem);

        if (!(castC.useWay == CastComponent.UseWay.CHARGE && castC.hasCast)) {
            castC.charge += deltaTime;

            // Cast if charge completed
            if (castC.charge >= castC.chargeTime) {
                switch (castC.travelWay) {
                    case STAB:
                        break;
                    case SWING:
                        break;
                    case HITSCAN:
                        break;
                    case PROJECTILE:
                        to.setLength2(castC.speed*castC.speed);
                        if (castC.angle != 0) {
                            to.rotateDeg(MathUtils.random(-castC.angle/2, castC.angle/2));
                        }
                        factory.createFireballSpell(from, to, castC.speed, entity, castItem);
                        castC.charge = 0;
                        castC.hasCast = true;
                        break;
                }
            }
        }
    }
}
