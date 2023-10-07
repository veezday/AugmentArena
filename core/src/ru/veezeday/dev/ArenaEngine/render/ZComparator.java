package ru.veezeday.dev.ArenaEngine.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import ru.veezeday.dev.ArenaEngine.render.components.EffectComponent;
import ru.veezeday.dev.ArenaEngine.render.components.TextureComponent;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<EffectComponent> effectM;

    public ZComparator() {
        textureM = ComponentMapper.getFor(TextureComponent.class);
        effectM = ComponentMapper.getFor(EffectComponent.class);
    }

    @Override
    public int compare(Entity entity1, Entity entity2) {
        TextureComponent textureC1 = textureM.get(entity1);
        TextureComponent textureC2 = textureM.get(entity2);
        EffectComponent effectC1 = effectM.get(entity1);
        EffectComponent effectC2 = effectM.get(entity2);
        float z1;
        float z2;

        if (textureC1 == null) {
            z1 = effectC1.shifts.get(0).z;
        } else {
            z1 = textureC1.shifts.get(0).z;
        }
        if (textureC2 == null) {
            z2 = effectC2.shifts.get(0).z;
        } else {
            z2 = textureC2.shifts.get(0).z;
        }

        int res = Float.compare(z1, z2);

        return res;
    }
}
