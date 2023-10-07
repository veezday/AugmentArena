package ru.veezeday.dev.ArenaEngine.physic.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/** Access to movement functions */

public class MovementComponent implements Component {
    public Vector2 targetVelocity = new Vector2(0,0);
    /**In radians*/
    public float targetAngle = 0;
    public float maxVelocitySpeed = 3;
    public float maxAngleSpeed = 1;
    public float acceleration = 0.5f;
    public MovementType accelerationType = MovementType.LINEAR;

    public enum MovementType {
        LINEAR;
    }
}
