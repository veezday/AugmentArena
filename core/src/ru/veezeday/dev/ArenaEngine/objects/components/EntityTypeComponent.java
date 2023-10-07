package ru.veezeday.dev.ArenaEngine.objects.components;

import com.badlogic.ashley.core.Component;

public class EntityTypeComponent implements Component {
    public EntityType type = EntityType.SCENERY;
    public SideType side = SideType.NEUTRAL;

    public enum EntityType {
        CREATURE,
        SCENERY,
        ITEM,
        SPELL;
    }

    public enum SideType {
        ALLY,
        RIVAL,
        NEUTRAL;
    }
}
