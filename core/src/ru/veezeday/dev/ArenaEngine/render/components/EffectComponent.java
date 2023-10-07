package ru.veezeday.dev.ArenaEngine.render.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
/** Same logic as TextureComponent has */
public class EffectComponent implements Component {
    public ArrayList<PooledEffect> effects = new ArrayList<>(1);
    public boolean shouldDeleteOnEffectDead = false;
    public ArrayList<Vector3> shifts = new ArrayList<>(1);

    public void addEffect(PooledEffect effect, float shiftX, float shiftY, float layerZ) {
        effects.add(effect);
        effect.start();
        shifts.add(new Vector3(shiftX, shiftY, layerZ));
        sort();
    }

    public void removeEffect(int index) {
        PooledEffect effect = effects.get(index);
        effect.reset();
        effects.remove(index);
        shifts.remove(index);
    }

    public void removeAll() {
        for (int i = 0; i < effects.size(); i++) {
            removeEffect(i);
        }
    }

    private void sort() {
        if (effects.size() > 1) {
            for (int i = 0; i < effects.size(); i++) {
                for (int j = 1; j < effects.size(); j++) {
                    if (shifts.get(j).z < shifts.get(j-1).z) {
                        Vector3 tempShift = new Vector3(shifts.get(j));
                        shifts.set(j, shifts.get(j-1));
                        shifts.set(j-1, tempShift);
                        PooledEffect tempEffect = effects.get(j);
                        effects.set(j, effects.get(j-1));
                        effects.set(j-1, tempEffect);
                    }
                }
            }
        }
    }
}
