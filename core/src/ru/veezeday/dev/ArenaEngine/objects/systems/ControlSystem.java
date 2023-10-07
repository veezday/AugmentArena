package ru.veezeday.dev.ArenaEngine.objects.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.components.ControlComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.InventotyComponent;
import ru.veezeday.dev.ArenaEngine.objects.components.CastComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.MovementComponent;

/** Sets movement vector out of control signals */
public class ControlSystem extends IntervalIteratingSystem {
    private ComponentMapper<ControlComponent> controlM;
    private ComponentMapper<MovementComponent> movementM;
    private ComponentMapper<InventotyComponent> inventoryM;
    private ComponentMapper<CastComponent> spellM;

    public ControlSystem() {
        super(Family.all(ControlComponent.class, MovementComponent.class).get(), ArenaEngine.STEP_TIME);
        controlM = ComponentMapper.getFor(ControlComponent.class);
        movementM = ComponentMapper.getFor(MovementComponent.class);
        inventoryM = ComponentMapper.getFor(InventotyComponent.class);
        spellM = ComponentMapper.getFor(CastComponent.class);
    }

    @Override
    protected void processEntity(Entity entity) {
        // Movement
        ControlComponent controlC = controlM.get(entity);
        MovementComponent movementC = movementM.get(entity);
        Vector2 newMovementVector = new Vector2(movementC.targetVelocity);
        if (controlC.left && !controlC.right) {
            newMovementVector.x = -movementC.maxVelocitySpeed;
        } else if (!controlC.left && controlC.right) {
            newMovementVector.x = movementC.maxVelocitySpeed;
        } else {
            newMovementVector.x = 0;
        }
        if (controlC.up && !controlC.down) {
            newMovementVector.y = movementC.maxVelocitySpeed;
        } else if (!controlC.up && controlC.down) {
            newMovementVector.y = -movementC.maxVelocitySpeed;
        } else {
            newMovementVector.y = 0;
        }
        movementC.targetVelocity.set(newMovementVector);

        // Attack
        InventotyComponent inventoryC = inventoryM.get(entity);
        if (inventoryC != null) {
            Entity attackSpell = inventoryC.attackCast;
            if (attackSpell != null) {
                CastComponent attackSpellC = spellM.get(attackSpell);
                attackSpellC.isActive = controlC.lmb;
            }

            Entity helpSpell = inventoryC.helpCast;
            if (helpSpell != null) {
                CastComponent helpSpellC = spellM.get(helpSpell);
                helpSpellC.isActive = controlC.rmb;
            }

            Entity moveSpell = inventoryC.moveCast;
            if (moveSpell != null) {
                CastComponent moveSpellC = spellM.get(moveSpell);
                moveSpellC.isActive = controlC.shift;
            }

            Entity defenceSpell = inventoryC.defenceCast;
            if (defenceSpell != null) {
                CastComponent defenceSpellC = spellM.get(defenceSpell);
                defenceSpellC.isActive = controlC.space;
            }
        }
    }
}
