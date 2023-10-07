package ru.veezeday.dev.ArenaEngine.render.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.*;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.render.components.EffectComponent;
import ru.veezeday.dev.ArenaEngine.render.components.TextureComponent;
import ru.veezeday.dev.ArenaEngine.render.ZComparator;

import java.util.Comparator;

/** Draws texture at its position */
public class RenderSystem extends SortedIteratingSystem {
    /**
     * 1 tile = 1 meter = 1 float coordinate system step.
     *  Tile size in pixels = 1/Zoom
     * */
    public static final float TILE_SIZE = 64;
    public static final float BASE_WORLD_WIDTH = 25;
    public static final float BASE_WORLD_HEIGHT = 20;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage stage;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<BodyComponent> bodyM;
    private ComponentMapper<EffectComponent> effectM;

    @SuppressWarnings("unchecked")
    public RenderSystem() {
        super(Family.one(EffectComponent.class, TextureComponent.class).all(BodyComponent.class).get(), new ZComparator());
        textureM = ComponentMapper.getFor(TextureComponent.class);
        bodyM = ComponentMapper.getFor(BodyComponent.class);
        effectM = ComponentMapper.getFor(EffectComponent.class);
        stage = new Stage(new ExtendViewport(BASE_WORLD_WIDTH, BASE_WORLD_HEIGHT));
        camera = (OrthographicCamera) stage.getCamera();
        camera.position.set(BASE_WORLD_WIDTH /2, BASE_WORLD_HEIGHT /2, 0);
        this.batch = (SpriteBatch) stage.getBatch();
        camera.zoom = 0.5f; // Zoom for development
    }

    @Override
    public void update(float deltaTime) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent textureC = textureM.get(entity);
        EffectComponent effectC = effectM.get(entity);
        BodyComponent bodyC = bodyM.get(entity);

        // Texture render
        if (!bodyC.isHidden && textureC != null) {
            for (int i = 0; i < textureC.textures.size(); i++) {
                TextureRegion region = textureC.textures.get(i);
                Vector3 shift = textureC.shifts.get(i);
                if (region != null && shift != null) {
                    float width = region.getRegionWidth() / TILE_SIZE;
                    float height = region.getRegionHeight() / TILE_SIZE;
                    float originX = width / 2;
                    float originY = height / 2;
                    float centerX = bodyC.body.getPosition().x;
                    float centerY = bodyC.body.getPosition().y;
                    float positionX = centerX+shift.x/TILE_SIZE;
                    float positionY = centerY+shift.y/TILE_SIZE;
                    float rotation = bodyC.body.getTransform().getRotation();
                    batch.draw(region,
                            (float)((positionX-centerX)*Math.cos(rotation)-(positionY-centerY)*Math.sin(rotation)+centerX)-originX,
                            (float)((positionX-centerX)*Math.sin(rotation)+(positionY-centerY)*Math.cos(rotation)+centerY)-originY,
                            originX, originY, width, height,
                            bodyC.scale.x,
                            bodyC.scale.y,
                            (float) Math.toDegrees(rotation));
                }
            }
        }

        // Particle effect render
        if (!bodyC.isHidden && effectC != null) {
            for (int i = 0; i < effectC.effects.size(); i++) {
                PooledEffect effect = effectC.effects.get(i);
                Vector3 shift = effectC.shifts.get(i);
                if (effect != null && shift != null) {
                    float centerX = bodyC.body.getPosition().x;
                    float centerY = bodyC.body.getPosition().y;
                    float positionX = centerX+shift.x/TILE_SIZE;
                    float positionY = centerY+shift.y/TILE_SIZE;
                    float rotation = bodyC.body.getTransform().getRotation();

                    effect.setPosition(
                            (float)((positionX-centerX)*Math.cos(rotation)-(positionY-centerY)*Math.sin(rotation)+centerX),
                            (float)((positionX-centerX)*Math.sin(rotation)+(positionY-centerY)*Math.cos(rotation)+centerY));
                    effect.scaleEffect(bodyC.scale.x, bodyC.scale.y, 1);
                    effect.draw(batch, deltaTime);
                    effect.update(deltaTime);

                    if (effectC.shouldDeleteOnEffectDead && effect.isComplete()) {
                        effectC.removeEffect(i);
                        entity.flags = -1;
                    }
                }
            }
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStage() {
        return stage;
    }
}
