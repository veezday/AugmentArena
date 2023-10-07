package ru.veezeday.dev.ArenaEngine.objects;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import ru.veezeday.dev.AppAssetManager;
import ru.veezeday.dev.ArenaEngine.ArenaEngine;
import ru.veezeday.dev.ArenaEngine.objects.components.*;
import ru.veezeday.dev.ArenaEngine.physic.components.BodyComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.CollisionComponent;
import ru.veezeday.dev.ArenaEngine.physic.components.MovementComponent;
import ru.veezeday.dev.ArenaEngine.render.components.EffectComponent;
import ru.veezeday.dev.ArenaEngine.render.components.TextureComponent;
import ru.veezeday.dev.ArenaEngine.render.systems.RenderSystem;

public class EntityFactory {
    private static EntityFactory instance;
    private ArenaEngine engine;
    private AppAssetManager assetManager;
    private BodyFactory bodyFactory;

    private EntityFactory(ArenaEngine engine) {
        bodyFactory = BodyFactory.getInstance(engine.getWorld());
        this.engine = engine;
        this.assetManager = engine.getParent().assetManager;
    }

    public static EntityFactory getInstance(ArenaEngine engine) {
        if (instance == null) instance = new EntityFactory(engine);
        return instance;
    }

    public Entity createCameraEntity(Entity followingEntity) {
        Entity entity = createEntity();
        BodyComponent bodyComponent = createBodyComponent(0,0,32f,32f,
                MaterialType.NONE, BodyDef.BodyType.KinematicBody, true, true);
        bodyComponent.body.setUserData(entity);
        bodyComponent.body.destroyFixture(bodyComponent.body.getFixtureList().get(0));
        entity.add(bodyComponent);
        CameraComponent cameraComponent = createComponent(CameraComponent.class);
        cameraComponent.camera = engine.getStage().getCamera();
        cameraComponent.followingBody = followingEntity.getComponent(BodyComponent.class);
        cameraComponent.isFollowing = true;
        MovementComponent movementComponent = createComponent(MovementComponent.class);
        movementComponent.maxVelocitySpeed = 12;
        movementComponent.acceleration = 0.2f;
        entity.add(cameraComponent);
        entity.add(movementComponent);
        return entity;
    }

    public Entity createBG(String textureName) {
        Entity entity = createEntity();
        TextureComponent textureComponent = createTextureComponent(textureName, 0, 0, -1);
        BodyComponent bodyComponent = createBodyComponent(RenderSystem.BASE_WORLD_WIDTH/2f, RenderSystem.BASE_WORLD_HEIGHT/2f,
                textureComponent.textures.get(0).getRegionWidth(), textureComponent.textures.get(0).getRegionHeight(),
                MaterialType.NONE, BodyDef.BodyType.StaticBody, true, false);
        bodyComponent.body.setUserData(entity);
        bodyComponent.body.destroyFixture(bodyComponent.body.getFixtureList().get(0));
        bodyComponent.body.setActive(false);
        entity.add(bodyComponent);
        entity.add(textureComponent);
        return entity;
    }

    public Entity createWall(float x, float y) {
        Entity entity = createEntity();
        TextureComponent textureComponent = createTextureComponent("stone_wall", 0, 0, 0);
        BodyComponent bodyComponent = createBodyComponent(x, y,
                textureComponent.textures.get(0).getRegionWidth(),
                textureComponent.textures.get(0).getRegionHeight(),
                MaterialType.STONE, BodyDef.BodyType.StaticBody, true, false);
        bodyComponent.body.setUserData(entity);
        entity.add(textureComponent);
        entity.add(bodyComponent);
        EntityTypeComponent typeComponent = createComponent(EntityTypeComponent.class);
        typeComponent.type = EntityTypeComponent.EntityType.SCENERY;
        entity.add(typeComponent);
        return entity;
    }

