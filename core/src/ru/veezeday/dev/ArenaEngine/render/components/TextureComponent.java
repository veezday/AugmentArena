package ru.veezeday.dev.ArenaEngine.render.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import ru.veezeday.dev.ArenaEngine.render.systems.RenderSystem;

import java.util.ArrayList;
import java.util.Comparator;

public class TextureComponent implements Component {
    /** Contains all textures of one entity/
     *  For each texture must be own shift*/
    public ArrayList<TextureRegion> textures = new ArrayList<>(1);
    /** Shows shift of all textures of one entity:
     * x,y shift from entity center, z is texture layer */
    public ArrayList<Vector3> shifts = new ArrayList<>(1);
    /** Each texture must have own shift from entity center */
    public void addTexture(TextureRegion textureRegion, float shiftX, float shiftY, float layerZ) {
        textures.add(textureRegion);
        shifts.add(new Vector3(shiftX, shiftY, layerZ));
        sort();
    }

    private void sort() {
        if (textures.size() > 1) {
            for (int i = 0; i < textures.size(); i++) {
                for (int j = 1; j < textures.size(); j++) {
                    if (shifts.get(j).z < shifts.get(j-1).z) {
                        Vector3 tempShift = new Vector3(shifts.get(j));
                        shifts.set(j, shifts.get(j-1));
                        shifts.set(j-1, tempShift);
                        TextureRegion tempRegion = new TextureRegion(textures.get(j));
                        textures.set(j, textures.get(j-1));
                        textures.set(j-1, tempRegion);
                    }
                }
            }
        }
    }
}
