package ru.veezeday.dev.ArenaEngine.physic.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Is entity has body to detect collision
 */
public class BodyComponent implements Component {
    public Body body = null;
    public Vector2 scale = new Vector2(1, 1);
    public boolean isHidden = false;
}