    public Entity createPlayer(float x, float y) {
        Entity entity = createEntity();
        TextureComponent textureComponent = createTextureComponent("player", 0, 0, 0);
        entity.add(textureComponent);

        BodyComponent bodyComponent = createBodyComponent(x, y,
                textureComponent.textures.get(0).getRegionWidth(),
                textureComponent.textures.get(0).getRegionHeight(),
                MaterialType.MEAT, BodyDef.BodyType.DynamicBody, true, true);
        bodyComponent.body.setUserData(entity);
        entity.add(bodyComponent);

        MovementComponent movementComponent = createComponent(MovementComponent.class);
        movementComponent.maxVelocitySpeed = 3;
        movementComponent.acceleration = 0.5f;
        entity.add(movementComponent);

        EntityTypeComponent typeComponent = createComponent(EntityTypeComponent.class);
        typeComponent.type = EntityTypeComponent.EntityType.CREATURE;
        typeComponent.side = EntityTypeComponent.SideType.ALLY;
        entity.add(typeComponent);

        entity.add(createComponent(CollisionComponent.class));
        entity.add(createComponent(PlayerComponent.class));
        entity.add(createComponent(ControlComponent.class));
        createCameraEntity(entity);
        entity.add(createComponent(InventotyComponent.class));

        return entity;
    }

    public Entity createSwordSpellItem(float x, float y) {
        Entity entity = createEntity();

        CastComponent castComponent = createComponent(CastComponent.class);
        castComponent.type = CastComponent.SpellType.ATTACK;
        castComponent.useWay = CastComponent.UseWay.CHARGE;
        castComponent.travelWay = CastComponent.TravelWay.SWING;
        castComponent.chargeTime = 0.5f;
        castComponent.damage = 6;
        castComponent.range = 2;
        castComponent.speed = 1;
        castComponent.angle = 120;
        entity.add(castComponent);

        TextureComponent textureComponent = createTextureComponent("sword_medium",0,0,0);
        entity.add(textureComponent);

        BodyComponent bodyComponent = createBodyComponent(x, y,
                textureComponent.textures.get(0).getRegionWidth(),
                textureComponent.textures.get(0).getRegionHeight(),
                MaterialType.STEEL, BodyDef.BodyType.StaticBody,
                false, false);
        bodyComponent.body.setUserData(entity);
        bodyComponent.body.setTransform(
                bodyComponent.body.getTransform().getPosition().x,
                bodyComponent.body.getTransform().getPosition().y,
                (float)Math.toRadians(-45));
        toSensor(bodyComponent.body);
        entity.add(bodyComponent);

        ItemComponent itemComponent = createComponent(ItemComponent.class);
        entity.add(itemComponent);

        CollisionComponent collisionComponent = createComponent(CollisionComponent.class);
        entity.add(collisionComponent);

        return entity;
    }

    public Entity createFireballCast(float x, float y) {
        Entity entity = createEntity();

        CastComponent castComponent = createComponent(CastComponent.class);
        castComponent.setupSpell(CastComponent.SpellType.ATTACK, CastComponent.UseWay.HOLD,
                CastComponent.TravelWay.PROJECTILE, assetManager.getExplosionPool(),
                6, 6, -1, 1, 0, false);
        entity.add(castComponent);

        TextureComponent textureComponent = createComponent(TextureComponent.class);
        TextureRegion texture = assetManager.getTexture("frame_red");
        textureComponent.addTexture(texture, 0,0,-1);
        entity.add(textureComponent);

        EffectComponent effectComponent = createComponent(EffectComponent.class);
        PooledEffect effect = assetManager.getFireballPool().obtain();
        effect.scaleEffect(0.5f);
        effectComponent.addEffect(effect, 0, 0, 1);
        entity.add(effectComponent);

        BodyComponent bodyComponent = createBodyComponent(x, y, texture.getRegionWidth(), texture.getRegionHeight(),
                MaterialType.STONE, BodyDef.BodyType.StaticBody, true, false);
        bodyComponent.body.setUserData(entity);
        toSensor(bodyComponent.body);
        entity.add(bodyComponent);

        ItemComponent itemComponent = createComponent(ItemComponent.class);
        entity.add(itemComponent);

        CollisionComponent collisionComponent = createComponent(CollisionComponent.class);
        entity.add(collisionComponent);

        return entity;
    }

