package ru.veezeday.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class AppAssetManager extends AssetManager {
    public final String uiSkin = "loading/skin/geo_tracer.json";
    public final String spriteAtlas = "game/sprites/sprites.atlas";
    private ParticleEffectPool fireballPool;
    private ParticleEffectPool explosionPool;

    public void queueAddImages() {
        load(spriteAtlas, TextureAtlas.class);
    }

    public void queueAddSkin() {
        String skinAtlas = "loading/skin/geo_tracer.atlas";
        SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter(skinAtlas);
        load(uiSkin, Skin.class, skinParameter);
    }

    public void queueAddEffects() {
        ParticleEffect fireball = new ParticleEffect();
        fireball.load(Gdx.files.internal("game/effects/fireball.pe"), getAtlas());
        fireballPool = new ParticleEffectPool(fireball, 5, 100);

        ParticleEffect explosion = new ParticleEffect();
        explosion.load(Gdx.files.internal("game/effects/explosion.pe"), getAtlas());
        explosionPool = new ParticleEffectPool(explosion, 5, 100);
    }

    private TextureAtlas getAtlas() {
        return this.get(spriteAtlas, TextureAtlas.class);
    }

    public TextureRegion getTexture(String textureName) {
        return getAtlas().findRegion(textureName);
    }

    public ParticleEffectPool getFireballPool() {
        return fireballPool;
    }

    public ParticleEffectPool getExplosionPool() {
        return explosionPool;
    }
}
