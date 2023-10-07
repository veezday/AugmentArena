package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class SpellComponent implements Component {
    //Must be declared
    public Entity  castItem;

    //Not necessary do declare
    public float delayOnDeath = 10;

    //Must not be changed
    public float lifeTime = 0;
    public Entity ignoreEntity;
}