    public Entity createFireballSpell(Vector2 position, Vector2 direction, float maxSpeed,
                                      Entity castOwner, Entity castItem) {
        Entity entity = createEntity();

        SpellComponent spellComponent = createComponent(SpellComponent.class);
        entity.add(spellComponent);
        spellComponent.castItem = castItem;
        spellComponent.ignoreEntity = castOwner;

        EffectComponent effectComponent = createComponent(EffectComponent.class);
        effectComponent.shouldDeleteOnEffectDead = true;
        PooledEffect effect = assetManager.getFireballPool().obtain();
        effect.scaleEffect(0.75f);
        effectComponent.addEffect(effect, 0, 0, 1);
        entity.add(effectComponent);

        BodyComponent bodyComponent = createBodyComponent(position.x, position.y, 36, 36,
                MaterialType.RUBBER, BodyDef.BodyType.DynamicBody, true, true);
        toSensor(bodyComponent.body);
        bodyComponent.body.setUserData(entity);
        entity.add(bodyComponent);
        System.out.println(bodyComponent.body == null);

        MovementComponent movementComponent = createComponent(MovementComponent.class);
        movementComponent.targetVelocity.set(direction);
        movementComponent.maxVelocitySpeed = maxSpeed;
        entity.add(movementComponent);

        CollisionComponent collisionComponent = createComponent(CollisionComponent.class);
        entity.add(collisionComponent);

        return entity;
    }

    public Entity createEnemy(float x, float y) {
        Entity entity = createEntity();
        TextureComponent textureComponent = createTextureComponent("enemy", 0, 0, 0);
        entity.add(textureComponent);

        BodyComponent bodyComponent = createBodyComponent(x, y,
                textureComponent.textures.get(0).getRegionWidth(),
                textureComponent.textures.get(0).getRegionHeight(),
                MaterialType.MEAT, BodyDef.BodyType.DynamicBody, true, true);
        bodyComponent.body.setUserData(entity);
        entity.add(bodyComponent);

        MovementComponent movementComponent = createComponent(MovementComponent.class);
        movementComponent.acceleration = 0.5f;
        entity.add(movementComponent);

        EntityTypeComponent typeComponent = createComponent(EntityTypeComponent.class);
        typeComponent.type = EntityTypeComponent.EntityType.CREATURE;
        typeComponent.side = EntityTypeComponent.SideType.RIVAL;
        entity.add(typeComponent);

        entity.add(createComponent(CollisionComponent.class));
        entity.add(createComponent(InventotyComponent.class));
        return entity;
    }

    private  <T extends Component> T createComponent(Class<T> tClass) {
        return engine.createComponent(tClass);
    }

    private TextureComponent createTextureComponent(String textureName, float shiftX, float shiftY, float layerZ) {
        TextureComponent textureComponent = createComponent(TextureComponent.class);
        textureComponent.addTexture(assetManager.getTexture(textureName), shiftX, shiftY, layerZ);
        return  textureComponent;
    }

    private BodyComponent createBodyComponent(float x, float y, float width, float height,
                                              MaterialType materialType, BodyDef.BodyType bodyType,
                                              boolean fixedRotation, boolean isCircle) {
        BodyComponent bodyComponent = createComponent(BodyComponent.class);
        if (isCircle) {
            bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, width / RenderSystem.TILE_SIZE,
                    materialType, bodyType, fixedRotation);
        } else {
            bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                    width / RenderSystem.TILE_SIZE, height / RenderSystem.TILE_SIZE,
                    materialType, bodyType, fixedRotation);
        }
        return bodyComponent;
    }

    private Entity createEntity() {
        Entity entity = engine.createEntity();
        engine.addEntity(entity);
        return entity;
    }

    private void toSensor(Body body) {
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setSensor(true);
        }
    }
}
