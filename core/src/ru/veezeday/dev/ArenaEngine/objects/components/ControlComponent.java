package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ControlComponent implements Component {
    public boolean left, right, up, down = false;
    public boolean lmb, rmb, mmb = false;
    public boolean shift, space = false;
    /** The point where entity is looking */
    public Vector2 viewPoint = new Vector2(0, 0);
}
